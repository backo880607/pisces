package com.pisces.core.dao;

import java.util.Collection;
import java.util.List;

import com.pisces.core.dao.impl.DaoImpl;
import com.pisces.core.entity.EntityObject;

public interface BaseDao<T extends EntityObject> {
	T select();
	List<T> selectAll();
	T selectByPrimaryKey(Object key);
	List<T> selectMap(Collection<Long> ids);
	boolean existsWithPrimaryKey(Object key);
	int insert(T record);
	int insertList(Collection<T> recordList);
	int updateByPrimaryKey(T record);
	int delete(T record);
	int deleteByPrimaryKey(Object key);
	
	DaoImpl createDaoImpl();
	void switchDaoImpl(DaoImpl impl);
	
	void loadData();
	void afterLoadData();
	void sync();
}