package com.pisces.platform.core.primary.expression.calculate;

import com.pisces.platform.core.entity.EntityObject;

public class BooleanCalculate implements Calculate {
    private boolean value;

    @Override
    public Object getValue(EntityObject entity) {
        return value;
    }

    @Override
    public int parse(String str, int index) {
        return 0;
    }

    @Override
    public Class<?> getReturnClass() {
        return Boolean.class;
    }
}
