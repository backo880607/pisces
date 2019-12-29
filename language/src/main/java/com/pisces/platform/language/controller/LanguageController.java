package com.pisces.platform.language.controller;

import com.pisces.platform.language.bean.Language;
import com.pisces.platform.language.service.LanguageService;
import com.pisces.platform.web.controller.EntityController;
import com.pisces.platform.web.controller.ResponseData;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
@RequestMapping("/language")
public class LanguageController extends EntityController<Language, LanguageService> {

	@RequestMapping("switchLocale")
	public ResponseData switchLocale(String lang, String country) {
		getService().switchLocale(new Locale(lang, country));
		return succeed();
	}
}
