package com.pisces.core.relation;

import com.pisces.core.entity.EntityObject;

public class Sign implements Comparable<Sign> {
	private int value = 0;
	private String name = "";
	private Class<? extends EntityObject> entityClass;
	public Sign() {
	}
	public Sign(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Class<? extends EntityObject> getEntityClass() {
		return entityClass;
	}
	public void setEntityClass(Class<? extends EntityObject> entityClass) {
		this.entityClass = entityClass;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Sign) {
            return value == ((Sign)obj).value;
        }
        return false;
	}
	@Override
	public int hashCode() {
		return Integer.hashCode(value);
	}
	
	@Override
	public int compareTo(Sign o) {
		return Integer.compare(this.value, o.value);
	}
	
	@Override
	public String toString() {
		return name;
	}
}
