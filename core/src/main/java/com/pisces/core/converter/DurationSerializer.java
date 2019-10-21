package com.pisces.core.converter;

import com.pisces.core.entity.BaseSerializer;
import com.pisces.core.entity.DateDur;

public class DurationSerializer extends BaseSerializer<DateDur> {
	
	@Override
	public String serialize(DateDur value) {
		return value.getString();
	}

}
