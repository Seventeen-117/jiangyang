package com.jiangYang.cloud.framework.idempotent.config;

import com.jiangYang.cloud.framework.idempotent.core.aop.IdempotentAspect;
import com.jiangYang.cloud.framework.idempotent.core.keyresolver.impl.DefaultIdempotentKeyResolver;
import com.jiangYang.cloud.framework.idempotent.core.keyresolver.impl.ExpressionIdempotentKeyResolver;
import com.jiangYang.cloud.framework.idempotent.core.keyresolver.IdempotentKeyResolver;
import com.jiangYang.cloud.framework.idempotent.core.keyresolver.impl.UserIdempotentKeyResolver;
import com.jiangYang.cloud.framework.idempotent.core.redis.IdempotentRedisDAO;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import com.jiangYang.cloud.framework.redis.config.JiangYangRedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;

@AutoConfiguration(after = JiangYangRedisAutoConfiguration.class)
public class JiangYangIdempotentConfiguration {

    @Bean
    public IdempotentAspect idempotentAspect(List<IdempotentKeyResolver> keyResolvers, IdempotentRedisDAO idempotentRedisDAO) {
        return new IdempotentAspect(keyResolvers, idempotentRedisDAO);
    }

    @Bean
    public IdempotentRedisDAO idempotentRedisDAO(StringRedisTemplate stringRedisTemplate) {
        return new IdempotentRedisDAO(stringRedisTemplate);
    }

    // ========== 各种 IdempotentKeyResolver Bean ==========

    @Bean
    public DefaultIdempotentKeyResolver defaultIdempotentKeyResolver() {
        return new DefaultIdempotentKeyResolver();
    }

    @Bean
    public UserIdempotentKeyResolver userIdempotentKeyResolver() {
        return new UserIdempotentKeyResolver();
    }

    @Bean
    public ExpressionIdempotentKeyResolver expressionIdempotentKeyResolver() {
        return new ExpressionIdempotentKeyResolver();
    }

}
