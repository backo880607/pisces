package com.pisces.platform.core.primary.expression.calculate;

import com.pisces.platform.core.entity.EntityObject;

public interface Calculate {
    Object getValue(EntityObject entity);

    int parse(String str, int index);

    Class<?> getReturnClass();
}
