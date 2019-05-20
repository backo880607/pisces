package com.pisces.language.service;

import java.lang.reflect.Field;
import java.util.Locale;

import com.pisces.core.entity.EntityObject;
import com.pisces.core.service.EntityService;
import com.pisces.language.bean.Language;

public interface LanguageService extends EntityService<Language> {
	Locale getLocale();
	void switchLocale(Locale locale);
	String get(Enum<?> key, Object ...arguments);
	String get(String key, Object ...arguments);
	String get(Class<? extends EntityObject> clazz);
	String get(Field field);
	String getTips(Class<? extends EntityObject> clazz);
	String getTips(Field field);
}
