package com.pisces.platform.framework.service.system.impl;

import com.pisces.platform.core.entity.EntityObject;
import com.pisces.platform.core.entity.Property;
import com.pisces.platform.core.service.EntityServiceImpl;
import com.pisces.platform.core.service.PropertyService;
import com.pisces.platform.framework.dao.system.PropertyDao;
import org.springframework.stereotype.Service;

import java.util.List;

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
