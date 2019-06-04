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
		Property property = EntityUtils.getProperty(clazz, code);
		if (property != null) {
			return property;
		}
		// find user property
		Class<? extends EntityObject> superClazz = clazz;
		while (superClazz != null) {
			final String superClassName = superClazz.getSimpleName();
			property = select((Property temp) -> {
				return temp.getBelongName().equals(superClassName) && temp.getCode().equals(code);
			});
			if (property != null) {
				break;
			}
			superClazz = EntityUtils.getSuperClass(superClazz);
		}
		
		return property;
	}

	@Override
	public List<Property> getByClass(Class<? extends EntityObject> clazz) {
		List<Property> result = EntityUtils.getProperties(clazz);
		// find user properties
		Class<? extends EntityObject> superClazz = clazz;
		while (superClazz != null) {
			final String superClassName = superClazz.getSimpleName();
			result.addAll(selectList((Property temp) -> {
				return temp.getBelongName().equals(superClassName);
			}));
			
			superClazz = EntityUtils.getSuperClass(superClazz);
		}
		return result;
	}
}
