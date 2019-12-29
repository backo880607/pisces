package com.pisces.platform.language.dao;

import com.pisces.platform.language.bean.Language;
import com.pisces.platform.language.service.LanguageService;
import com.pisces.platform.rds.common.SQLSingletonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LanguageDao extends SQLSingletonDao<Language> {
	
	@Autowired
	private LanguageService languageService;
	
	@Override
	public void loadData() {
		super.loadData();
		languageService.switchLocale(select().getLocale());
	}
}
