package com.pisces.platform.core.entity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pisces.platform.core.enums.PROPERTY_TYPE;
import com.pisces.platform.core.utils.EntityUtils;

import java.util.HashMap;
import java.util.Map;

public class EntityMapper extends ObjectMapper {
    private static final long serialVersionUID = 415052758487294871L;

    private EntitySerializerModifier serModifier = new EntitySerializerModifier();
    private EntityDeserializerModifier desModifier = new EntityDeserializerModifier();

    public void bind() {
        setSerializerFactory(getSerializerFactory().withSerializerModifier(this.serModifier));
    }

    @SuppressWarnings("unchecked")
    public <T extends EntityObject> void registerkSerializer(Class<T> clazz, BaseSerializer<?> serializer) {
        this.serModifier.clazzSerializers.put(clazz, (BaseSerializer<Object>) serializer);
    }

    @SuppressWarnings("unchecked")
    public void registerSerializer(PROPERTY_TYPE type, BaseSerializer<?> serializer) {
        this.serModifier.typeSerializers.put(type, (BaseSerializer<Object>) serializer);
    }

    @SuppressWarnings("unchecked")
    public <T extends EntityObject> void registerSerializer(Class<T> clazz, String fieldName, BaseSerializer<?> serializer) {
        Map<String, BaseSerializer<Object>> beanSerializers = this.serModifier.fieldSerializers.get(clazz);
        if (beanSerializers == null) {
            beanSerializers = new HashMap<>();
            this.serModifier.fieldSerializers.put(clazz, beanSerializers);
        }
        beanSerializers.put(fieldName, (BaseSerializer<Object>) serializer);
    }

    public <T extends EntityObject> void registerDeserializer(Class<T> clazz, BaseDeserializer<?> deserializer) {
        this.desModifier.clazzDeserializers.put(clazz, (BaseDeserializer<Object>) deserializer);
    }

    public void registerDeserializer(PROPERTY_TYPE type, BaseDeserializer<?> deserializer) {
        this.desModifier.typeDeserializers.put(type, (BaseDeserializer<Object>) deserializer);
    }

    public <T extends EntityObject> void registerDeserializer(Class<T> clazz, String fieldName, BaseDeserializer<?> deserializer) {
        Map<String, BaseDeserializer<Object>> entityDeserializers = this.desModifier.fieldDeserializers.get(clazz);
        if (entityDeserializers == null) {
            entityDeserializers = new HashMap<>();
            this.desModifier.fieldDeserializers.put(clazz, entityDeserializers);
        }
        entityDeserializers.put(fieldName, (BaseDeserializer<Object>) deserializer);
    }

    public String getTextValue(EntityObject entity, Property property) {
        Object value = EntityUtils.getValue(entity, property);
        if (value == null) {
            return "";
        }

        return this.serModifier.serialize(property, value);
    }

    public void setTextValue(EntityObject entity, Property property, String text) {
        EntityUtils.setValue(entity, property, convertTextValue(property, text));
    }

    public Object convertTextValue(Property property, String text) {
        return this.desModifier.deserialize(property, text);
    }
}
