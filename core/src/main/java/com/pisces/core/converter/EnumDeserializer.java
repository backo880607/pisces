package com.pisces.core.converter;

import com.pisces.core.entity.BaseDeserializer;
import com.pisces.core.entity.Property;

public class EnumDeserializer extends BaseDeserializer<Enum<?>> {

	@Override
	public Enum<?> deserialize(Property property, String value) {
		return null;
	}

}
