package com.pisces.core.converter;

import com.pisces.core.entity.BaseDeserializer;
import com.pisces.core.entity.Property;

public class DoubleDeserializer extends BaseDeserializer<Double> {

	@Override
	public Double deserialize(Property property, String value) {
		return Double.valueOf(value);
	}

}
