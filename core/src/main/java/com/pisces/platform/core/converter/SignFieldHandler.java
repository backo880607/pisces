package com.pisces.platform.core.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.DeserializationProblemHandler;
import com.pisces.platform.core.entity.EntityMapper;
import com.pisces.platform.core.entity.EntityObject;
import com.pisces.platform.core.entity.Property;
import com.pisces.platform.core.utils.AppUtils;

import java.io.IOException;

public class SignFieldHandler extends DeserializationProblemHandler {
    private EntityMapper mapper;

    public SignFieldHandler(EntityMapper value) {
        this.mapper = value;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean handleUnknownProperty(DeserializationContext ctxt, JsonParser p, JsonDeserializer<?> deserializer,
                                         Object beanOrClass, String propertyName) throws IOException {
        if (EntityObject.class.isAssignableFrom(deserializer.handledType())) {
            Class<? extends EntityObject> clazz = (Class<? extends EntityObject>) deserializer.handledType();
            Property property = AppUtils.getPropertyService().get(clazz, propertyName);
            if (property != null) {
                EntityObject entity = (EntityObject) beanOrClass;
                JsonNode node = p.getCodec().readTree(p);
                mapper.setTextValue(entity, property, node.textValue());
                return true;
            }
        }

        return super.handleUnknownProperty(ctxt, p, deserializer, beanOrClass, propertyName);
    }
}
