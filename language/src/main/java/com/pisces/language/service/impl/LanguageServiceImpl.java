package com.pisces.language.service.impl;

import java.lang.reflect.Field;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.pisces.core.annotation.LanguageAnnotation;
import com.pisces.core.entity.EntityObject;
import com.pisces.core.service.EntityServiceImpl;
import com.pisces.language.LanguageManager;
import com.pisces.language.bean.Language;
import com.pisces.language.dao.LanguageDao;
import com.pisces.language.service.LanguageService;

@Service
class LanguageServiceImpl extends EntityServiceImpl<Language, LanguageDao> implements LanguageService {
	@Autowired
	private LanguageManager languageMgr;

	@Override
	public Locale getLocale() {
		return select().getLocale();
	}
	
	@Override
	public void switchLocale(Locale locale) {
		Language language = select();
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
		return select().get(key.getDeclaringClass().getSimpleName() + "." + key.name(), arguments);
	}

	@Override
	public String get(String key, Object... arguments) {
		if (StringUtils.isEmpty(key)) {
			return Language.ERROR;
		}
		return select().get(key, arguments);
	}

	@Override
	public String get(Class<? extends EntityObject> clazz) {
		if (clazz == null) {
			return Language.ERROR;
		}
		return select().get(clazz.getSimpleName());
	}

	@Override
	public String get(Field field) {
		if (field == null) {
			return Language.ERROR;
		}
		return select().get(field.getDeclaringClass().getSimpleName() + "." + field.getName());
	}

	@Override
	public String getTips(Class<? extends EntityObject> clazz) {
		if (clazz == null) {
			return Language.ERROR;
		}
		return select().get("tips." + clazz.getSimpleName());
	}

	@Override
	public String getTips(Field field) {
		if (field == null) {
			return Language.ERROR;
		}
		return select().get("tips" + field.getDeclaringClass().getSimpleName() + "." + field.getName());
	}
}
