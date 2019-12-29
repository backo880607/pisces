package com.pisces.platform.core.dao.impl;

import com.pisces.platform.core.entity.EntityObject;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MemoryDaoImpl<T extends EntityObject> implements DaoImpl {
    public Map<Long, T> records = new ConcurrentHashMap<>();
}
