package com.pisces.platform.framework.config;

import com.pisces.platform.core.BasicProperties;
import com.pisces.platform.core.annotation.LanguageAnnotation;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "pisces.platform")
@LanguageAnnotation(message = FrameworkMessage.class, path = "framework")
public class FrameworkProperties extends BasicProperties {

}
