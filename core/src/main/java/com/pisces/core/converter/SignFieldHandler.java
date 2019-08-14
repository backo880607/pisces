package com.pisces.core.converter;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.DeserializationProblemHandler;
import com.fasterxml.jackson.databind.node.LongNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.pisces.core.entity.EntityObject;
import com.pisces.core.entity.Property;
import com.pisces.core.enums.PropertyType;
import com.pisces.core.relation.Ioc;
import com.pisces.core.utils.AppUtils;
import com.pisces.core.utils.EntityUtils;

public class SignFieldHandler extends DeserializationProblemHandler {

	@SuppressWarnings("unchecked")
	@Override
	public boolean handleUnknownProperty(DeserializationContext ctxt, JsonParser p, JsonDeserializer<?> deserializer,
			Object beanOrClass, String propertyName) throws IOException {
		if (EntityObject.class.isAssignableFrom(deserializer.handledType())) {
			Class<? extends EntityObject> clazz = (Class<? extends EntityObject>) deserializer.handledType();
			EntityObject entity = (EntityObject) beanOrClass;
			Property property = AppUtils.getPropertyService().get(clazz, propertyName);
			if (property != null) {
				if (property.sign != null) {
					return handleRelationProperty(entity, property, p);
				} else if (!property.getInherent()) {
					return handleUserProperty(entity, property, p);
				}
			}
		}
		
		return super.handleUnknownProperty(ctxt, p, deserializer, beanOrClass, propertyName);
	}
	
	private EntityObject getRelaEntity(JsonNode node, Class<? extends EntityObject> clazz) {
		EntityObject entity = null;
		long id = 0;
		if (node instanceof TextNode) {
			try {
				id = Long.valueOf(node.textValue());
			} catch (NumberFormatException e) {
				
			}
		} else if (node instanceof LongNode) {
			id = node.longValue();
		}
		if (id > 0) {
			try {
				entity = clazz.newInstance();
				entity.setId(id);
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return entity;
	}
	
	private boolean handleRelationProperty(EntityObject entity, Property property, JsonParser p) throws IOException {
		Class<? extends EntityObject> propertyClazz = property.sign.getEntityClass();
		try {
			if (property.getType() == PropertyType.Object) {
				EntityObject relaEntity = EntityUtils.defaultObjectMapper().readValue(p, propertyClazz);
				Ioc.set(entity, property.sign, relaEntity);
				return true;
			}
		} catch (JsonProcessingException ex) {
		}
		
		JsonNode node = p.getCodec().readTree(p);
		if (node.isArray()) {
			for (JsonNode subNode : node) {
				Ioc.set(entity, property.sign, getRelaEntity(subNode, propertyClazz));
			}
		} else {
			Ioc.set(entity, property.sign, getRelaEntity(node, propertyClazz));
		}
		
		return true;
	}
	
	private boolean handleUserProperty(EntityObject entity, Property property, JsonParser p) throws IOException {
		if (property.getModifiable()) {
			JsonNode node = p.getCodec().readTree(p);
			EntityUtils.setTextValue(entity, property, node.textValue());
		}
		return true;
	}
}
