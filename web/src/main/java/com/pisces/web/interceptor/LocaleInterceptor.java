package com.pisces.web.interceptor;

import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

public class LocaleInterceptor extends LocaleChangeInterceptor {
	public LocaleInterceptor() {
		setParamName("lang");
	}
}
