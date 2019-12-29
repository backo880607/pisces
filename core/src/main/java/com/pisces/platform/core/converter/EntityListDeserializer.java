package com.pisces.platform.core.converter;

import com.pisces.platform.core.entity.BaseDeserializer;
import com.pisces.platform.core.entity.EntityObject;
import com.pisces.platform.core.entity.Property;

import java.util.ArrayList;
import java.util.Collection;

public class EntityListDeserializer extends BaseDeserializer<Collection<EntityObject>> {

    @Override
    public Collection<EntityObject> deserialize(Property property, String value) {
        ArrayList<EntityObject> entities = new ArrayList<EntityObject>();

        try {
            for (String strId : value.split(";")) {
                if (strId.isEmpty()) {
                    continue;
                }

                final long id = Long.valueOf(strId);
                EntityObject entity = (EntityObject) property.clazz.newInstance();
                entity.setId(id);
                entities.add(entity);

            }
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e.getMessage());
        }
        return entities;
    }

}
