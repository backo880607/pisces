package com.pisces.platform.core.dao.impl;

import com.pisces.platform.core.entity.EntityObject;

public class SingletonModifyDaoImpl<T extends EntityObject> extends SingletonDaoImpl<T> {
    public boolean modified = false;
}
