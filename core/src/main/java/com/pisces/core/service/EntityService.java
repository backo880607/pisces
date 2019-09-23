package com.pisces.core.service;

import java.util.List;
import java.util.function.Predicate;

import com.pisces.core.dao.BaseDao;
import com.pisces.core.entity.EntityObject;
import com.pisces.core.utils.PageParam;

public interface EntityService<T extends EntityObject> extends BaseService {
	BaseDao<T> getBaseDao();
	void init();
	T create();
	
	T select();
	List<T> selectAll();
	T selectById(long id);
	List<T> selectByIds(List<Long> ids);
	List<T> select(PageParam param);
	T select(Predicate<T> filter);
	List<T> selectList(Predicate<T> filter);
	
	boolean exist(long id);
	
	int insert(T entity);
	int insertList(List<T> entities);
	
	int update(T entity);
	int updateList(List<T> entities);
	
	int delete(T entity);
	int deleteList(List<T> entities);
	int deleteById(long id);
	int deleteByIds(List<Long> ids);
}