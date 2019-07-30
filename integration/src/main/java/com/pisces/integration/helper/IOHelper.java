package com.pisces.integration.helper;

import java.util.Collection;
import java.util.List;

import com.pisces.core.entity.EntityObject;
import com.pisces.core.entity.Property;
import com.pisces.core.exception.ExistedException;
import com.pisces.core.exception.NotImplementedException;
import com.pisces.core.service.EntityService;
import com.pisces.core.service.ServiceManager;
import com.pisces.core.utils.EntityUtils;
import com.pisces.integration.bean.DataSource;
import com.pisces.integration.bean.FieldInfo;
import com.pisces.integration.bean.Scheme;
import com.pisces.integration.service.DataSourceService;

public abstract class IOHelper {
	protected DataSourceService<? extends DataSource> dataSourceService;
	private DataConfig config;
	
	public DataConfig getConfig() {
		return this.config;
	}
	
	public abstract void execute(Collection<Scheme> schemes);
	
	@SuppressWarnings("unchecked")
	protected void switchDataSourceService(DataSource dataSource) {
		EntityService<? extends EntityObject> service = ServiceManager.getService(dataSource.getClass());
		if (service != null) {
			this.dataSourceService = (DataSourceService<? extends DataSource>) service;
		} else {
			throw new NotImplementedException("datasource " + dataSource.getName() + " not implement service class!");
		}
	}
	
	protected void checkPrimaryKey(Scheme scheme) {
		Class<? extends EntityObject> clazz = EntityUtils.getEntityClass(scheme.getInName());
		Collection<FieldInfo> fields = scheme.getFields();
		List<Property> properties = EntityUtils.getPrimaries(clazz);
		for (Property property : properties) {
			boolean bFind = false;
			for (FieldInfo field : fields) {
				if (field.getName().equals(property.getName())) {
					bFind = true;
					break;
				}
			}
			if (!bFind) {
				throw new ExistedException("missing primary key field " + property.getName() + " in table " + scheme.getOutName());
			}
		}
	}
	
	protected void checkProperties(Scheme scheme) {
		Class<? extends EntityObject> clazz = EntityUtils.getEntityClass(scheme.getInName());
		Collection<FieldInfo> fields = scheme.getFields();
		for (FieldInfo field : fields) {
			Property property = EntityUtils.getProperty(clazz, field.getName());
			if (property != null) {
				throw new ExistedException("invalid field " + field.getExternName() + " in table " + scheme.getOutName());
			}
		}
	}
	
	protected void write(EntityObject entity, Property property, String value) {
		EntityUtils.setTextValue(entity, property, value);
	}
}
