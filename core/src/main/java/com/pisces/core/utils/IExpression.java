package com.pisces.core.utils;

import java.util.List;

import com.pisces.core.entity.EntityObject;

public interface IExpression {
	/**
	 * 解析表达式
	 * @param str
	 * @return
	 */
	boolean Parse(String str);
	
	/**
	 * 获取表达式的值
	 * @return
	 */
	Object getValue();
	Object getValue(EntityObject entity);
	boolean getBoolean();
	boolean getBoolean(EntityObject entity);
	String getString();
	String getString(EntityObject entity);
	
	int compare(EntityObject o1, EntityObject o2);
	
	<T extends EntityObject> void filter(List<T> entities);
}
