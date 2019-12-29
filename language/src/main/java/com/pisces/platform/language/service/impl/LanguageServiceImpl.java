package com.pisces.platform.language.service.impl;

import com.pisces.platform.core.annotation.LanguageAnnotation;
import com.pisces.platform.core.service.EntityServiceImpl;
import com.pisces.platform.language.LanguageManager;
import com.pisces.platform.language.bean.Language;
import com.pisces.platform.language.dao.LanguageDao;
import com.pisces.platform.language.service.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.Locale;

@Service
class LanguageServiceImpl extends EntityServiceImpl<Language, LanguageDao> implements LanguageService {
	@Autowired
	private LanguageManager languageMgr;

	@Override
	public Locale getLocale() {
		return get().getLocale();
	}
	
	@Override
	public void switchLocale(Locale locale) {
		Language language = get();
		language.setLocale(locale);
		language.clearBundles();
		for (LanguageAnnotation resource : languageMgr.getResources()) {
			language.bind(resource.path());
		}
		update(language);
	}
	
	@Override
	public String get(Enum<?> key, Object... arguments) {
		if (key == null) {
			return Language.ERROR;
		}
		return get().get(key.getDeclaringClass().getSimpleName() + "." + key.name(), arguments);
	}

	@Override
	public String get(String key, Object... arguments) {
		if (StringUtils.isEmpty(key)) {
			return Language.ERROR;
		}
		return get().get(key, arguments);
	}

	@Override
	public String get(Class<?> clazz) {
		if (clazz == null) {
			return Language.ERROR;
		}
		return get().get(clazz.getSimpleName());
	}

	@Override
	public String get(Field field) {
		if (field == null) {
			return Language.ERROR;
		}
		return get().get(field.getDeclaringClass().getSimpleName() + "." + field.getName());
	}
	
	@Override
	public String get(Class<?> clazz, String field) {
		if (clazz == null) {
			return Language.ERROR;
		}
		return get().get(clazz.getSimpleName() + "." + field);
	}

	@Override
	public String getTips(Class<?> clazz) {
		if (clazz == null) {
			return Language.ERROR;
		}
		return get().get("tips." + clazz.getSimpleName());
	}

	@Override
	public String getTips(Field field) {
		if (field == null) {
			return Language.ERROR;
		}
		return get().get("tips" + field.getDeclaringClass().getSimpleName() + "." + field.getName());
	}
}
