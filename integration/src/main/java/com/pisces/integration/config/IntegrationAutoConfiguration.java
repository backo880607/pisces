package com.pisces.integration.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@EnableConfigurationProperties(IntegrationProperties.class)
@PropertySource(ignoreResourceNotFound=true, value="classpath:integration.properties")
public class IntegrationAutoConfiguration {
}
