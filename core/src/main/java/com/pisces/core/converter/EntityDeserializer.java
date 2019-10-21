package com.pisces.core.converter;

import com.pisces.core.entity.BaseDeserializer;
import com.pisces.core.entity.EntityObject;
import com.pisces.core.entity.Property;

public class EntityDeserializer extends BaseDeserializer<EntityObject> {

	@Override
	public EntityObject deserialize(Property property, String value) {
		EntityObject entity = null;
		try {
			long id = Long.valueOf(value);
			entity = (EntityObject)property.clazz.newInstance();
			entity.setId(id);
			return entity;
		} catch (NumberFormatException e) {
			
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return entity;
	}

}
