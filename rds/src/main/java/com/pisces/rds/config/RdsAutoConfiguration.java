package com.pisces.rds.config;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import tk.mybatis.mapper.autoconfigure.MapperProperties;
import tk.mybatis.mapper.autoconfigure.MybatisProperties;

@Configuration
@EnableConfigurationProperties({DataSourceProperties.class, MybatisProperties.class, MapperProperties.class, RdsProperties.class})
@PropertySource(ignoreResourceNotFound=true, value="classpath:rds.properties")
public class RdsAutoConfiguration {

}
