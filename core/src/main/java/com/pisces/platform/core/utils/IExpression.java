package com.pisces.platform.core.utils;

import com.pisces.platform.core.entity.EntityObject;

import java.util.List;

public interface IExpression {
    boolean parse(String str);

    Object getValue();

    Object getValue(EntityObject entity);

    boolean getBoolean();

    boolean getBoolean(EntityObject entity);

    String getString();

    String getString(EntityObject entity);

    int compare(EntityObject o1, EntityObject o2);

    <T extends EntityObject> void filter(List<T> entities);
}
