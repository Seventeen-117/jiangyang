package com.jiangYang.cloud.module.system.service.auth;

import cn.hutool.core.util.IdUtil;
import com.jiangYang.cloud.framework.common.enums.CommonStatusEnum;
import com.jiangYang.cloud.framework.common.enums.UserTypeEnum;
import com.jiangYang.cloud.framework.common.util.monitor.TracerUtils;
import com.jiangYang.cloud.framework.common.util.servlet.ServletUtils;
import com.jiangYang.cloud.module.system.api.logger.dto.LoginLogCreateReqDTO;
import com.jiangYang.cloud.module.system.config.QrCodeProperties;
import com.jiangYang.cloud.module.system.controller.admin.auth.qcr.QrCodeStatusEnum;
import com.jiangYang.cloud.module.system.controller.admin.auth.qcr.QrCodeUtils;
import com.jiangYang.cloud.module.system.controller.admin.auth.vo.*;
import com.jiangYang.cloud.module.system.dal.dataobject.user.AdminUserDO;
import com.jiangYang.cloud.module.system.dal.mysql.qcr.AuthQrCodeMapper;
import com.jiangYang.cloud.module.system.enums.logger.LoginLogTypeEnum;
import com.jiangYang.cloud.module.system.enums.logger.LoginResultEnum;
import com.jiangYang.cloud.module.system.service.logger.LoginLogService;
import com.jiangYang.cloud.module.system.service.oauth2.OAuth2TokenService;
import com.jiangYang.cloud.module.system.service.user.AdminUserService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Objects;

import static com.jiangYang.cloud.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.jiangYang.cloud.module.system.controller.admin.auth.qcr.AuthErrorCodeConstants.*;
import static com.jiangYang.cloud.module.system.enums.ErrorCodeConstants.AUTH_LOGIN_BAD_CREDENTIALS;
import static com.jiangYang.cloud.module.system.enums.ErrorCodeConstants.AUTH_LOGIN_USER_DISABLED;

@Service
public class AuthQrCodeServiceImpl implements AuthQrCodeService {

    @Resource
    private AuthQrCodeMapper authQrCodeMapper;

    @Resource
    private AdminAuthService adminAuthService;

    @Resource
    private AdminUserService userService;

    @Resource
    private LoginLogService loginLogService;

    @Resource
    private OAuth2TokenService oauth2TokenService;

    @Resource
    private QrCodeProperties qrCodeProperties;


    /**
     * 获取二维码基础URL
     * 用于生成完整的二维码URL
     */
    private String getQrCodeBaseUrl() {
        return qrCodeProperties.getBaseUrl();
    }


    @Override
    public QrCodeRespVO createQrCode(String tenantName) {
        // 1. 生成二维码ID
        String id = IdUtil.fastSimpleUUID();

        // 2. 创建二维码记录
        AuthQrCodeDO qrCode = new AuthQrCodeDO()
                .setId(id)
                .setTenantName(tenantName)
                .setStatus(QrCodeStatusEnum.UNUSED.getStatus())
                .setExpireTime(LocalDateTime.now().plusMinutes(5)); // 5分钟有效期
        authQrCodeMapper.insert(qrCode);

        // 3. 生成二维码内容（前端扫码时需要的信息）
        String qrCodeContent = String.format("%s?id=%s&tenantName=%s", getQrCodeBaseUrl(), id, tenantName);

        // 4. 生成二维码图片
        byte[] qrCodeImage = QrCodeUtils.generateQrCode(qrCodeContent, 200, 200);
        String qrCodeBase64 = Base64.getEncoder().encodeToString(qrCodeImage);

        // 5. 返回结果
        return new QrCodeRespVO()
                .setId(id)
                .setUrl(qrCodeContent)
                .setQrCodeBase64(qrCodeBase64);
    }


    @Override
    public QrCodeStatusRespVO getQrCodeStatus(String id, String tenantName) {
        // 1. 获取二维码记录
        AuthQrCodeDO qrCode = authQrCodeMapper.selectById(id);
        if (qrCode == null) {
            throw exception(AUTH_QR_CODE_NOT_FOUND);
        }

        // 2. 校验是否过期
        if (LocalDateTime.now().isAfter(qrCode.getExpireTime())) {
            authQrCodeMapper.updateById(new AuthQrCodeDO()
                    .setId(id)
                    .setStatus(QrCodeStatusEnum.EXPIRED.getStatus()));
            return new QrCodeStatusRespVO().setStatus(QrCodeStatusEnum.EXPIRED.getStatus());
        }

        // 3. 返回状态
        return new QrCodeStatusRespVO().setStatus(qrCode.getStatus());
    }

