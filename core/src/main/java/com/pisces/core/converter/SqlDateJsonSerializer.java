package com.pisces.core.converter;

import java.sql.Date;

import com.pisces.core.entity.BaseSerializer;
import com.pisces.core.utils.DateUtils;

public class SqlDateJsonSerializer extends BaseSerializer<Date> {

	@Override
	public String serialize(Date value) {
		return DateUtils.format(value);
	}

}
