package com.pisces.platform.integration.config;

import com.pisces.platform.core.BasicProperties;
import com.pisces.platform.core.annotation.LanguageAnnotation;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "pisces.integration")
@LanguageAnnotation(message = IntegrationMessage.class, path = "integration")
public class IntegrationProperties extends BasicProperties {

}
