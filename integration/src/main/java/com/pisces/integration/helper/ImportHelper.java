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
import com.pisces.integration.bean.DataSource;
import com.pisces.integration.bean.FieldInfo;
import com.pisces.integration.bean.Scheme;
import com.pisces.integration.bean.SchemeGroup;

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
	public void execute(SchemeGroup schemeGroup) {
		DataSource dataSource = schemeGroup.getDataSource();
		if (dataSource == null) {
			throw new UnsupportedOperationException("missing datasource configuration in Scheme " + schemeGroup.getName());
		}
		switchDataSourceService(dataSource);

		List<Scheme> targetSchemes = new LinkedList<Scheme>(schemeGroup.getSchemes());
		Iterator<Scheme> iter = targetSchemes.iterator();
		while (iter.hasNext()) {
			Scheme scheme = iter.next();
			if (scheme.getStatus() == ENTITY_STATUS.DISABLE) {
				iter.remove();
				continue;
			}
			
			try {
				if (!adapter.validConnection(dataSource, scheme.getOutName(), false)) {
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
				throw new DataSourceException("datasource " + dataSource.getName() + " connection failed!");
			} finally {
				if (adapter != null) {
					adapter.close();
				}
			}
		}
		
		for (Scheme scheme : targetSchemes) {
			try {
				adapter.open(dataSource, scheme.getOutName(), false);
				Collection<FieldInfo> fields = scheme.getFields();
				if (fields.isEmpty()) {
					fields = adapter.getFields();
				}
				if (adapter.executeQuery(dataSource, scheme.getOutName(), fields)) {
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
							//EntityUtils.setTextValue(entity, property, value, getMapper());
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
