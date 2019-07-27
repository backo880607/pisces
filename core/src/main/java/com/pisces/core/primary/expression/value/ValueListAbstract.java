package com.pisces.core.primary.expression.value;

import java.util.HashMap;
import java.util.Map;

public class ValueListAbstract extends ValueAbstract {
	public Map<Long, ValueAbstract> value;

	public ValueListAbstract() {
		this.value = new HashMap<>();
	}

	@Override
	public Type getType() {
		return Type.ListAbstract;
	}
	
	@Override
	public Class<?> getReturnClass() {
		return Map.class;
	}
	
	@Override
	public Object getValue() {
		return this.value;
	}

	@Override
	public ValueText toText() {
		return new ValueText("");
	}
}
