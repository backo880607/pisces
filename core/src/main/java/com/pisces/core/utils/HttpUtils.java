package com.pisces.core.utils;

import java.util.Map;
import java.util.Map.Entry;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

public class HttpUtils {
	protected final static String DEFAULT_ENCODING = "UTF-8";
	private OkHttp3ClientHttpRequestFactory factory = new OkHttp3ClientHttpRequestFactory();
	private RestTemplate restTemplate;
	private HttpHeaders headers;
	private String baseUrl = "";
	
	public HttpUtils(int connectTimeout, int readTimeout, int writeTimeout) {
		factory.setConnectTimeout(connectTimeout);
		factory.setReadTimeout(readTimeout);
		factory.setWriteTimeout(writeTimeout);
		restTemplate = new RestTemplate(factory);
		headers = new HttpHeaders();
		headers.set(HttpHeaders.CONTENT_ENCODING, DEFAULT_ENCODING);
	}
	
	public void setBaseUrl(String url) {
		this.baseUrl = StringUtils.trimTrailingCharacter(url, '/');
	}
	
	private String getFullUrl(String url) {
		if (StringUtils.isEmpty(url)) {
			return "";
		}
		
		if (url.startsWith("/")) {
			url = this.baseUrl + url;
		} else {
			url = this.baseUrl + "/" + url;
		}
		
		return url;
	}
	
	public <T> T get(String url, Map<String, Object> params, Class<T> responseType) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getFullUrl(url));
		for (Entry<String, Object> entry : params.entrySet()) {
			builder.queryParam(entry.getKey(), entry.getValue());
		}
		HttpEntity<?> entity = new HttpEntity<>(headers);
		HttpEntity<T> response = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET, entity,
				responseType);
		return response.getBody();
	}
	
	public <T> T post(String url, Map<String, Object> params, Class<T> responseType) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getFullUrl(url));
		for (Entry<String, Object> entry : params.entrySet()) {
			builder.queryParam(entry.getKey(), entry.getValue());
		}
		HttpEntity<?> entity = new HttpEntity<>(headers);
		HttpEntity<T> response = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.POST, entity,
				responseType);
		return response.getBody();
	}
}
