package com.pisces.nosql.config;

import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@EnableConfigurationProperties({MongoProperties.class, RedisProperties.class, NoSqlProperties.class})
@PropertySource(ignoreResourceNotFound=true, value="classpath:nosql.properties")
public class NoSqlAutoConfiguration {

}
