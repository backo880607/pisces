package com.pisces.platform.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pisces.core.entity.EntityObject;
import com.pisces.core.entity.Property;
import com.pisces.core.service.EntityServiceImpl;
import com.pisces.core.service.PropertyService;
import com.pisces.core.utils.EntityUtils;
import com.pisces.platform.dao.PropertyDao;

@Service
class PropertyServiceImpl extends EntityServiceImpl<Property, PropertyDao> implements PropertyService {

	@Override
	public Property getByCode(Class<? extends EntityObject> clazz, String code) {
		return select((Property temp) -> {
			return temp.belongClazz == clazz && temp.getCode().equals(code);
		});
	}

	@Override
	public List<Property> getByClass(Class<? extends EntityObject> clazz) {
		return EntityUtils.getProperties(clazz);
	}
}
