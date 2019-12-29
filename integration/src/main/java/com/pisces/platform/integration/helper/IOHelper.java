package com.pisces.platform.integration.helper;

import com.pisces.platform.core.entity.EntityObject;
import com.pisces.platform.core.entity.Property;
import com.pisces.platform.core.service.PropertyService;
import com.pisces.platform.core.utils.AppUtils;
import com.pisces.platform.core.utils.EntityUtils;
import com.pisces.platform.integration.bean.DataSource;
import com.pisces.platform.integration.bean.FieldInfo;
import com.pisces.platform.integration.bean.Scheme;
import com.pisces.platform.integration.bean.SchemeGroup;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public abstract class IOHelper {
    private DataConfig config;
    protected DataSourceAdapter adapter;
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

    public abstract void execute(SchemeGroup schemeGroup);

    protected void switchDataSourceService(DataSource dataSource) {
        adapter = AdapterManager.getAdapter(dataSource);
        if (adapter == null) {
            throw new UnsupportedOperationException("datasource " + dataSource.getName() + " not implement service class!");
        }

        if (this.config == null) {
            this.config = new DataConfig();
        }
        adapter.modifyConfig(this.config);
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
}
