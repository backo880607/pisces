package com.pisces.platform.nosql.config;

import com.pisces.platform.core.BasicProperties;
import com.pisces.platform.core.annotation.LanguageAnnotation;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "pisces.nosql")
@LanguageAnnotation(message = NoSqlMessage.class, path = "nosql")
public class NoSqlProperties extends BasicProperties {

}
