package com.pisces.core.relation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import com.pisces.core.entity.EntityObject;
import com.pisces.core.utils.EntityUtils;

public class RefList extends ArrayList<EntityObject> implements RefBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9023683110960743784L;
	private Sign sign;
	private List<Long> ids;

	@Override
	public void bindSign(Sign sign) {
		this.sign = sign;
	}
	
	@Override
	public void bindId(long id) {
		if (ids == null) {
			ids = new ArrayList<>();
		}
		ids.add(id);
	}
	
	private void ensureCached() {
		if (ids == null) {
			return;
		}

		super.addAll(EntityUtils.getInherit(sign.getEntityClass(), ids));
		ids = null;
	}
	
	@Override
	public int size() {
		ensureCached();
		return super.size();
	}
	
	@Override
	public boolean isEmpty() {
		ensureCached();
		return super.isEmpty();
	}
	
	@Override
	public boolean contains(Object o) {
		ensureCached();
		return super.contains(o);
	}
	
	@Override
	public EntityObject get() {
		if (this.isEmpty()) {
			return null;
		}
		return super.get(0);
	}
	
	@Override
	public EntityObject get(int index) {
		ensureCached();
		return super.get(index);
	}
	
	@Override
	public boolean add(EntityObject e) {
		ensureCached();
		return super.add(e);
	}
	
	@Override
	public boolean remove(Object o) {
		ensureCached();
		return super.remove(o);
	}
	
	@Override
	public void clear() {
		ensureCached();
		super.clear();
	}

	@Override
	public EntityObject first() {
		if (this.isEmpty()) {
			throw new NoSuchElementException();
		}
		return super.get(0);
	}

	@Override
	public EntityObject last() {
		if (this.isEmpty()) {
			throw new NoSuchElementException();
		}
		return super.get(size() - 1);
	}
	
	@Override
	public Iterator<EntityObject> iterator() {
		ensureCached();
		return super.iterator();
	}
	
	@Override
	public Object[] toArray() {
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		throw new UnsupportedOperationException();
	}
}
