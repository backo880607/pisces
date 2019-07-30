package com.pisces.core.relation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

import com.pisces.core.entity.EntityObject;
import com.pisces.core.utils.EntityUtils;

public class RefSingleton implements RefBase {
	private class SingletonIterator implements Iterator<EntityObject> {
		private boolean begin;
		private EntityObject o;
		public SingletonIterator(EntityObject o) {
			this.o = o;
			this.begin = o != null;
		}

		@Override
		public boolean hasNext() {
			return this.begin;
		}

		@Override
		public EntityObject next() {
			this.begin = false;
			return this.o;
		}
	}
	
	private EntityObject entity;
	
	private Sign sign;
	private long id;
	
	@Override
	public void bindSign(Sign sign) {
		this.sign = sign;
	}
	
	@Override
	public void bindId(long id) {
		this.id = id;
	}
	
	private void ensureCached() {
		if (id == 0) {
			return;
		}

		this.entity = EntityUtils.getInherit(sign.getEntityClass(), id);
		id = 0;
	}

	@Override
	public int size() {
		return isEmpty() ? 0 : 1;
	}

	@Override
	public boolean isEmpty() {
		ensureCached();
		return this.entity != null;
	}

	@Override
	public boolean contains(Object o) {
		ensureCached();
		return this.entity.equals(o);
	}

	@Override
	public EntityObject get() {
		ensureCached();
		return this.entity;
	}

	@Override
	public boolean add(EntityObject o) {
		ensureCached();
		this.entity = o;
		return true;
	}

	@Override
	public boolean remove(Object o) {
		ensureCached();
		if (this.entity.equals(o)) {
			this.entity = null;
			return true;
		}
		return false;
	}

	@Override
	public void clear() {
		ensureCached();
		this.entity = null;
	}

	@Override
	public Iterator<EntityObject> iterator() {
		ensureCached();
		return new SingletonIterator(this.entity);
	}

	@Override
	public EntityObject first() {
		ensureCached();
		if (entity == null) {
			throw new NoSuchElementException();
		}
		return entity;
	}

	@Override
	public EntityObject last() {
		ensureCached();
		if (entity == null) {
			throw new NoSuchElementException();
		}
		return entity;
	}
	
	@Override
	public boolean addAll(Collection<? extends EntityObject> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Collection<EntityObject> collection() {
		Collection<EntityObject> result = new ArrayList<EntityObject>();
		if (this.entity != null) {
			result.add(this.entity);
		}
		return result;
	}
}
