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
import com.pisces.core.utils.AppUtils;
import com.pisces.core.utils.EntityUtils;
import com.pisces.core.utils.StringUtils;

public class PrimaryKeyValidator implements ConstraintValidator<PrimaryKey, EntityObject> {	
	
	@Override
	public boolean isValid(EntityObject value, ConstraintValidatorContext context) {
		return true;
		/*List<Property> properties = AppUtils.getPropertyService().getPrimaries(value.getClass());
		String key = StringUtils.join(properties, "\t", (Property property) -> {
			return EntityUtils.getTextValue(value, property, null);
		});
		
		Set<String> primaryKeys = new HashSet<>();
		EntityService<? extends EntityObject> service = ServiceManager.getService(value.getClass());
		if (service != null) {
			List<? extends EntityObject> entities = service.getAll();
			for (EntityObject entity : entities) {
				String temp = StringUtils.join(properties, "\t", (Property property) -> {
					return EntityUtils.getTextValue(entity, property, null);
				});
				
				primaryKeys.add(temp);
			}
		}
		
		return !primaryKeys.contains(key);*/
	}
}
