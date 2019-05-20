package com.pisces.language.controller;

import java.util.Locale;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pisces.language.bean.Language;
import com.pisces.language.service.LanguageService;
import com.pisces.web.controller.EntityController;
import com.pisces.web.controller.ResponseData;

@RestController
@RequestMapping("/language")
public class LanguageController extends EntityController<Language, LanguageService> {

	@RequestMapping("switchLocale")
	public ResponseData switchLocale(String lang, String country) {
		getService().switchLocale(new Locale(lang, country));
		return succeed();
	}
}
