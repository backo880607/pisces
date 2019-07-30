package com.pisces.core.locale;

import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import com.pisces.core.utils.AppUtils;

public class LocaleManager {
	private static ILanguageManager languageManager;
	
	private static ILanguageManager getLangugeManager() {
		if (languageManager == null) {
			synchronized (LocaleManager.class) {
				if (languageManager == null) {
					Map<String, ILanguageManager> mgrs = AppUtils.getBeansOfType(ILanguageManager.class);
					for (Entry<String, ILanguageManager> entry : mgrs.entrySet()) {
						languageManager = entry.getValue();
						break;
					}
				}
			}
		}
		
		return languageManager;
	}
	
	public static String getLanguage(Enum<?> key, Object... arguments) {
		if (getLangugeManager() == null) {
			return "";
		}
		
		return getLangugeManager().get(key, arguments);
	}
	
	public static void init() {
		if (getLangugeManager() != null) {
			getLangugeManager().init();
		}
	}
	
	public static Locale getLocale() {
		if (getLangugeManager() != null) {
			return getLangugeManager().getLocale();
		}
		
		return Locale.getDefault();
	}
}
