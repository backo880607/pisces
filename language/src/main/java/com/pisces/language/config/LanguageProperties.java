package com.pisces.language.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.pisces.core.annotation.LanguageAnnotation;
import com.pisces.rds.config.RdsMessage;

@ConfigurationProperties(prefix = "pisces.language")
@LanguageAnnotation(message = RdsMessage.class, path = "language")
public class LanguageProperties {

}
