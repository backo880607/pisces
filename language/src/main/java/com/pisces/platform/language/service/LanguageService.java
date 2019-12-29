package com.pisces.platform.language.service;

import com.pisces.platform.core.service.EntityService;
import com.pisces.platform.language.bean.Language;

import java.lang.reflect.Field;
import java.util.Locale;

public interface LanguageService extends EntityService<Language> {
	Locale getLocale();
	void switchLocale(Locale locale);
	String get(Enum<?> key, Object ...arguments);
	String get(String key, Object ...arguments);
	String get(Class<?> clazz);
	String get(Field field);
	String get(Class<?> clazz, String field);
	String getTips(Class<?> clazz);
	String getTips(Field field);
}
