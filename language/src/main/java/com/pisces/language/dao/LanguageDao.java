package com.pisces.language.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pisces.language.bean.Language;
import com.pisces.language.service.LanguageService;
import com.pisces.rds.common.SQLSingletonDao;

@Component
public class LanguageDao extends SQLSingletonDao<Language> {
	
	@Autowired
	private LanguageService languageService;
	
	@Override
	public void afterLoadData() {
		languageService.switchLocale(select().getLocale());
	}
}
