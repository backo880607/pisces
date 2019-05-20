package com.pisces.core.validator;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.pisces.core.annotation.PrimaryKey;
import com.pisces.core.entity.EntityObject;
import com.pisces.core.entity.Property;
import com.pisces.core.service.EntityService;
import com.pisces.core.service.ServiceManager;
import com.pisces.core.utils.EntityUtils;
import com.pisces.core.utils.StringUtils;

public class PrimaryKeyValidator implements ConstraintValidator<PrimaryKey, EntityObject> {	
	
	@Override
	public boolean isValid(EntityObject value, ConstraintValidatorContext context) {
		List<Property> properties = EntityUtils.getPrimaries(value.getClass());
		String key = StringUtils.join(properties, "\t", (Property property) -> {
			return EntityUtils.getValue(value, property);
		});
		
		Set<String> primaryKeys = getPrimaryKeys(value.getClass());
		if (primaryKeys.contains(key)) {
			return false;
		}
		return true;
	}

	private Set<String> getPrimaryKeys(Class<? extends EntityObject> clazz) {
		Set<String> primaryKeys = new HashSet<>();
		EntityService<? extends EntityObject> service = ServiceManager.getService(clazz);
		if (service == null) {
			return primaryKeys;
		}
		
		List<Property> properties = EntityUtils.getPrimaries(clazz);
		List<? extends EntityObject> entities = service.selectAll();
		for (EntityObject entity : entities) {
			String key = StringUtils.join(properties, "\t", (Property property) -> {
				return EntityUtils.getValue(entity, property);
			});
			
			primaryKeys.add(key);
		}
		
		return primaryKeys;
	}
}
