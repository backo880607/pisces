package com.pisces.platform.jms.config;

import com.pisces.platform.core.BasicProperties;
import com.pisces.platform.core.annotation.LanguageAnnotation;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "pisces.jms")
@LanguageAnnotation(message = JmsMessage.class, path = "jms")
public class JmsProperties extends BasicProperties {

}
