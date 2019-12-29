package com.pisces.platform.search.config;

import com.pisces.platform.core.BasicProperties;
import com.pisces.platform.core.annotation.LanguageAnnotation;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "pisces.search")
@LanguageAnnotation(message = SearchMessage.class, path = "search")
public class SearchProperties extends BasicProperties {

}
