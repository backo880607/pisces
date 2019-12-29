package com.pisces.platform.report.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@EnableConfigurationProperties(ReportProperties.class)
@PropertySource(ignoreResourceNotFound=true, value="classpath:report.properties")
public class ReportAutoConfiguration {

}
