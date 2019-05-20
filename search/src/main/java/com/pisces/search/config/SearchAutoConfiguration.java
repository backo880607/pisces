package com.pisces.search.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@EnableConfigurationProperties(SearchProperties.class)
@PropertySource(ignoreResourceNotFound=true, value="classpath:search.properties")
public class SearchAutoConfiguration {

}
