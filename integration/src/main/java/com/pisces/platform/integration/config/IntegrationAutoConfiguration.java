package com.pisces.platform.integration.config;

import com.pisces.platform.core.config.BaseConfiguration;
import com.pisces.platform.integration.helper.AdapterManager;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@EnableConfigurationProperties(IntegrationProperties.class)
@PropertySource(ignoreResourceNotFound=true, value="classpath:integration.properties")
public class IntegrationAutoConfiguration extends BaseConfiguration {
}
