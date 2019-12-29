package com.pisces.platform.core.converter;

import com.pisces.platform.core.entity.BaseSerializer;
import com.pisces.platform.core.entity.EntityObject;
import com.pisces.platform.core.utils.StringUtils;

import java.util.Collection;

public class EntityListSerializer extends BaseSerializer<Collection<EntityObject>> {

    @Override
    public String serialize(Collection<EntityObject> value) {
        return StringUtils.join(value, ";", (EntityObject entity) -> {
            return entity.getId().toString();
        });
    }

}
