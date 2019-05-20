package com.pisces.core.locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Message {
	private Logger log;
	public Message(Class<?> arg) {
		log = LoggerFactory.getLogger(arg);
	}
	
	private String getMessage(Enum<?> key, Object... arguments) {
		return LocaleManager.getLanguage(key, arguments);
	}
	
	public void debug(Enum<?> key, Object... arguments) {
		log.debug(getMessage(key, arguments));
	}
	
	public void info(Enum<?> key, Object... arguments) {
		log.info(getMessage(key, arguments));
	}
	
	public void warn(Enum<?> key, Object... arguments) {
		log.warn(getMessage(key, arguments));
	}
	
	public void error(Enum<?> key, Object... arguments) {
		log.error(getMessage(key, arguments));
	}
	
	public void tips_debug(Enum<?> key, Object... arguments) {
		debug(key, arguments);
	}
	
	public void tips_info(Enum<?> key, Object... arguments) {
		info(key, arguments);
	}
	
	public void tips_warn(Enum<?> key, Object... arguments) {
		warn(key, arguments);
	}
	
	public void tips_error(Enum<?> key, Object... arguments) {
		error(key, arguments);
	}
}
