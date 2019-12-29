package com.pisces.platform.core.dao.impl;

import com.pisces.platform.core.entity.EntityObject;

import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MemoryModifyDaoImpl<T extends EntityObject> extends MemoryDaoImpl<T> {
    public Collection<Long> deleteds = new ConcurrentLinkedQueue<>();
}
