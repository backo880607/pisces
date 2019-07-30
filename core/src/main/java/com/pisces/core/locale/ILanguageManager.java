package com.pisces.core.locale;

import java.lang.reflect.Field;
import java.util.Locale;

public interface ILanguageManager {
	public void init();
	public Locale getLocale();
	public String get(Enum<?> key, Object ...arguments);
	public String get(String key, Object ...arguments);
	public String get(Class<?> clazz);
	public String get(Field field);
	public String get(Class<?> clazz, String field);
	public String getTips(Class<?> clazz);
	public String getTips(Field field);
}
