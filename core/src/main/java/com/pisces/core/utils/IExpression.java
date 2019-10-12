package com.pisces.core.utils;

import java.util.List;

import com.pisces.core.entity.EntityObject;

public interface IExpression {
	boolean Parse(String str);
	
	Object getValue();
	Object getValue(EntityObject entity);
	boolean getBoolean();
	boolean getBoolean(EntityObject entity);
	String getString();
	String getString(EntityObject entity);
	
	int compare(EntityObject o1, EntityObject o2);
	
	<T extends EntityObject> void filter(List<T> entities);
}
