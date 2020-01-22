package com.pisces.platform.core.exception;

import com.pisces.platform.core.entity.Property;

public class PropertyException extends BaseException {
    private Property property;

    public PropertyException(Enum<?> enumKey, Object... argements) {
        super(enumKey, argements);
    }


    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }
}
