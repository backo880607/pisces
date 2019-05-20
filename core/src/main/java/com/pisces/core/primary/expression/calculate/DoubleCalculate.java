package com.pisces.core.primary.expression.calculate;

import com.pisces.core.entity.EntityObject;
import com.pisces.core.primary.expression.value.ValueAbstract;
import com.pisces.core.primary.expression.value.ValueDouble;

public class DoubleCalculate implements Calculate {
	public double value;
	
	@Override
	public ValueAbstract GetValue(EntityObject entity) {
		return new ValueDouble(value);
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
