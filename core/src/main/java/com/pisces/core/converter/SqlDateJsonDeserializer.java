package com.pisces.core.converter;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.pisces.core.utils.DateUtils;

public class SqlDateJsonDeserializer extends JsonDeserializer<Date> {
	@Override
	public Date deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		try {
			return new Date(DateUtils.parse(p.getText()).getTime());
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
}
