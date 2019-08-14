package com.pisces.core.primary.expression;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.pisces.core.BasicProperties;
import com.pisces.core.utils.AppUtils;
import com.pisces.core.utils.FileUtils;

public final class EnumHelper {
	private static Map<String, Class<?>> s_enumHelper = new HashMap<>();
	
	public static void init() {
		Map<String, BasicProperties> configs = AppUtils.getBeansOfType(BasicProperties.class);
		for (Entry<String, BasicProperties> entry : configs.entrySet()) {
			BasicProperties property = entry.getValue();
			if (property.getEnumPath() != null) {
				for (String enumPath : property.getEnumPath().split(";")) {
					if (enumPath.isEmpty()) {
						continue;
					}
					
					register(enumPath);
				}
			}
		}
	}
	
	public static void register(String packPath) {
		for (Class<?> cls : FileUtils.loadClass(packPath)) {
			if (Enum.class.isAssignableFrom(cls)) {
				s_enumHelper.put(cls.getSimpleName(), cls);
			}
		}
	}
	
	public static Class<?> get(String name) {
		return s_enumHelper.get(name);
	}
}
