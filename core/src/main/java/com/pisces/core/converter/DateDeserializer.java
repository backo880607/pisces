package com.pisces.core.converter;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.pisces.core.utils.DateUtils;

public class DateDeserializer extends JsonDeserializer<Date> {
	
	@Override
	public Date deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		try {
			return DateUtils.parse(p.getText(), DateUtils.DATE_FORMAT);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public Date getNullValue(DeserializationContext ctxt) throws JsonMappingException {
		return DateUtils.INVALID;
	}
}
