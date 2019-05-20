package com.pisces.core.service;

import java.util.List;

import com.pisces.core.entity.EntityObject;
import com.pisces.core.entity.Property;
import com.pisces.core.service.EntityService;

public interface PropertyService extends EntityService<Property> {
	public Property getByCode(Class<? extends EntityObject> clazz, String code);
	public List<Property> getByClass(Class<? extends EntityObject> clazz);
}
