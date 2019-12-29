package com.pisces.platform.core.converter;

import com.pisces.platform.core.entity.BaseDeserializer;
import com.pisces.platform.core.entity.EntityObject;
import com.pisces.platform.core.entity.Property;

public class EntityDeserializer extends BaseDeserializer<EntityObject> {

    @Override
    public EntityObject deserialize(Property property, String value) {
        EntityObject entity = null;
        try {
            final long id = Long.valueOf(value);
            entity = (EntityObject) property.clazz.newInstance();
            entity.setId(id);
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e.getMessage());
        }
        return entity;
    }

}
