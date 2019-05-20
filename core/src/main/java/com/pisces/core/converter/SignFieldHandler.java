package com.pisces.core.converter;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.DeserializationProblemHandler;
import com.fasterxml.jackson.databind.node.LongNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.pisces.core.entity.EntityObject;
import com.pisces.core.entity.Property;
import com.pisces.core.exception.ExistedException;
import com.pisces.core.relation.Ioc;
import com.pisces.core.service.EntityService;
import com.pisces.core.service.ServiceManager;
import com.pisces.core.utils.EntityUtils;

public class SignFieldHandler extends DeserializationProblemHandler {

	@SuppressWarnings("unchecked")
	@Override
	public boolean handleUnknownProperty(DeserializationContext ctxt, JsonParser p, JsonDeserializer<?> deserializer,
			Object beanOrClass, String propertyName) throws IOException {
		if (EntityObject.class.isAssignableFrom(deserializer.handledType())) {
			Class<? extends EntityObject> clazz = (Class<? extends EntityObject>) deserializer.handledType();
			EntityObject entity = (EntityObject) beanOrClass;
			Property property = EntityUtils.getProperty(clazz, propertyName);
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
	
	private long getEntityId(JsonNode idNode) {
		long id = 0;
		if (idNode != null) {
			if (idNode instanceof TextNode) {
				id = Long.valueOf(idNode.textValue());
			} else if (idNode instanceof LongNode) {
				id = idNode.longValue();
			}
		}
		return id;
	}
	
	private boolean handleRelationProperty(EntityObject entity, Property property, JsonParser p) throws IOException {
		JsonNode node = p.getCodec().readTree(p);
		Class<? extends EntityObject> propertyClazz = property.sign.getEntityClass();
		EntityService<EntityObject> service = ServiceManager.getService(propertyClazz);
		if (node.isArray()) {
			for (JsonNode subNode : node) {
				if (subNode.has("id")) {
					JsonNode idNode = subNode.get("id");
					long id = 0;
					if (idNode instanceof TextNode) {
						id = Long.valueOf(idNode.textValue());
					} else if (idNode instanceof LongNode) {
						id = idNode.longValue();
					}
					if (id > 0) {
						EntityObject relaEntity = EntityUtils.getInherit(propertyClazz, id);
						Ioc.set(entity, property.sign, relaEntity);
						try {
							service.update(relaEntity);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				} else {
					List<Property> primaries = EntityUtils.getPrimaries(propertyClazz);
				}
			}
		} else if (node.isObject()) {
			final long id = getEntityId(node.get("id"));
			if (id > 0) {
				EntityObject relaEntity = EntityUtils.getInherit(propertyClazz, id);
				if (relaEntity == null) {
					throw new ExistedException(propertyClazz.getName() + " or subclass has not entity for id " + id);
				}
				Ioc.set(entity, property.sign, relaEntity);
			}
		}
		
		return true;
	}
	
	private boolean handleUserProperty(EntityObject entity, Property property, JsonParser p) throws IOException {
		JsonNode node = p.getCodec().readTree(p);
		EntityUtils.setValue(entity, property, node.textValue());
		return true;
	}
}
