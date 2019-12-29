package com.pisces.platform.core.entity;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import com.pisces.platform.core.converter.*;
import com.pisces.platform.core.enums.PROPERTY_TYPE;
import com.pisces.platform.core.service.PropertyService;
import com.pisces.platform.core.utils.AppUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntitySerializerModifier extends BeanSerializerModifier {
    private static Map<PROPERTY_TYPE, BaseSerializer<?>> defaultSerializers = new HashMap<>();
    Map<Class<?>, BaseSerializer<Object>> clazzSerializers = new HashMap<>();
    Map<PROPERTY_TYPE, BaseSerializer<Object>> typeSerializers = new HashMap<>();
    Map<Class<? extends EntityObject>, Map<String, BaseSerializer<Object>>> fieldSerializers = new HashMap<>();

    static {
        defaultSerializers.put(PROPERTY_TYPE.DATE, new DateSerializer());
        defaultSerializers.put(PROPERTY_TYPE.TIME, new TimeSerializer());
        defaultSerializers.put(PROPERTY_TYPE.DATE_TIME, new DateTimeSerializer());
        defaultSerializers.put(PROPERTY_TYPE.DURATION, new DurationSerializer());
        defaultSerializers.put(PROPERTY_TYPE.MULTI_ENUM, new MultiEnumSerializer());
        defaultSerializers.put(PROPERTY_TYPE.ENTITY, new EntitySerializer());
        defaultSerializers.put(PROPERTY_TYPE.LIST, new EntityListSerializer());
    }

    public BaseSerializer<Object> getSerializer(Property property) {
        if (property == null) {
            return null;
        }

        BaseSerializer<Object> serializer = null;
        Map<String, BaseSerializer<Object>> fieldSerializer = fieldSerializers.get(property.belongClazz);
        if (fieldSerializer != null) {
            serializer = fieldSerializer.get(property.getCode());
        }
        if (serializer != null) {
            return serializer;
        }
        serializer = typeSerializers.get(property.getType());
        if (serializer != null) {
            return serializer;
        }

        Class<?> temp = property.clazz;
        while (temp != Object.class) {
            serializer = clazzSerializers.get(temp);
            if (serializer != null) {
                break;
            }
            temp = temp.getSuperclass();
        }
        if (serializer != null) {
            return serializer;
        }

        return (BaseSerializer<Object>) defaultSerializers.get(property.getType());
    }


    public String serialize(Property property, Object value) {
        BaseSerializer<Object> serializer = getSerializer(property);
        return serializer != null ? serializer.serialize(value) : value.toString();
    }

    @Override
    public List<BeanPropertyWriter> changeProperties(SerializationConfig config, BeanDescription beanDesc,
                                                     List<BeanPropertyWriter> beanProperties) {
        if (EntityObject.class.isAssignableFrom(beanDesc.getBeanClass())) {
            PropertyService propertyService = AppUtils.getPropertyService();
            @SuppressWarnings("unchecked")
            Class<EntityObject> clazz = (Class<EntityObject>) beanDesc.getBeanClass();
            for (BeanPropertyWriter writer : beanProperties) {
                Property property = propertyService.get(clazz, writer.getName());
                JsonSerializer<Object> serializer = getSerializer(property);
                if (serializer != null) {
                    writer.assignSerializer(serializer);
                }
            }
            List<Property> properties = propertyService.get(clazz);
            for (Property property : properties) {
                if (!property.getInherent()) {
                    BeanPropertyWriter writer = new UserFieldWriter();
                    JsonSerializer<Object> serializer = getSerializer(property);
                    if (serializer != null) {
                        writer.assignSerializer(serializer);
                    }
                    beanProperties.add(new UserFieldWriter());
                }
            }
        }
        return super.changeProperties(config, beanDesc, beanProperties);
    }

    public static class UserFieldWriter extends BeanPropertyWriter {
        private static final long serialVersionUID = 5358862065792784550L;

        @Override
        public void fixAccess(SerializationConfig config) {
        }
    }
}
