package com.pisces.core.converter;

import com.pisces.core.entity.BaseDeserializer;
import com.pisces.core.entity.Property;

public class LongDeserializer extends BaseDeserializer<Long> {

	@Override
	public Long deserialize(Property property, String value) {
		return Long.valueOf(value);
	}

}
