package com.pisces.core.relation;

import java.util.Collection;

import com.pisces.core.entity.EntityObject;

public interface RefBase extends Collection<EntityObject> {
	EntityObject get();
	EntityObject first();
	EntityObject last();
	void bindSign(Sign sign);
	void bindId(long id);
}