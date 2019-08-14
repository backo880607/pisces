package com.pisces.core.service;

import java.util.List;

import com.pisces.core.dao.BaseDao;
import com.pisces.core.entity.EntityObject;
import com.pisces.core.utils.PageParam;

public interface EntityService<T extends EntityObject> extends BaseService {
	public BaseDao<T> getBaseDao();
	public void init();
	public T create();
	public List<T> selectAll();
	public List<T> select(PageParam param);
	public T selectById(long id);
	public int insert(T entity);
	public int update(T entity);
	public int delete(T entity);
	public int deleteById(long id);
}