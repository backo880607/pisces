package com.pisces.core.converter;

import com.pisces.core.entity.BaseDeserializer;
import com.pisces.core.entity.Property;

public class BooleanDeserializer extends BaseDeserializer<Boolean> {

	@Override
	public Boolean deserialize(Property property, String value) {
		return Boolean.valueOf(value);
	}

}
