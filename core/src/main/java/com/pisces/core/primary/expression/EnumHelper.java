package com.pisces.core.primary.expression;

import java.util.HashMap;
import java.util.Map;

public final class EnumHelper {
	private static Map<String, Class<?>> s_enumHelper = new HashMap<>();
	
	public static void register(String name) {
		Class<?> enumCls = null;
		try {
			enumCls = Class.forName(name);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return;
		}
		if (enumCls != null) {
			s_enumHelper.put(enumCls.getSimpleName(), enumCls);
		}
	}
	
	public static void registerAll(String packPath) {
		/*List<Class<?>> clses = MWTools.loadClass(packPath);
		for (Class<?> cls : clses) {
			if (Enum.class.isAssignableFrom(cls)) {
				s_enumHelper.put(cls.getSimpleName(), cls);
			}
		}*/
	}
	
	public static Class<?> get(String name) {
		return s_enumHelper.get(name);
	}
}
