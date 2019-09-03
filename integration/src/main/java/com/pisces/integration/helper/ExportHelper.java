package com.pisces.integration.helper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.ibatis.datasource.DataSourceException;

import com.pisces.core.entity.EntityObject;
import com.pisces.core.entity.Property;
import com.pisces.core.exception.ExistedException;
import com.pisces.core.exception.OperandException;
import com.pisces.core.service.EntityService;
import com.pisces.core.service.ServiceManager;
import com.pisces.core.utils.AppUtils;
import com.pisces.core.utils.EntityUtils;
import com.pisces.core.utils.PageParam;
import com.pisces.integration.bean.FieldInfo;
import com.pisces.integration.bean.Scheme;

public class ExportHelper extends IOHelper {
	
	private List<FieldInfo> getDefaultFields(Scheme scheme) {
		Class<? extends EntityObject> clazz = EntityUtils.getEntityClass(scheme.getInName());
		List<FieldInfo> infos = new ArrayList<FieldInfo>();
		List<Property> properties = AppUtils.getPropertyService().getVisiables(clazz);
		for (Property property : properties) {
			FieldInfo info = new FieldInfo();
			info.setName(property.getCode());
			info.setExternName(property.getName());
			info.setScheme(scheme);
			infos.add(info);
		}
		return infos;
	}

	@Override
	public void execute(Collection<Scheme> schemes) {
		for (Scheme scheme : schemes) {
			if (scheme.getDataSource() == null) {
				throw new ExistedException("missing datasource configuration in Scheme " + scheme.getName());
			}
			try {
				switchDataSourceService(scheme.getDataSource());
				if (!adapter.validConnection(scheme.getDataSource(), scheme.getOutName())) {
					throw new DataSourceException("datasource " + scheme.getDataSource().getName() + " connection failed!");
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
				throw new OperandException(ex);
			} finally {
				if (adapter != null) {
					adapter.close();
				}
			}
		}
		
		for (Scheme scheme : schemes) {
			try {
				switchDataSourceService(scheme.getDataSource());
				adapter.open(scheme.getDataSource(), scheme.getOutName());
				Collection<FieldInfo> fieldInfos = scheme.getFields();
				adapter.writeHeader(fieldInfos);
				final Class<? extends EntityObject> clazz = EntityUtils.getEntityClass(scheme.getInName());
				List<Property> properties = new ArrayList<Property>();
				for (FieldInfo info : fieldInfos) {
					properties.add(AppUtils.getPropertyService().get(clazz, info.getName()));
				}
				EntityService<? extends EntityObject> service = ServiceManager.getService(clazz);
				PageParam pageParam = new PageParam();
				pageParam.setFilter(scheme.getFilter());
				pageParam.setOrderBy(scheme.getOrderBy());
				List<? extends EntityObject> entities = service.select(pageParam);
				for (EntityObject entity : entities) {
					adapter.beforeWriteEntity(entity);
					int index = 0;
					for (Property property : properties) {
						String value = EntityUtils.getTextValue(entity, property);
						adapter.write(index, value);
						++index;
					}
					adapter.afterWriteEntity(entity);
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

}
