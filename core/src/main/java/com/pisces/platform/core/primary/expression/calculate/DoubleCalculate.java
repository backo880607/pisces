package com.pisces.platform.core.primary.expression.calculate;

import com.pisces.platform.core.entity.EntityObject;

public class DoubleCalculate implements Calculate {
    public double value;

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
        return Double.class;
    }
}
