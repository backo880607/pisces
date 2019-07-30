package com.pisces.language;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pisces.core.BasicProperties;
import com.pisces.core.annotation.LanguageAnnotation;
import com.pisces.core.locale.ILanguageManager;
import com.pisces.core.utils.AppUtils;
import com.pisces.language.service.LanguageService;

@Component
public class LanguageManager implements ILanguageManager {
	@Autowired
	private LanguageService langService;
	
	private List<LanguageAnnotation> resources = new ArrayList<>();
	
	@Override
	public void init() {
		Map<String, BasicProperties> configs = AppUtils.getBeansOfType(BasicProperties.class);
		for (Entry<String, BasicProperties> entry : configs.entrySet()) {
			Class<? extends BasicProperties> configClass = entry.getValue().getClass();
			LanguageAnnotation languageAnnotation = configClass.getAnnotation(LanguageAnnotation.class);
			if (languageAnnotation != null) {
				resources.add(languageAnnotation);
			}
		}
	}
	
	@Override
	public Locale getLocale() {
		return langService.getLocale();
	}
	
	public List<LanguageAnnotation> getResources() {
		return this.resources;
	}

	@Override
	public String get(Enum<?> key, Object... arguments) {
		return langService.get(key, arguments);
	}

	@Override
	public String get(String key, Object... arguments) {
		return langService.get(key, arguments);
	}

	@Override
	public String get(Class<?> clazz) {
		return langService.get(clazz);
	}

	@Override
	public String get(Field field) {
		return langService.get(field);
	}
	
	@Override
	public String get(Class<?> clazz, String field) {
		return langService.get(clazz, field);
	}

	@Override
	public String getTips(Class<?> clazz) {
		return langService.getTips(clazz);
	}

	@Override
	public String getTips(Field field) {
		return langService.getTips(field);
	}
}