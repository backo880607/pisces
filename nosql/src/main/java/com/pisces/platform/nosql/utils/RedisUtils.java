package com.pisces.platform.nosql.utils;

import com.pisces.platform.core.utils.EntityUtils;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

public class RedisUtils {
	
	public static <T> RedisTemplate<String, T> obtainTemplate(Class<T> clazz, RedisConnectionFactory factory) {
		RedisTemplate<String, T> redisTemplate = new RedisTemplate<String, T>();
		redisTemplate.setConnectionFactory(factory);
		redisTemplate.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
		redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        // 解决查询缓存转换异常的问题
        Jackson2JsonRedisSerializer<T> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<T>(clazz);
        jackson2JsonRedisSerializer.setObjectMapper(EntityUtils.createEntityMapper());
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        return redisTemplate;
	}
}
