package com.pisces.rds.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.pisces.core.BasicProperties;
import com.pisces.core.annotation.LanguageAnnotation;

@ConfigurationProperties(prefix = "pisces.rds")
@LanguageAnnotation(message = RdsMessage.class, path = "rds")
public class RdsProperties implements BasicProperties {

}
