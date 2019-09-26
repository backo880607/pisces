package com.pisces.platform.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pisces.core.entity.EntityObject;
import com.pisces.core.entity.Property;
import com.pisces.core.service.EntityServiceImpl;
import com.pisces.core.service.PropertyService;
import com.pisces.platform.dao.PropertyDao;

@Service
class PropertyServiceImpl extends EntityServiceImpl<Property, PropertyDao> implements PropertyService {
	
	@Override
	public List<Property> get(Class<? extends EntityObject> clazz) {
		return getDao().get(clazz);
	}
	
	@Override
	public Property get(Class<? extends EntityObject> clazz, String code) {
		return getDao().get(clazz, code);
	}
	
	@Override
	public List<Property> getPrimaries(Class<? extends EntityObject> clazz) {
		return getDao().getPrimaries(clazz);
	}
}
