package com.pisces.nosql.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.pisces.core.BasicProperties;
import com.pisces.core.annotation.LanguageAnnotation;

@ConfigurationProperties(prefix = "pisces.nosql")
@LanguageAnnotation(message = NoSqlMessage.class, path = "nosql")
public class NoSqlProperties implements BasicProperties {

}
