package com.pisces.user.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.pisces.core.BasicProperties;
import com.pisces.core.annotation.LanguageAnnotation;

@ConfigurationProperties(prefix = "pisces.user")
@LanguageAnnotation(message = UserMessage.class, path = "user")
public class UserProperties extends BasicProperties {

}
