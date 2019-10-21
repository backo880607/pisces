package com.pisces.core.converter;

import java.util.Collection;

import com.pisces.core.entity.BaseSerializer;
import com.pisces.core.entity.EntityObject;
import com.pisces.core.utils.StringUtils;

public class EntityListSerializer extends BaseSerializer<Collection<EntityObject>> {

	@Override
	public String serialize(Collection<EntityObject> value) {
		return StringUtils.join(value, ";", (EntityObject entity) -> {
			return entity.getId().toString();
		});
	}

}
