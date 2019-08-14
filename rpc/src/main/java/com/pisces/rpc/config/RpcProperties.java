package com.pisces.rpc.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.pisces.core.BasicProperties;
import com.pisces.core.annotation.LanguageAnnotation;

@ConfigurationProperties(prefix = "pisces.rpc")
@LanguageAnnotation(message = RpcMessage.class, path = "rpc")
public class RpcProperties extends BasicProperties {

}
