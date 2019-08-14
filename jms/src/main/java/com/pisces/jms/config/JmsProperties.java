package com.pisces.jms.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.pisces.core.BasicProperties;
import com.pisces.core.annotation.LanguageAnnotation;

@ConfigurationProperties(prefix = "pisces.jms")
@LanguageAnnotation(message = JmsMessage.class, path = "jms")
public class JmsProperties extends BasicProperties {

}
