package com.pisces.platform.core.entity;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.deser.BeanDeserializerBuilder;
import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import com.pisces.platform.core.converter.*;
import com.pisces.platform.core.enums.PROPERTY_TYPE;
import com.pisces.platform.core.service.PropertyService;
import com.pisces.platform.core.utils.AppUtils;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class EntityDeserializerModifier extends BeanDeserializerModifier {
    private static Map<PROPERTY_TYPE, BaseDeserializer<? extends Object>> defaultDeserializers = new HashMap<>();
    Map<Class<?>, BaseDeserializer<Object>> clazzDeserializers = new HashMap<>();
    Map<PROPERTY_TYPE, BaseDeserializer<Object>> typeDeserializers = new HashMap<>();
    Map<Class<? extends EntityObject>, Map<String, BaseDeserializer<Object>>> fieldDeserializers = new HashMap<>();

    static {
        defaultDeserializers.put(PROPERTY_TYPE.DATE, new DateDeserializer());
        defaultDeserializers.put(PROPERTY_TYPE.TIME, new TimeDeserializer());
        defaultDeserializers.put(PROPERTY_TYPE.DATE_TIME, new DateTimeDeserializer());
        defaultDeserializers.put(PROPERTY_TYPE.DURATION, new DurationDeserializer());
        defaultDeserializers.put(PROPERTY_TYPE.MULTI_ENUM, new MultiEnumDeserializer());
        defaultDeserializers.put(PROPERTY_TYPE.ENTITY, new EntityDeserializer());
        defaultDeserializers.put(PROPERTY_TYPE.LIST, new EntityListDeserializer());
    }

    public BaseDeserializer<Object> getDeserializer(Property property) {
        if (property == null) {
            return null;
        }

        BaseDeserializer<Object> deserializer = null;
        Map<String, BaseDeserializer<Object>> fieldDeserializer = fieldDeserializers.get(property.belongClazz);
        if (fieldDeserializer != null) {
            deserializer = fieldDeserializer.get(property.getCode());
        }
        if (deserializer != null) {
            return deserializer;
        }

        deserializer = typeDeserializers.get(property.getType());
        if (deserializer != null) {
            return deserializer;
        }

        Class<?> temp = property.clazz;
        while (temp != Object.class) {
            deserializer = clazzDeserializers.get(temp);
            if (deserializer != null) {
                break;
            }
            temp = temp.getSuperclass();
        }
        if (deserializer != null) {
            return deserializer;
        }

        return (BaseDeserializer<Object>) defaultDeserializers.get(property.getType());
    }

    public Object deserialize(Property property, String text) {
        BaseDeserializer<? extends Object> deserializer = getDeserializer(property);
        return deserializer != null ? deserializer.deserialize(property, text) : deserializeDefault(property, text);
    }

    private Object deserializeDefault(Property property, String text) {
        switch (property.getType()) {
            case BOOLEAN:
                return Boolean.valueOf(text);
            case SHORT:
                return Short.valueOf(text);
            case INTEGER:
                return Integer.valueOf(text);
            case LONG:
                return Long.valueOf(text);
            case DOUBLE:
                return Double.valueOf(text);
            case ENUM:
                return Enum.valueOf((Class<? extends Enum>) property.clazz, text);
        }

        throw new UnsupportedOperationException();
    }

    @Override
    public BeanDeserializerBuilder updateBuilder(DeserializationConfig config, BeanDescription beanDesc, BeanDeserializerBuilder builder) {
        if (EntityObject.class.isAssignableFrom(beanDesc.getBeanClass())) {
            PropertyService propertyService = AppUtils.getPropertyService();
            Class<EntityObject> clazz = (Class<EntityObject>) beanDesc.getBeanClass();
            Iterator<SettableBeanProperty> iter = builder.getProperties();
            while (iter.hasNext()) {
                SettableBeanProperty setProperty = iter.next();
                Property property = propertyService.get(clazz, setProperty.getName());
                BaseDeserializer<Object> deserializer = getDeserializer(property);
                if (deserializer != null) {
                    try {
                        deserializer = (BaseDeserializer<Object>) BeanUtils.cloneBean(deserializer);
                    } catch (IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                    deserializer.property = property;
                    setProperty = setProperty.withValueDeserializer(deserializer);
                    builder.addOrReplaceProperty(setProperty, true);
                }
            }
        }

        return builder;
    }
}
