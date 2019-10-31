package com.pisces.core.primary.expression.calculate;

import com.pisces.core.entity.EntityObject;

public class BooleanCalculate implements Calculate {
	private boolean value;

	@Override
	public Object GetValue(EntityObject entity) {
		return value;
	}

	@Override
	public int Parse(String str, int index) {
		return 0;
	}

	@Override
	public Class<?> getReturnClass() {
		return Boolean.class;
	}
}
