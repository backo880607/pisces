package com.pisces.platform.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@EnableConfigurationProperties(PlatformProperties.class)
@PropertySource(ignoreResourceNotFound=true, value="classpath:platform.properties")
public class PlatformAutoConfiguration {

}
