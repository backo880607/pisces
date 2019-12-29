package com.pisces.platform.rds.config;

import com.pisces.platform.core.BasicProperties;
import com.pisces.platform.core.annotation.LanguageAnnotation;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "pisces.rds")
@LanguageAnnotation(message = RdsMessage.class, path = "rds")
public class RdsProperties extends BasicProperties {

}
