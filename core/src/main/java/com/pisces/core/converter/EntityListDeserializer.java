package com.pisces.core.converter;

import java.util.ArrayList;
import java.util.Collection;

import com.pisces.core.entity.BaseDeserializer;
import com.pisces.core.entity.EntityObject;
import com.pisces.core.entity.Property;

public class EntityListDeserializer extends BaseDeserializer<Collection<EntityObject>> {

	@Override
	public Collection<EntityObject> deserialize(Property property, String value) {
		ArrayList<EntityObject> entities = new ArrayList<EntityObject>();
		for (String strId : value.split(";")) {
			EntityObject entity = null;
			try {
				long id = Long.valueOf(strId);
				entity = (EntityObject)property.clazz.newInstance();
				entity.setId(id);
				entities.add(entity);
			} catch (NumberFormatException e) {
				
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return entities;
	}

}
