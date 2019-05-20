package com.pisces.integration.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.pisces.core.BasicProperties;
import com.pisces.core.annotation.LanguageAnnotation;

@ConfigurationProperties(prefix = "pisces.integration")
@LanguageAnnotation(message = IntegrationMessage.class, path = "integration")
public class IntegrationProperties implements BasicProperties {

}
