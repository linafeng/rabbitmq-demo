package com.fiona.mq.rabbitmq.util;

import com.fiona.mq.rabbitmq.config.consts.RedisKeyPrefix;
import com.fiona.mq.rabbitmq.util.base.AppCtxRegister;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;

import java.util.concurrent.TimeUnit;

public class RedisUtil {
    private static RedisTemplate redisTemplate = AppCtxRegister.getBean(RedisTemplate.class);
    public static final String GENID_KEY = RedisKeyPrefix.RABBIT_MQ + RedisKeyPrefix.SEP + RedisKeyPrefix.GENID;

    /**
     * @param key key
     * @return
     * @Description: 获取自增长值
     */
    public static Long getIncr(String key) {
        RedisAtomicLong entityIdCounter = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());
        Long increment = entityIdCounter.getAndIncrement();
        entityIdCounter.expire(0, TimeUnit.SECONDS);
        return increment;
    }

    /**
     * @param key   key
     * @param value 当前值
     * @Description: 初始化自增长值
     */
    public void setIncr(String key, int value) {
        RedisAtomicLong counter = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());
        counter.set(value);
        counter.expire(0, TimeUnit.SECONDS);
    }
}
