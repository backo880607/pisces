package com.pisces.platform.core.validator;

import com.pisces.platform.core.annotation.PrimaryKey;
import com.pisces.platform.core.entity.EntityObject;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

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
