package com.pisces.core.entity;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.pisces.core.utils.EntityUtils;

public abstract class BaseSerializer<T> extends JsonSerializer<T> {
	EntitySerializerModifier modifier;
	
	public abstract String serialize(T value);
	
	@Override
	public void serialize(T value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		String text = serialize(value);
		gen.writeString(text != null ? text : "");
	}
	
	public String getTextValue(EntityObject entity, Property property) {
		Object value = EntityUtils.getValue(entity, property);
		if (value == null) {
			return "";
		}
		
		return this.modifier != null ? this.modifier.serialize(property, value) : "";
	}
}
