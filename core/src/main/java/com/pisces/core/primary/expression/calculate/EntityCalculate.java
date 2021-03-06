package com.pisces.core.primary.expression.calculate;

import com.pisces.core.entity.EntityObject;
import com.pisces.core.utils.EntityUtils;

public class EntityCalculate implements Calculate {
	private Class<?> clazz;
	
	@Override
	public Object GetValue(EntityObject entity) {
		return entity;
	}
	
	@Override
	public int Parse(String str, int index) {
		int temp = index;
		while (index < str.length()) {
			char curChar = str.charAt(index);
			if (!Character.isAlphabetic(curChar) && !Character.isDigit(curChar)) {
				String name = str.substring(temp, index);
				clazz = EntityUtils.getEntityClass(name);
				if (clazz == null) {
					return -1;
				}
				return index;
			}
			++index;
		}
		
		return -1;
	}

	@Override
	public Class<?> getReturnClass() {
		return clazz;
	}
}
