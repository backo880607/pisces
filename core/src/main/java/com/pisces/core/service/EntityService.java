package com.pisces.core.service;

import java.util.List;
import java.util.function.Predicate;

import com.pisces.core.dao.BaseDao;
import com.pisces.core.entity.EntityObject;
import com.pisces.core.utils.PageParam;

public interface EntityService<T extends EntityObject> extends BaseService {
	BaseDao<T> getBaseDao();
	T create();
	
	T get();
	List<T> getAll();
	T getById(long id);
	List<T> getByIds(List<Long> ids);
	List<T> get(PageParam param);
	T get(Predicate<T> filter);
	List<T> getList(Predicate<T> filter);
	
	boolean exist(long id);
	
	int insert(T entity);
	int insertList(List<T> entities);
	
	int update(T entity);
	int updateList(List<T> entities);
	
	int delete(T entity);
	int deleteList(List<T> entities);
	int deleteById(long id);
	int deleteByIds(List<Long> ids);
	
	void clear();
	
	void deleteImpl(EntityObject entity);
}