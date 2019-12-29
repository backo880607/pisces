package com.pisces.platform.integration.helper;

import com.pisces.platform.core.entity.EntityObject;
import com.pisces.platform.core.entity.Property;
import com.pisces.platform.core.enums.ENTITY_STATUS;
import com.pisces.platform.core.service.EntityService;
import com.pisces.platform.core.service.ServiceManager;
import com.pisces.platform.core.utils.AppUtils;
import com.pisces.platform.core.utils.EntityUtils;
import com.pisces.platform.core.utils.PageParam;
import com.pisces.platform.integration.bean.DataSource;
import com.pisces.platform.integration.bean.FieldInfo;
import com.pisces.platform.integration.bean.Scheme;
import com.pisces.platform.integration.bean.SchemeGroup;
import com.pisces.platform.integration.config.IntegrationMessage;
import com.pisces.platform.integration.exception.DataSourceException;
import org.springframework.util.StringUtils;

import java.util.*;

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
                if (!fields.isEmpty()) {
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
                if (!adapter.beforeWriteTable(scheme, fieldInfos)) {
                    continue;
                }

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
                    if (!adapter.beforeWriteEntity(entity)) {
                        continue;
                    }

                    int index = 0;
                    for (Property property : properties) {
                        String value = getConfig().getMapper().getTextValue(entity, property);
                        if (!StringUtils.isEmpty(getConfig().getSepField())) {
                            value.replace(getConfig().getSepField(), getConfig().getReplaceField());
                        }
                        if (!StringUtils.isEmpty(getConfig().getSepEntity())) {
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
