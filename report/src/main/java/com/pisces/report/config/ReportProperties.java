package com.pisces.report.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.pisces.core.BasicProperties;
import com.pisces.core.annotation.LanguageAnnotation;

@ConfigurationProperties(prefix = "pisces.report")
@LanguageAnnotation(message = ReportMessage.class, path = "report")
public class ReportProperties extends BasicProperties {

}
