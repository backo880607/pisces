package com.pisces.platform.language.config;

import com.pisces.platform.core.BasicProperties;
import com.pisces.platform.core.annotation.LanguageAnnotation;
import com.pisces.platform.rds.config.RdsMessage;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "pisces.language")
@LanguageAnnotation(message = RdsMessage.class, path = "language")
public class LanguageProperties extends BasicProperties {

}
