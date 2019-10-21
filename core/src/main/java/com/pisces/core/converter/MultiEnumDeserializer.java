package com.pisces.core.converter;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.exc.InvalidTypeIdException;
import com.pisces.core.entity.BaseDeserializer;
import com.pisces.core.entity.MultiEnum;
import com.pisces.core.entity.Property;

public class MultiEnumDeserializer extends BaseDeserializer<MultiEnum<? extends Enum<?>>> implements ContextualDeserializer {
	private Class<? extends MultiEnum<? extends Enum<?>>> clazz;

	@Override
	public MultiEnum<? extends Enum<?>> deserialize(JsonParser p, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		MultiEnum<? extends Enum<?>> result = null;
		try {
			result = clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new InvalidTypeIdException(p, e.getMessage(), null, clazz.getName());
		}
		result.parse(p.getText());
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property)
			throws JsonMappingException {
		Class<? extends MultiEnum<? extends Enum<?>>> rawCls = (Class<? extends MultiEnum<? extends Enum<?>>>) ctxt.getContextualType().getRawClass();
		MultiEnumDeserializer deser = new MultiEnumDeserializer();
		deser.clazz = rawCls;
		return deser;
	}

	@SuppressWarnings("unchecked")
	@Override
	public MultiEnum<? extends Enum<?>> deserialize(Property property, String value) {
		MultiEnum<? extends Enum<?>> result = null;
		try {
			result = (MultiEnum<? extends Enum<?>>)property.clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
		}
		result.parse(value);
		return result;
	}
}
