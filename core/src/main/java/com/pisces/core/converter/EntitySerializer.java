package com.pisces.core.converter;


import com.pisces.core.entity.BaseSerializer;
import com.pisces.core.entity.EntityObject;

public class EntitySerializer extends BaseSerializer<EntityObject> {

	@Override
	public String serialize(EntityObject value) {
		return value.getId().toString();
	}

}
