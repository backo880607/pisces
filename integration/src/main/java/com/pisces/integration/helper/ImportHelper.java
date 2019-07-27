package com.pisces.integration.helper;

import java.util.Collection;

import org.apache.ibatis.datasource.DataSourceException;

import com.pisces.core.entity.EntityObject;
import com.pisces.core.entity.Property;
import com.pisces.core.exception.ExistedException;
import com.pisces.core.utils.EntityUtils;
import com.pisces.integration.bean.FieldInfo;
import com.pisces.integration.bean.Scheme;

public class ImportHelper extends IOHelper {

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
				
				//dataSourceService.checkTableStructure(scheme.getDataSource(), tableName, fields)
				checkPrimaryKey(scheme);
				checkProperties(scheme);
			} catch (Exception ex) {
				if (dataSourceService != null) {
					dataSourceService.close();
				}
			}
		}
		
		for (Scheme scheme : schemes) {
			try {
				switchDataSourceService(scheme.getDataSource());
				dataSourceService.open(scheme.getDataSource(), scheme.getOutName());
				Class<? extends EntityObject> clazz = EntityUtils.getEntityClass(scheme.getInName());
				Collection<FieldInfo> fields = scheme.getFields();
				dataSourceService.executeQuery(scheme.getDataSource(), scheme.getOutName(), fields);
				while (dataSourceService.step()) {
					EntityObject entity = createEntity(clazz);
					if (entity == null) {
						continue;
					}
					
					int index = 0;
					for (FieldInfo field : fields) {
						String value = obtainValue(index);
						++index;
						
						Property property = EntityUtils.getProperty(clazz, field.getName());
						write(entity, property, value);
					}
				}
			} catch (Exception e) {
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
	
	private EntityObject createEntity(Class<?> clazz) {
		return null;
	}

}
