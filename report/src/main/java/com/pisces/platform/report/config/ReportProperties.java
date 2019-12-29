package com.pisces.platform.report.config;

import com.pisces.platform.core.BasicProperties;
import com.pisces.platform.core.annotation.LanguageAnnotation;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "pisces.report")
@LanguageAnnotation(message = ReportMessage.class, path = "report")
public class ReportProperties extends BasicProperties {

}
