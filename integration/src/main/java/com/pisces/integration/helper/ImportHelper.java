package com.pisces.integration.helper;

import java.util.Collection;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;

import org.apache.ibatis.datasource.DataSourceException;

import com.pisces.core.entity.EntityObject;
import com.pisces.core.entity.Property;
import com.pisces.core.exception.ExistedException;
import com.pisces.core.exception.OperandException;
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
		for (Scheme scheme : schemes) {
			if (scheme.getDataSource() == null) {
				throw new ExistedException("missing datasource configuration in Scheme " + scheme.getName());
			}
			try {
				switchDataSourceService(scheme.getDataSource());
				if (!dataSourceService.validConnection(scheme.getDataSource(), scheme.getOutName())) {
					throw new DataSourceException("datasource " + scheme.getDataSource().getName() + " connection failed!");
				}
				
				Collection<FieldInfo> fields = scheme.getFields();
				if (fields.isEmpty()) {
					fields = dataSourceService.getFields();
				}
				checkPrimaryKey(scheme, fields);
				checkProperties(scheme, fields);
			} catch (Exception ex) {
				if (dataSourceService != null) {
					dataSourceService.close();
				}
				throw new OperandException(ex);
			} finally {
				if (dataSourceService != null) {
					dataSourceService.close();
				}
			}
		}
		
		for (Scheme scheme : schemes) {
			try {
				switchDataSourceService(scheme.getDataSource());
				dataSourceService.open(scheme.getDataSource(), scheme.getOutName());
				Collection<FieldInfo> fields = scheme.getFields();
				if (fields.isEmpty()) {
					fields = dataSourceService.getFields();
				}
				if (dataSourceService.executeQuery(scheme.getDataSource(), scheme.getOutName(), fields)) {
					Class<? extends EntityObject> clazz = EntityUtils.getEntityClass(scheme.getInName());
					while (dataSourceService.step()) {
						EntityObject entity = createEntity(clazz);
						if (entity == null) {
							continue;
						}
						
						int index = 0;
						for (FieldInfo field : fields) {
							String value = obtainValue(index);
							++index;
							
							Property property = AppUtils.getPropertyService().get(clazz, field.getName());
							write(entity, property, value);
						}
						
						Set<ConstraintViolation<EntityObject>> errors = getValidator().validate(entity, Default.class);
						if (errors != null && !errors.isEmpty()) {
							
						}
					}
				}
			} catch (Exception e) {
				if (dataSourceService != null) {
					dataSourceService.close();
				}
			} finally {
				if (dataSourceService != null) {
					dataSourceService.close();
				}
			}
		}
	}
	
	private String obtainValue(int index) throws Exception {
		String value = dataSourceService.getData(index);
		value.replace(getConfig().getReplaceField(), getConfig().getSepField());
		value.replace(getConfig().getReplaceEntity(), getConfig().getSepEntity());
		return value;
	}
	
	private EntityObject createEntity(Class<? extends EntityObject> clazz) {
		EntityObject entity = null;
		try {
			entity = clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new OperandException(e);
		}
		return entity;
	}

}
