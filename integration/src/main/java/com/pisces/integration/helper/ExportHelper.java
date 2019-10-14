package com.pisces.integration.helper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.pisces.core.entity.EntityObject;
import com.pisces.core.entity.Property;
import com.pisces.core.enums.ENTITY_STATUS;
import com.pisces.core.enums.PROPERTY_TYPE;
import com.pisces.core.service.EntityService;
import com.pisces.core.service.ServiceManager;
import com.pisces.core.utils.AppUtils;
import com.pisces.core.utils.EntityUtils;
import com.pisces.core.utils.PageParam;
import com.pisces.core.utils.StringUtils;
import com.pisces.integration.bean.FieldInfo;
import com.pisces.integration.bean.Scheme;
import com.pisces.integration.config.IntegrationMessage;
import com.pisces.integration.exception.DataSourceException;

public class ExportHelper extends IOHelper {
	
	private List<FieldInfo> getDefaultFields(Scheme scheme) {
		Class<? extends EntityObject> clazz = EntityUtils.getEntityClass(scheme.getInName());
		List<FieldInfo> infos = new ArrayList<FieldInfo>();
		List<Property> properties = AppUtils.getPropertyService().get(clazz);
		for (Property property : properties) {
			FieldInfo info = new FieldInfo();
			info.setName(property.getCode());
			info.setExternName(property.getName());
			infos.add(info);
		}
		return infos;
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
				if (!adapter.validConnection(scheme.getDataSource(), scheme.getOutName(), true)) {
					iter.remove();
					continue;
				}
				
				Collection<FieldInfo> fields = scheme.getFields();
				if (fields.isEmpty()) {
					fields = getDefaultFields(scheme);
				} else {
					checkProperties(scheme, fields);
				}
			} catch (Exception ex) {
				if (adapter != null) {
					adapter.close();
				}
				throw new DataSourceException(IntegrationMessage.ConnectFailed, scheme.getDataSource().getClass().getSimpleName().substring(2), scheme.getDataSource().getHost());
			} finally {
				if (adapter != null) {
					adapter.close();
				}
			}
		}
		
		for (Scheme scheme : targetSchemes) {
			try {
				switchDataSourceService(scheme.getDataSource());
				adapter.open(scheme.getDataSource(), scheme.getOutName(), true);
				Collection<FieldInfo> fieldInfos = scheme.getFields();
				if (fieldInfos.isEmpty()) {
					fieldInfos = getDefaultFields(scheme);
				}
				adapter.beforeWriteTable(scheme, fieldInfos);
				
				final Class<? extends EntityObject> clazz = EntityUtils.getEntityClass(scheme.getInName());
				List<Property> properties = new ArrayList<Property>();
				for (FieldInfo info : fieldInfos) {
					properties.add(AppUtils.getPropertyService().get(clazz, info.getName()));
				}
				
				EntityService<? extends EntityObject> service = ServiceManager.getService(clazz);
				PageParam pageParam = new PageParam();
				pageParam.setFilter(scheme.getFilter());
				pageParam.setOrderBy(scheme.getOrderBy());
				List<? extends EntityObject> entities = service.get(pageParam);
				for (EntityObject entity : entities) {
					adapter.beforeWriteEntity(entity);
					int index = 0;
					for (Property property : properties) {
						String value = obtainValue(entity, property);
						if (getConfig() != null) {
							value.replace(getConfig().getSepField(), getConfig().getReplaceField());
							value.replace(getConfig().getSepEntity(), getConfig().getReplaceEntity());
						}
						
						adapter.write(index++, value);
					}
					adapter.afterWriteEntity(entity);
				}
				adapter.afterWriteTable(scheme);
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
	
	@SuppressWarnings("unchecked")
	private String obtainValue(EntityObject entity, Property property) {
		String userValue = this.adapter.obtainValue(entity, property);
		if (userValue != null) {
			return userValue;
		}
		
		if (property.getType() == PROPERTY_TYPE.ENTITY) {
			Object value = EntityUtils.getValue(entity, property);
			return value != null ? getPrimaryValue((EntityObject) value) : "";
		} else if (property.getType() == PROPERTY_TYPE.LIST) {
			Object value = EntityUtils.getValue(entity, property);
			return StringUtils.join((List<EntityObject>)value, ";", (EntityObject temp) -> {
				return getPrimaryValue(temp);
			});
		}
		return EntityUtils.getTextValue(entity, property);
	}

	private String getPrimaryValue(EntityObject entity) {
		List<Property> properties = propertyService.getPrimaries(entity.getClass());
		return StringUtils.join(properties, ",", (Property property) -> {
			return EntityUtils.getTextValue(entity, property);
		});
	}
}
