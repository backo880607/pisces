package com.pisces.integration.helper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.pisces.core.entity.EntityObject;
import com.pisces.core.entity.Property;
import com.pisces.core.enums.ENTITY_STATUS;
import com.pisces.core.service.EntityService;
import com.pisces.core.service.ServiceManager;
import com.pisces.core.utils.AppUtils;
import com.pisces.core.utils.EntityUtils;
import com.pisces.core.utils.PageParam;
import com.pisces.integration.bean.DataSource;
import com.pisces.integration.bean.FieldInfo;
import com.pisces.integration.bean.Scheme;
import com.pisces.integration.bean.SchemeGroup;
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
	public void execute(SchemeGroup schemeGroup) {
		DataSource dataSource = schemeGroup.getDataSource();
		if (dataSource == null) {
			throw new UnsupportedOperationException("missing datasource configuration in SchemeGroup " + schemeGroup.getName());
		}
		switchDataSourceService(dataSource);
		
		Collection<Scheme> schemes = schemeGroup.getSchemes();
		List<Scheme> targetSchemes = new LinkedList<Scheme>(schemes);
		Iterator<Scheme> iter = targetSchemes.iterator();
		while (iter.hasNext()) {
			Scheme scheme = iter.next();
			if (scheme.getStatus() == ENTITY_STATUS.DISABLE) {
				iter.remove();
				continue;
			}

			try {
				if (!adapter.validConnection(dataSource, scheme.getOutName(), true)) {
					return;
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
				throw new DataSourceException(IntegrationMessage.ConnectFailed, dataSource.getClass().getSimpleName().substring(2), dataSource.getHost());
			} finally {
				if (adapter != null) {
					adapter.close();
				}
			}
		}
		
		for (Scheme scheme : targetSchemes) {
			try {
				adapter.open(dataSource, scheme.getOutName(), true);
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
						String value = getConfig().getMapper().getTextValue(entity, property);
						if (getConfig().getSepField() != null) {
							value.replace(getConfig().getSepField(), getConfig().getReplaceField());
						}
						if (getConfig().getSepEntity() != null) {
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
}
