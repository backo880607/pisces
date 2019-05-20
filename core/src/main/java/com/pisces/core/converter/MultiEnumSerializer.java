package com.pisces.core.converter;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.pisces.core.entity.MultiEnum;

public class MultiEnumSerializer extends JsonSerializer<MultiEnum<? extends Enum<?>>> {

	@Override
	public void serialize(MultiEnum<? extends Enum<?>> value, JsonGenerator gen, SerializerProvider serializers)
			throws IOException {
		gen.writeString(value.toString());
	}
}
