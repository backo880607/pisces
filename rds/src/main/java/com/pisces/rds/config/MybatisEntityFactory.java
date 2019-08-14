package com.pisces.rds.config;

import org.apache.ibatis.reflection.factory.DefaultObjectFactory;

import com.pisces.core.entity.EntityObject;

public class MybatisEntityFactory extends DefaultObjectFactory {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4783626710004724883L;
	
	@Override
	public <T> T create(Class<T> type) {
		T entity = super.create(type);
		if (EntityObject.class.isAssignableFrom(type)) {
			((EntityObject)entity).init();
		}
		return entity;
	}

}
