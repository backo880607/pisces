package com.pisces.core.dao.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.pisces.core.entity.EntityObject;

public class MemoryDaoImpl<T extends EntityObject> implements DaoImpl {
	public Map<Long, T> records = new ConcurrentHashMap<>();
}
