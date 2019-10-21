package com.pisces.core.entity;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public abstract class BaseDeserializer<T> extends JsonDeserializer<T> {
	
	@Override
	public T deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		return deserialize(null, p.getText());
	}
	
	public abstract T deserialize(Property property, String value);
}
