package com.pisces.core.converter;

import com.pisces.core.entity.BaseDeserializer;
import com.pisces.core.entity.DateDur;
import com.pisces.core.entity.Property;

public class DurationDeserializer extends BaseDeserializer<DateDur> {

	@Override
	public DateDur deserialize(Property property, String value) {
		return new DateDur(value);
	}
}
