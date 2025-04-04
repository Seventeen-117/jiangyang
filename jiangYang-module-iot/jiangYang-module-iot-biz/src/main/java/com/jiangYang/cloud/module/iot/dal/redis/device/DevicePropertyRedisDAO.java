package com.jiangYang.cloud.module.iot.dal.redis.device;

import cn.hutool.core.collection.CollUtil;
import com.jiangYang.cloud.framework.common.util.json.JsonUtils;
import com.jiangYang.cloud.module.iot.dal.dataobject.device.IotDevicePropertyDO;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Map;

import static com.jiangYang.cloud.framework.common.util.collection.CollectionUtils.convertMap;
import static com.jiangYang.cloud.module.iot.dal.redis.RedisKeyConstants.DEVICE_PROPERTY;

/**
 * {@link IotDevicePropertyDO} 的 Redis DAO
 */
@Repository
public class DevicePropertyRedisDAO {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public Map<String, IotDevicePropertyDO> get(String deviceKey) {
        String redisKey = formatKey(deviceKey);
        Map<Object, Object> entries = stringRedisTemplate.opsForHash().entries(redisKey);
        if (CollUtil.isEmpty(entries)) {
            return Collections.emptyMap();
        }
        return convertMap(entries.entrySet(),
                entry -> (String) entry.getKey(),
                entry -> JsonUtils.parseObject((String) entry.getValue(), IotDevicePropertyDO.class));
    }

    public void putAll(String deviceKey, Map<String, IotDevicePropertyDO> properties) {
        if (CollUtil.isEmpty(properties)) {
            return;
        }
        String redisKey = formatKey(deviceKey);
        stringRedisTemplate.opsForHash().putAll(redisKey, convertMap(properties.entrySet(),
                Map.Entry::getKey,
                entry -> JsonUtils.toJsonString(entry.getValue())));
    }

    private static String formatKey(String deviceKey) {
        return String.format(DEVICE_PROPERTY, deviceKey);
    }

}
