package com.pisces.core.converter;

import com.pisces.core.entity.BaseDeserializer;
import com.pisces.core.entity.Property;

public class StringDeserializer extends BaseDeserializer<String> {

	@Override
	public String deserialize(Property property, String value) {
		return value;
	}

}
