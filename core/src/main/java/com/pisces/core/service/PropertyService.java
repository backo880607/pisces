package com.pisces.core.service;

import java.util.List;

import com.pisces.core.entity.EntityObject;
import com.pisces.core.entity.Property;
import com.pisces.core.service.EntityService;

public interface PropertyService extends EntityService<Property> {
	public List<Property> get(Class<? extends EntityObject> clazz);
	public Property get(Class<? extends EntityObject> clazz, String code);
	public List<Property> getPrimaries(Class<? extends EntityObject> clazz);
	public List<Property> getVisiables(Class<? extends EntityObject> clazz);
}
