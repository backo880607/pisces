package com.pisces.platform.user.config;

import com.pisces.platform.core.BasicProperties;
import com.pisces.platform.core.annotation.LanguageAnnotation;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "pisces.user")
@LanguageAnnotation(message = UserMessage.class, path = "user")
public class UserProperties extends BasicProperties {

}
