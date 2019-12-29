package com.pisces.platform.core.entity;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public abstract class BaseSerializer<T> extends JsonSerializer<T> {
    @Override
    public final void serialize(T value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        String text = serialize(value);
        gen.writeString(text != null ? text : "");
    }

    public abstract String serialize(T value);
}
