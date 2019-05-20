package com.pisces.nosql.utils;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.pisces.core.utils.EntityUtils;

public class RedisUtils {
	
	public static <T> RedisTemplate<Long, T> obtainTemplate(Class<T> clazz, RedisConnectionFactory factory) {
		RedisTemplate<Long, T> redisTemplate = new RedisTemplate<Long, T>();
		redisTemplate.setConnectionFactory(factory);
		redisTemplate.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
		redisTemplate.setKeySerializer(new StringRedisSerializer());
        // 解决查询缓存转换异常的问题
        Jackson2JsonRedisSerializer<T> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<T>(clazz);
        jackson2JsonRedisSerializer.setObjectMapper(EntityUtils.defaultObjectMapper());
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        return redisTemplate;
	}
}
