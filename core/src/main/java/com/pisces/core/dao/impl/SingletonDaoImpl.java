package com.pisces.core.dao.impl;

import com.pisces.core.entity.EntityObject;

public class SingletonDaoImpl<T extends EntityObject> implements DaoImpl {
	public T record;
}
