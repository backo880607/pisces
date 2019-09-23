package com.pisces.core.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.pisces.core.BasicProperties;
import com.pisces.core.annotation.LanguageAnnotation;

@ConfigurationProperties(prefix = "pisces.core")
@LanguageAnnotation(message = CoreMessage.class, path = "core")
public class CoreProperties extends BasicProperties {

}