    @Override
    public void scanQrCode(String id) {
        // 1. 获取二维码记录
        AuthQrCodeDO qrCode = authQrCodeMapper.selectById(id);
        if (qrCode == null) {
            throw exception(AUTH_QR_CODE_NOT_FOUND);
        }

        // 2. 校验是否过期
        if (LocalDateTime.now().isAfter(qrCode.getExpireTime())) {
            authQrCodeMapper.updateById(new AuthQrCodeDO()
                    .setId(id)
                    .setStatus(QrCodeStatusEnum.EXPIRED.getStatus()));
            throw exception(AUTH_QR_CODE_EXPIRED);
        }

        // 3. 校验状态
        if (!QrCodeStatusEnum.UNUSED.getStatus().equals(qrCode.getStatus())) {
            throw exception(AUTH_QR_CODE_STATUS_ERROR);
        }

        // 4. 更新状态为已扫描
        authQrCodeMapper.updateById(new AuthQrCodeDO()
                .setId(id)
                .setStatus(QrCodeStatusEnum.SCANNED.getStatus()));
    }

    @Override
    public AuthLoginRespVO verifyQrCode(QrCodeAuthReqVO reqVO) {
        // 1. 获取二维码记录
        AuthQrCodeDO qrCode = authQrCodeMapper.selectById(reqVO.getId());
        if (qrCode == null) {
            throw exception(AUTH_QR_CODE_NOT_FOUND);
        }

        // 2. 校验是否过期
        if (LocalDateTime.now().isAfter(qrCode.getExpireTime())) {
            authQrCodeMapper.updateById(new AuthQrCodeDO()
                    .setId(reqVO.getId())
                    .setStatus(QrCodeStatusEnum.EXPIRED.getStatus()));
            throw exception(AUTH_QR_CODE_EXPIRED);
        }

        // 3. 校验状态
        if (!QrCodeStatusEnum.SCANNED.getStatus().equals(qrCode.getStatus())) {
            throw exception(AUTH_QR_CODE_STATUS_ERROR);
        }

        try {
            // 4. 更新状态为验证中
            authQrCodeMapper.updateById(new AuthQrCodeDO()
                    .setId(reqVO.getId())
                    .setStatus(QrCodeStatusEnum.VERIFYING.getStatus()));

            // 5. 验证登录信息
            AuthLoginReqVO loginReqVO = AuthLoginReqVO.builder()
                    .tenantName(reqVO.getTenantName())
                    .username(reqVO.getUsername())
                    .password(reqVO.getPassword())
                    .build();
            AdminUserDO user = authenticate(reqVO.getUsername(), reqVO.getPassword());

            AuthLoginRespVO loginRespVO = adminAuthService.login(loginReqVO);

            // 6. 更新状态为已授权
            authQrCodeMapper.updateById(new AuthQrCodeDO()
                    .setId(reqVO.getId())
                    .setUserId(loginRespVO.getUserId())
                    .setStatus(QrCodeStatusEnum.AUTHORIZED.getStatus()));

            // 7. 创建登录日志
            createLoginLog(user.getId(), reqVO.getUsername(),
                    LoginLogTypeEnum.QR_CODE, LoginResultEnum.SUCCESS);

            return loginRespVO;

        } catch (ServiceException e) {
            // 8. 验证失败，更新状态
            authQrCodeMapper.updateById(new AuthQrCodeDO()
                    .setId(reqVO.getId())
                    .setStatus(QrCodeStatusEnum.AUTH_FAILED.getStatus()));
            throw e;
        }
    }

    public AdminUserDO authenticate(String username, String password) {
        final LoginLogTypeEnum logTypeEnum = LoginLogTypeEnum.LOGIN_USERNAME;
        // 校验账号是否存在
        AdminUserDO user = userService.getUserByUsername(username);
        if (user == null) {
            createLoginLog(null, username, logTypeEnum, LoginResultEnum.BAD_CREDENTIALS);
            throw exception(AUTH_LOGIN_BAD_CREDENTIALS);
        }
        if (!userService.isPasswordMatch(password, user.getPassword())) {
            createLoginLog(user.getId(), username, logTypeEnum, LoginResultEnum.BAD_CREDENTIALS);
            throw exception(AUTH_LOGIN_BAD_CREDENTIALS);
        }
        // 校验是否禁用
        if (CommonStatusEnum.isDisable(user.getStatus())) {
            createLoginLog(user.getId(), username, logTypeEnum, LoginResultEnum.USER_DISABLED);
            throw exception(AUTH_LOGIN_USER_DISABLED);
        }
        return user;
    }

    private void createLoginLog(Long userId, String username,
                                LoginLogTypeEnum logTypeEnum, LoginResultEnum loginResult) {
        LoginLogCreateReqDTO reqDTO = new LoginLogCreateReqDTO();
        reqDTO.setLogType(logTypeEnum.getType());
        reqDTO.setTraceId(TracerUtils.getTraceId());
        reqDTO.setUserId(userId);
        reqDTO.setUserType(UserTypeEnum.ADMIN.getValue());
        reqDTO.setUsername(username);
        reqDTO.setUserAgent(ServletUtils.getUserAgent());
        reqDTO.setUserIp(ServletUtils.getClientIP());
        reqDTO.setResult(loginResult.getResult());
        loginLogService.createLoginLog(reqDTO);

        if (userId != null && Objects.equals(LoginResultEnum.SUCCESS.getResult(), loginResult.getResult())) {
            userService.updateUserLogin(userId, ServletUtils.getClientIP());
        }
    }
}