package com.pisces.search.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.pisces.core.BasicProperties;
import com.pisces.core.annotation.LanguageAnnotation;

@ConfigurationProperties(prefix = "pisces.search")
@LanguageAnnotation(message = SearchMessage.class, path = "search")
public class SearchProperties extends BasicProperties {

}
