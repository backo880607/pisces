package com.pisces.platform.integration.helper;

import com.pisces.platform.core.entity.EntityObject;
import com.pisces.platform.core.entity.Property;
import com.pisces.platform.core.enums.ENTITY_STATUS;
import com.pisces.platform.core.utils.AppUtils;
import com.pisces.platform.core.utils.EntityUtils;
import com.pisces.platform.integration.bean.DataSource;
import com.pisces.platform.integration.bean.FieldInfo;
import com.pisces.platform.integration.bean.Scheme;
import com.pisces.platform.integration.bean.SchemeGroup;
import org.apache.ibatis.datasource.DataSourceException;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import java.util.*;

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
            final Class<? extends EntityObject> clazz = EntityUtils.getEntityClass(scheme.getInName());

            try {
                adapter.open(dataSource, scheme.getOutName(), false);
                Collection<FieldInfo> fields = scheme.getFields();
                if (fields.isEmpty()) {
                    fields = adapter.getFields();
                }

                if (adapter.executeQuery(dataSource, scheme.getOutName(), fields)) {
                    List<Property> properties = new ArrayList<Property>();
                    for (FieldInfo info : fields) {
                        properties.add(AppUtils.getPropertyService().get(clazz, info.getName()));
                    }

                    while (adapter.step()) {
                        EntityObject entity = createEntity(clazz);
                        if (entity == null) {
                            continue;
                        }

                        int index = 0;
                        for (Property property : properties) {
                            String value = adapter.getData(index);
                            if (!StringUtils.isEmpty(getConfig().getSepField())) {
                                value.replace(getConfig().getReplaceField(), getConfig().getSepField());
                            }
                            if (!StringUtils.isEmpty(getConfig().getSepEntity())) {
                                value.replace(getConfig().getReplaceEntity(), getConfig().getSepEntity());
                            }

                            getConfig().getMapper().setTextValue(entity, property, value);
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
