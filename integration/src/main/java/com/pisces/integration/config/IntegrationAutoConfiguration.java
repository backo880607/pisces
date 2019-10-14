package com.pisces.integration.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.pisces.core.config.BaseConfiguration;
import com.pisces.integration.helper.AdapterManager;

@Configuration
@EnableConfigurationProperties(IntegrationProperties.class)
@PropertySource(ignoreResourceNotFound=true, value="classpath:integration.properties")
public class IntegrationAutoConfiguration extends BaseConfiguration {
	
	@Override
	public void init() {
		AdapterManager.init();
	}
}
