package com.pisces.platform.core.config;

import com.pisces.platform.core.BasicProperties;
import com.pisces.platform.core.annotation.LanguageAnnotation;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "pisces.core")
@LanguageAnnotation(message = CoreMessage.class, path = "core")
public class CoreProperties extends BasicProperties {

}
