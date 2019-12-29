package com.pisces.platform.user.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@EnableConfigurationProperties({UserProperties.class})
@PropertySource(ignoreResourceNotFound=true, value="classpath:user.properties")
public class UserAutoConfiguration {

}
