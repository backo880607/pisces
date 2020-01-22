package com.pisces.platform.core.exception;

import com.pisces.platform.core.entity.EntityObject;

public class EntityException extends PropertyException {
    private EntityObject entity;

    public EntityException(Enum<?> enumKey, Object... argements) {
        super(enumKey, argements);
    }


    public EntityObject getEntity() {
        return entity;
    }

    public void setEntity(EntityObject entity) {
        this.entity = entity;
    }
}
