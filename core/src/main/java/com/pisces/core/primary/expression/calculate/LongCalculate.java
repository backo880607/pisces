package com.pisces.core.primary.expression.calculate;

import com.pisces.core.entity.EntityObject;
import com.pisces.core.primary.expression.value.ValueAbstract;
import com.pisces.core.primary.expression.value.ValueInt;

public class LongCalculate implements Calculate {
	public ValueInt value;
	
	@Override
	public ValueAbstract GetValue(EntityObject entity) {
		return value;
	}
	
	@Override
	public int Parse(String str, int index) {
		return -1;
	}

	@Override
	public Class<?> getReturnClass() {
		return Long.class;
	}
}
