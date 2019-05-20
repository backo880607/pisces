package com.pisces.web.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.pisces.core.BasicProperties;
import com.pisces.core.annotation.LanguageAnnotation;

@ConfigurationProperties(prefix = "pisces.web")
@LanguageAnnotation(message = WebMessage.class, path = "web")
public class WebProperties implements BasicProperties {
	private String clientId;
    private String base64Secret;
    private String name;
    private int expiresSecond;
    
	public String getClientId() {
		return clientId;
	}
	
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	
	public String getBase64Secret() {
		return base64Secret;
	}
	
	public void setBase64Secret(String base64Secret) {
		this.base64Secret = base64Secret;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getExpiresSecond() {
		return expiresSecond;
	}
	
	public void setExpiresSecond(int expiresSecond) {
		this.expiresSecond = expiresSecond;
	}
	
}
