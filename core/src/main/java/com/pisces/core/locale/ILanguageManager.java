package com.pisces.core.locale;

import java.lang.reflect.Field;

import com.pisces.core.entity.EntityObject;

public interface ILanguageManager {
	public void init();
	public String get(Enum<?> key, Object ...arguments);
	public String get(String key, Object ...arguments);
	public String get(Class<? extends EntityObject> clazz);
	public String get(Field field);
	public String getTips(Class<? extends EntityObject> clazz);
	public String getTips(Field field);
}
