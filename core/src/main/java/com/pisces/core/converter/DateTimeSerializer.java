package com.pisces.core.converter;

import java.util.Date;

import com.pisces.core.entity.BaseSerializer;
import com.pisces.core.utils.DateUtils;

public class DateTimeSerializer extends BaseSerializer<Date> {
	
	@Override
	public String serialize(Date value) {
		return DateUtils.format(value);
	}
}