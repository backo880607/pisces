package com.pisces.core.relation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.pisces.core.entity.EntityObject;
import com.pisces.core.utils.EntityUtils;

public class RefSequence extends LinkedList<EntityObject> implements RefBase {
	private static final long serialVersionUID = 7023267463036954038L;
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
		return this.isEmpty() ? null : super.getFirst();
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
		ensureCached();
		return super.getFirst();
	}

	@Override
	public EntityObject last() {
		ensureCached();
		return super.getLast();
	}

	@Override
	public Iterator<EntityObject> iterator() {
		ensureCached();
		return super.iterator();
	}

	@Override
	public Collection<EntityObject> collection() {
		return this;
	}
}
