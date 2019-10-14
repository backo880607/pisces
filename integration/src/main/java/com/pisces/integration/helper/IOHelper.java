package com.pisces.integration.helper;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.pisces.core.entity.EntityObject;
import com.pisces.core.entity.Property;
import com.pisces.core.service.PropertyService;
import com.pisces.core.utils.AppUtils;
import com.pisces.core.utils.EntityUtils;
import com.pisces.integration.bean.DataSource;
import com.pisces.integration.bean.FieldInfo;
import com.pisces.integration.bean.Scheme;

public abstract class IOHelper {
	protected DataSourceAdapter adapter;
	private DataConfig config;
	protected PropertyService propertyService;
	
	public IOHelper() {
		Map<String, PropertyService> temp = AppUtils.getBeansOfType(PropertyService.class);
		if (temp != null && !temp.isEmpty()) {
			this.propertyService = temp.entrySet().iterator().next().getValue();
		}
	}
	
	public DataConfig getConfig() {
		return this.config;
	}
	
	public abstract void execute(Collection<Scheme> schemes);
	
	protected void switchDataSourceService(DataSource dataSource) {
		this.adapter = AdapterManager.getAdapter(dataSource);
		if (this.adapter != null) {
			this.config = this.adapter.getDataConfig();
		} else {
			throw new UnsupportedOperationException("datasource " + dataSource.getName() + " not implement service class!");
		}
	}
	
	protected void checkPrimaryKey(Scheme scheme, Collection<FieldInfo> fields) {
		Class<? extends EntityObject> clazz = EntityUtils.getEntityClass(scheme.getInName());
		List<Property> properties = AppUtils.getPropertyService().getPrimaries(clazz);
		for (Property property : properties) {
			boolean bFind = false;
			for (FieldInfo field : fields) {
				if (field.getName().equals(property.getName())) {
					bFind = true;
					break;
				}
			}
			if (!bFind) {
				throw new UnsupportedOperationException("missing primary key field " + property.getName() + " in table " + scheme.getOutName());
			}
		}
	}
	
	protected void checkProperties(Scheme scheme, Collection<FieldInfo> fields) {
		Class<? extends EntityObject> clazz = EntityUtils.getEntityClass(scheme.getInName());
		for (FieldInfo field : fields) {
			Property property = AppUtils.getPropertyService().get(clazz, field.getName());
			if (property == null) {
				throw new UnsupportedOperationException("invalid field " + field.getExternName() + " in table " + scheme.getOutName());
			}
		}
	}
	
	protected void write(EntityObject entity, Property property, String value) {
		EntityUtils.setTextValue(entity, property, value);
	}
}
