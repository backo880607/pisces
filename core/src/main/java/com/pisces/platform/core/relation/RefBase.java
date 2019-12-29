package com.pisces.platform.core.relation;

import com.pisces.platform.core.entity.EntityObject;

import java.util.Collection;

public interface RefBase extends Iterable<EntityObject> {
    EntityObject get();

    EntityObject first();

    EntityObject last();

    boolean isEmpty();

    int size();

    boolean contains(Object o);

    boolean add(EntityObject o);

    boolean remove(Object o);

    boolean addAll(Collection<? extends EntityObject> c);

    void clear();

    void bindSign(Sign sign);

    void bindId(long id);

    Collection<EntityObject> collection();
}