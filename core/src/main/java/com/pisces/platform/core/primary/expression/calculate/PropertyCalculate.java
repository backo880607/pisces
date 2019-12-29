package com.pisces.platform.core.primary.expression.calculate;

import com.pisces.platform.core.config.CoreMessage;
import com.pisces.platform.core.entity.EntityObject;
import com.pisces.platform.core.entity.Property;
import com.pisces.platform.core.enums.PROPERTY_TYPE;
import com.pisces.platform.core.exception.ExpressionException;
import com.pisces.platform.core.utils.AppUtils;
import com.pisces.platform.core.utils.EntityUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PropertyCalculate implements Calculate {
    private Property property = null;
    private List<Property> paths = new ArrayList<>();
    private boolean isList = false;

    private Object convertValue(Object value) {
        if (value == null) {
            throw new NullPointerException();
        }
        switch (property.getType()) {
            case BOOLEAN:
                return (boolean) value;
            case LONG:
                return (long) value;
            case DOUBLE:
                return (double) value;
            case DATE:
            case TIME:
            case DATE_TIME:
            case DURATION:
            case ENUM:
            case MULTI_ENUM:
            case STRING:
            case ENTITY:
            case LIST:
                return value;
            default:
                break;
        }

        throw new UnsupportedOperationException(this.property.getType() + " is not supported!");
    }

    @SuppressWarnings("unchecked")
    private static void getListImpl(Collection<EntityObject> result, EntityObject entity, List<Property> paths, int index) {
        if (index >= paths.size()) {
            result.add(entity);
        } else {
            try {
                Object relaEntity = paths.get(index).getMethod.invoke(entity);
                if (relaEntity != null) {
                    if (paths.get(index).getType() == PROPERTY_TYPE.ENTITY) {
                        getListImpl(result, (EntityObject) relaEntity, paths, index + 1);
                    } else if (paths.get(index).getType() == PROPERTY_TYPE.LIST) {
                        for (EntityObject rela : (Collection<EntityObject>) relaEntity) {
                            getListImpl(result, rela, paths, index + 1);
                        }
                    }
                }
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                throw new UnsupportedOperationException(e.getMessage());
            }
        }
    }

    @SuppressWarnings("unchecked")
    private Collection<EntityObject> getListValue(EntityObject entity) {
        Collection<EntityObject> entities = new ArrayList<>();
        getListImpl(entities, entity, this.paths, 0);
        Collection<EntityObject> result = new ArrayList<EntityObject>();
        for (EntityObject curEntity : entities) {
            Object val = EntityUtils.getValue(curEntity, this.property);
            if (val == null) {
                continue;
            }
            if (this.property.getType() == PROPERTY_TYPE.ENTITY) {
                result.add((EntityObject) val);
            } else if (this.property.getType() == PROPERTY_TYPE.LIST) {
                result.addAll((Collection<EntityObject>) val);
            }
        }
        return result;
    }

    public Object getValue(EntityObject entity) {
        if (this.isList) {
            return getListValue(entity);
        }
        EntityObject relaEntity = entity;
        for (Property path : this.paths) {
            relaEntity = (EntityObject) EntityUtils.getValue(relaEntity, path);
            if (relaEntity == null) {
                throw new NullPointerException();
            }
        }
        return convertValue(EntityUtils.getValue(relaEntity, this.property));
    }

    @Override
    public int parse(String str, int index) {
        return parse(str, index, null);
    }

    @SuppressWarnings("unchecked")
    public int parse(String str, int index, Class<? extends EntityObject> propertyClazz) {
        final int origin = index;
        int temp = index;

        this.property = null;
        this.paths.clear();
        this.isList = false;
        while (index < str.length()) {
            char curChar = str.charAt(index);
            if (curChar == '.') {
                String name = str.substring(temp, index);
                if (propertyClazz == null) {
                    propertyClazz = EntityUtils.getEntityClass(name);
                    if (propertyClazz == null) {
                        throw new ExpressionException(CoreMessage.InvalidObjectName, name);
                    }
                } else {
                    Property path = AppUtils.getPropertyService().get(propertyClazz, name);
                    if (path == null) {
                        throw new ExpressionException(CoreMessage.InvalidProperty, propertyClazz.getName(), name);
                    }
                    if (path.getType() != PROPERTY_TYPE.ENTITY && path.getType() != PROPERTY_TYPE.LIST) {
                        throw new ExpressionException(CoreMessage.NotEntityOrList, propertyClazz.getName(), name);
                    }
                    this.paths.add(path);
                    propertyClazz = (Class<? extends EntityObject>) path.clazz;
                    if (path.getType() == PROPERTY_TYPE.LIST) {
                        this.isList = true;
                    }
                }

                temp = index + 1;
            } else if (!Character.isAlphabetic(curChar) && !Character.isDigit(curChar) && curChar != '_') {
                break;
            }

            ++index;
        }

        if (temp < index) {
            String name = str.substring(temp, index);
            this.property = AppUtils.getPropertyService().get(propertyClazz, name);
            if (this.property == null) {
                throw new ExpressionException(CoreMessage.InvalidProperty, propertyClazz.getName(), name);
            }

            return index;
        }

        throw new ExpressionException(CoreMessage.ExpressionError, str.substring(origin));
    }

    @Override
    public Class<?> getReturnClass() {
        return this.property.getType() != PROPERTY_TYPE.LIST ? this.property.clazz : Collection.class;
    }
}
