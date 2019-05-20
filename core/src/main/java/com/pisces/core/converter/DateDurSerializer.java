package com.pisces.core.converter;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.pisces.core.entity.DateDur;

public class DateDurSerializer extends JsonSerializer<DateDur> {

	@Override
	public void serialize(DateDur value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		gen.writeString(value.getString());
	}

}
