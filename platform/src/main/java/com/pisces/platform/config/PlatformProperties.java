package com.pisces.platform.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.pisces.core.BasicProperties;
import com.pisces.core.annotation.LanguageAnnotation;

@ConfigurationProperties(prefix = "pisces.platform")
@LanguageAnnotation(message = PlatformMessage.class, path = "platform")
public class PlatformProperties extends BasicProperties {

}
