package com.pisces.core.primary.expression.calculate;

import com.pisces.core.entity.EntityObject;

public class DoubleCalculate implements Calculate {
	public double value;
	
	@Override
	public Object GetValue(EntityObject entity) {
		return value;
	}
	
	@Override
	public int Parse(String str, int index) {
		return -1;
	}

	@Override
	public Class<?> getReturnClass() {
		return Double.class;
	}
}
