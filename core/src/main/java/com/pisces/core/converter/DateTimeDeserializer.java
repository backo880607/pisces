package com.pisces.core.converter;

import java.text.ParseException;
import java.util.Date;

import com.pisces.core.entity.BaseDeserializer;
import com.pisces.core.entity.Property;
import com.pisces.core.utils.DateUtils;

public class DateTimeDeserializer extends BaseDeserializer<Date> {

	@Override
	public Date deserialize(Property property, String value) {
		try {
			return DateUtils.parse(value);
		} catch (ParseException e) {
			throw new RuntimeException(e.getMessage());
		}
	}
}
