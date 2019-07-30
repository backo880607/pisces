package com.pisces.language.bean;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.ibatis.type.JdbcType;

import com.pisces.core.entity.EntityObject;
import com.pisces.rds.handler.LocaleHandler;

import tk.mybatis.mapper.annotation.ColumnType;

public class Language extends EntityObject {
	public static final String ERROR = "#error#";
	@ColumnType(jdbcType = JdbcType.VARCHAR, typeHandler = LocaleHandler.class)
	private Locale locale = Locale.getDefault();
	private transient List<ResourceBundle> bundles = new ArrayList<>();
	
	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}
	
	public void bind(String name) {
		bundles.add(ResourceBundle.getBundle("language." + name, getLocale()));
	}
	
	public void clearBundles() {
		bundles.clear();
	}
	
	public String get(String key, Object... arguments) {
		for (ResourceBundle bundle : bundles) {
			try {
				String pattern = bundle.getString(key);
				if (arguments.length == 0) {
					return pattern;
				}
				MessageFormat format = new MessageFormat(pattern, getLocale());
				return format.format(arguments);
			} catch (Exception e) {
			}
		}
		
		return key;
	}
}
