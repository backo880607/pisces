package com.pisces.core.entity;

import java.util.HashMap;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pisces.core.enums.PROPERTY_TYPE;
import com.pisces.core.utils.EntityUtils;

public class EntityMapper extends ObjectMapper {
	private static final long serialVersionUID = 415052758487294871L;
	
	private EntitySerializerModifier serModifier = new EntitySerializerModifier();
	private EntityDeserializerModifier desModifier = new EntityDeserializerModifier();

	public void bind() {
		setSerializerFactory(getSerializerFactory().withSerializerModifier(this.serModifier));
	}

	@SuppressWarnings("unchecked")
	public <T> void register(Class<T> clazz, BaseSerializer<?> serializer) {
		if (clazz == null || serializer == null) {
			return;
		}
		
		this.serModifier.clazzSerializers.put(clazz, (BaseSerializer<Object>)serializer);
		serializer.modifier = this.serModifier;
	}
	
	@SuppressWarnings("unchecked")
	public void register(PROPERTY_TYPE type, BaseSerializer<?> serializer) {
		if (type == null || serializer == null) {
			return;
		}
		this.serModifier.typeSerializers.put(type, (BaseSerializer<Object>)serializer);
		serializer.modifier = this.serModifier;
	}
	
	@SuppressWarnings("unchecked")
	public void register(Class<? extends EntityObject> clazz, String fieldName, BaseSerializer<?> serializer) {
		if (clazz == null || StringUtils.isEmpty(fieldName) || serializer == null) {
			return;
		}
		Map<String, BaseSerializer<Object>> beanSerializers = this.serModifier.fieldSerializers.get(clazz);
		if (beanSerializers == null) {
			beanSerializers = new HashMap<>();
			this.serModifier.fieldSerializers.put(clazz, beanSerializers);
		}
		beanSerializers.put(fieldName, (BaseSerializer<Object>)serializer);
		serializer.modifier = this.serModifier;
	}
	
	public String getTextValue(EntityObject entity, Property property) {
		Object value = EntityUtils.getValue(entity, property);
		if (value == null) {
			return "";
		}
		
		return this.serModifier != null ? this.serModifier.serialize(property, value) : "";
	}
	
	public void setTextValue(EntityObject entity, Property property, String text) {
		EntityUtils.setValue(entity, property, convertTextValue(property, text));
	}
	
	public Object convertTextValue(Property property, String text) {
		return this.desModifier != null ? this.desModifier.deserialize(property, text) : null;
	}
}
