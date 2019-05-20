package com.pisces.core.dao.impl;

import com.pisces.core.entity.EntityObject;

public class SingletonModifyDaoImpl<T extends EntityObject> extends SingletonDaoImpl<T> {
	public boolean modified = false;
}
