package com.pisces.platform.language.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@EnableConfigurationProperties({LanguageProperties.class})
@PropertySource(ignoreResourceNotFound=true, value="classpath:language.properties")
public class LanguageAutoConfiguration {

}
