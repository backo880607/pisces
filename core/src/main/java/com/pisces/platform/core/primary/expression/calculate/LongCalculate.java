package com.pisces.platform.core.primary.expression.calculate;

import com.pisces.platform.core.entity.EntityObject;

public class LongCalculate implements Calculate {
    public long value;

    @Override
    public Object getValue(EntityObject entity) {
        return value;
    }

    @Override
    public int parse(String str, int index) {
        return -1;
    }

    @Override
    public Class<?> getReturnClass() {
        return Long.class;
    }
}
