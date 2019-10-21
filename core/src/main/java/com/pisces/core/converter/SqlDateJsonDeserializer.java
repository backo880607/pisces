package com.pisces.core.converter;

import java.sql.Date;
import java.text.ParseException;

import com.pisces.core.entity.BaseDeserializer;
import com.pisces.core.entity.Property;
import com.pisces.core.utils.DateUtils;

public class SqlDateJsonDeserializer extends BaseDeserializer<Date> {

	@Override
	public Date deserialize(Property property, String value) {
		try {
			return new Date(DateUtils.parse(value).getTime());
		} catch (ParseException e) {
			throw new RuntimeException(e.getMessage());
		}
	}
}
