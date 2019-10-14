package com.pisces.integration.helper;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;

import org.apache.ibatis.datasource.DataSourceException;

import com.pisces.core.entity.EntityObject;
import com.pisces.core.entity.Property;
import com.pisces.core.enums.ENTITY_STATUS;
import com.pisces.core.utils.AppUtils;
import com.pisces.core.utils.EntityUtils;
import com.pisces.integration.bean.FieldInfo;
import com.pisces.integration.bean.Scheme;

public class ImportHelper extends IOHelper {
	private static Validator validator = null;
	
	private Validator getValidator() {
		if (validator == null) {
			synchronized (getClass()) {
				if (validator == null) {
					validator = Validation.buildDefaultValidatorFactory().getValidator();
				}
			}
		}
		return validator;
	}

	@Override
	public void execute(Collection<Scheme> schemes) {
		if (propertyService == null) {
			throw new UnsupportedOperationException();
		}
		List<Scheme> targetSchemes = new LinkedList<Scheme>(schemes);
		Iterator<Scheme> iter = targetSchemes.iterator();
		while (iter.hasNext()) {
			Scheme scheme = iter.next();
			if (scheme.getStatus() == ENTITY_STATUS.DISABLE) {
				iter.remove();
				continue;
			}
			
			if (scheme.getDataSource() == null) {
				throw new UnsupportedOperationException("missing datasource configuration in Scheme " + scheme.getName());
			}
			try {
				switchDataSourceService(scheme.getDataSource());
				if (!adapter.validConnection(scheme.getDataSource(), scheme.getOutName(), false)) {
					iter.remove();
					continue;
				}
				
				Collection<FieldInfo> fields = scheme.getFields();
				if (fields.isEmpty()) {
					fields = adapter.getFields();
				}
				checkPrimaryKey(scheme, fields);
				checkProperties(scheme, fields);
			} catch (Exception ex) {
				if (adapter != null) {
					adapter.close();
				}
				throw new DataSourceException("datasource " + scheme.getDataSource().getName() + " connection failed!");
			} finally {
				if (adapter != null) {
					adapter.close();
				}
			}
		}
		
		for (Scheme scheme : targetSchemes) {
			try {
				switchDataSourceService(scheme.getDataSource());
				adapter.open(scheme.getDataSource(), scheme.getOutName(), false);
				Collection<FieldInfo> fields = scheme.getFields();
				if (fields.isEmpty()) {
					fields = adapter.getFields();
				}
				if (adapter.executeQuery(scheme.getDataSource(), scheme.getOutName(), fields)) {
					Class<? extends EntityObject> clazz = EntityUtils.getEntityClass(scheme.getInName());
					while (adapter.step()) {
						EntityObject entity = createEntity(clazz);
						if (entity == null) {
							continue;
						}
						
						int index = 0;
						for (FieldInfo field : fields) {
							String value = adapter.getData(index);
							if (getConfig() != null) {
								value.replace(getConfig().getReplaceField(), getConfig().getSepField());
								value.replace(getConfig().getReplaceEntity(), getConfig().getSepEntity());
							}
							
							Property property = AppUtils.getPropertyService().get(clazz, field.getName());
							write(entity, property, value);
							++index;
						}
						
						Set<ConstraintViolation<EntityObject>> errors = getValidator().validate(entity, Default.class);
						if (errors != null && !errors.isEmpty()) {
							
						}
					}
				}
			} catch (Exception e) {
				if (adapter != null) {
					adapter.close();
				}
			} finally {
				if (adapter != null) {
					adapter.close();
				}
			}
		}
	}
	
	private EntityObject createEntity(Class<? extends EntityObject> clazz) {
		EntityObject entity = null;
		try {
			entity = clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new UnsupportedOperationException(e);
		}
		return entity;
	}

}
