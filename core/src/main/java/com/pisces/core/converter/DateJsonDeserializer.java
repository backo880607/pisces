package com.pisces.core.converter;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.pisces.core.utils.DateUtils;

public class DateJsonDeserializer extends JsonDeserializer<Date> {
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS");

	@Override
	public Date deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		try {
			return sdf.parse(p.getText());
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public Date getNullValue(DeserializationContext ctxt) throws JsonMappingException {
		return DateUtils.INVALID_DATE;
	}
}
