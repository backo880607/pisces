package com.pisces.core.primary.expression.calculate;

import com.pisces.core.entity.EntityObject;
import com.pisces.core.primary.expression.value.ValueAbstract;
import com.pisces.core.primary.expression.value.ValueBoolean;

public class BooleanCalculate implements Calculate {
	private boolean value;

	@Override
	public ValueAbstract GetValue(EntityObject entity) {
		return new ValueBoolean(value);
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
