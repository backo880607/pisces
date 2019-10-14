package com.pisces.integration.helper;

import java.lang.reflect.ParameterizedType;

public abstract class AdapterRegister<T extends Enum<T>> implements DataSourceAdapter {
	Class<T> clazz;
	
	@SuppressWarnings("unchecked")
	public AdapterRegister() {
		clazz = (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		AdapterManager.register(this);
	}
	
	public abstract T getType();
}
