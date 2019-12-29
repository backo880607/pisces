package com.pisces.platform.core.dao.impl;

import com.pisces.platform.core.entity.EntityObject;

public class SingletonDaoImpl<T extends EntityObject> implements DaoImpl {
    public T record;
}
