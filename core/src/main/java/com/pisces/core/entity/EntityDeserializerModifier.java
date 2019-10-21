package com.pisces.core.entity;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier;
import com.pisces.core.converter.BooleanDeserializer;
import com.pisces.core.converter.DateDeserializer;
import com.pisces.core.converter.DateTimeDeserializer;
import com.pisces.core.converter.DoubleDeserializer;
import com.pisces.core.converter.DurationDeserializer;
import com.pisces.core.converter.EntityDeserializer;
import com.pisces.core.converter.EntityListDeserializer;
import com.pisces.core.converter.EnumDeserializer;
import com.pisces.core.converter.LongDeserializer;
import com.pisces.core.converter.MultiEnumDeserializer;
import com.pisces.core.converter.StringDeserializer;
import com.pisces.core.converter.TimeDeserializer;
import com.pisces.core.enums.PROPERTY_TYPE;

public class EntityDeserializerModifier extends BeanDeserializerModifier {
	private static Map<PROPERTY_TYPE, BaseDeserializer<? extends Object>> defaultDeserializers = new HashMap<>();
	Map<Class<?>, BaseDeserializer<? extends Object>> clazzDeserializers = new HashMap<>();
	Map<PROPERTY_TYPE, BaseDeserializer<? extends Object>> typeDeserializers = new HashMap<>();
	Map<Class<? extends EntityObject>, Map<String, BaseDeserializer<? extends Object>>> fieldDeserializers = new HashMap<>();
	
	static {
		defaultDeserializers.put(PROPERTY_TYPE.BOOLEAN, new BooleanDeserializer());
		defaultDeserializers.put(PROPERTY_TYPE.LONG, new LongDeserializer());
		defaultDeserializers.put(PROPERTY_TYPE.DOUBLE, new DoubleDeserializer());
		defaultDeserializers.put(PROPERTY_TYPE.DATE, new DateDeserializer());
		defaultDeserializers.put(PROPERTY_TYPE.TIME, new TimeDeserializer());
		defaultDeserializers.put(PROPERTY_TYPE.DATE_TIME, new DateTimeDeserializer());
		defaultDeserializers.put(PROPERTY_TYPE.DURATION, new DurationDeserializer());
		defaultDeserializers.put(PROPERTY_TYPE.ENUM, new EnumDeserializer());
		defaultDeserializers.put(PROPERTY_TYPE.MULTI_ENUM, new MultiEnumDeserializer());
		defaultDeserializers.put(PROPERTY_TYPE.STRING, new StringDeserializer());
		defaultDeserializers.put(PROPERTY_TYPE.ENTITY, new EntityDeserializer());
		defaultDeserializers.put(PROPERTY_TYPE.LIST, new EntityListDeserializer());
	}
	
	public BaseDeserializer<? extends Object> getDeserializer(Property property) {
		BaseDeserializer<? extends Object> deserializer = null;
		if (property == null) {
			return deserializer;
		}
		
		Map<String, BaseDeserializer<? extends Object>> fieldDeserializer = fieldDeserializers.get(property.belongClazz);
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
		
		return deserializer;
	}
	
	public Object deserialize(Property property, String text) {
		BaseDeserializer<? extends Object> deserializer = getDeserializer(property);
		if (deserializer == null) {
			deserializer = defaultDeserializers.get(property.getType());
		}
		
		return deserializer != null ? deserializer.deserialize(property, text) : null;
	}
}
