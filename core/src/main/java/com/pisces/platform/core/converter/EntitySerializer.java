package com.pisces.platform.core.converter;


import com.pisces.platform.core.entity.BaseSerializer;
import com.pisces.platform.core.entity.EntityObject;

public class EntitySerializer extends BaseSerializer<EntityObject> {

    @Override
    public String serialize(EntityObject value) {
        return value.getId().toString();
    }

}
