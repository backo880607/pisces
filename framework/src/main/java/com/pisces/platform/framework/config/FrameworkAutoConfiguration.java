package com.pisces.platform.framework.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@EnableConfigurationProperties(FrameworkProperties.class)
@PropertySource(ignoreResourceNotFound=true, value= "classpath:framework.properties")
public class FrameworkAutoConfiguration {

}
