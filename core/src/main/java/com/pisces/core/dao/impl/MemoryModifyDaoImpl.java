package com.pisces.core.dao.impl;

import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.pisces.core.entity.EntityObject;

public class MemoryModifyDaoImpl<T extends EntityObject> extends MemoryDaoImpl<T> {
	public Collection<Long> deleteds = new ConcurrentLinkedQueue<>();
}
