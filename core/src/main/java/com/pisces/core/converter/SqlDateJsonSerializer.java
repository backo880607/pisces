package com.pisces.core.converter;

import java.io.IOException;
import java.sql.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.pisces.core.utils.DateUtils;

public class SqlDateJsonSerializer extends JsonSerializer<Date> {
	@Override
	public void serialize(Date value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		gen.writeString(DateUtils.format(value));
	}

}
