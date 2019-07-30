package com.pisces.core.converter;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.pisces.core.entity.DateDur;

public class DateDurDeserializer extends JsonDeserializer<DateDur> {

	@Override
	public DateDur deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		return new DateDur(p.getText());
	}

	@Override
	public DateDur getNullValue(DeserializationContext ctxt) throws JsonMappingException {
		return new DateDur("");
	}
}
