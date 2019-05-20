package com.pisces.core.dao;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import com.pisces.core.dao.impl.DaoImpl;
import com.pisces.core.dao.impl.SingletonDaoImpl;
import com.pisces.core.entity.EntityObject;
import com.pisces.core.utils.EntityUtils;

public class SingletonDao<T extends EntityObject> implements BaseDao<T> {
	private ThreadLocal<SingletonDaoImpl<T>> impl = new ThreadLocal<>();
	private Class<T> entityClass;
	
	@SuppressWarnings("unchecked")
	public SingletonDao() {
		this.entityClass = (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		EntityUtils.registerEntityClass(this.entityClass);
		DaoManager.register(this);
	}
	
	@Override
	public T select() {
		return impl.get().record;
	}

	@Override
	public List<T> selectAll() {
		List<T> result = new LinkedList<>();
		result.add(impl.get().record);
		return result;
	}

	@Override
	public T selectByPrimaryKey(Object key) {
		return key.equals(impl.get().record.getId()) ? impl.get().record : null;
	}
	
	@Override
	public List<T> selectMap(Collection<Long> ids) {
		List<T> result = new ArrayList<>();
		final T record = this.select();
		if (ids.size() == 1 && ids.contains(record.getId())) {
			result.add(record);
		}
		
		return result;
	}

	@Override
	public boolean existsWithPrimaryKey(Object key) {
		return key.equals(impl.get().record.getId());
	}

	@Override
	public int insert(T record) {
		return 0;
	}

	@Override
	public int insertList(Collection<T> recordList) {
		return 0;
	}

	@Override
	public int updateByPrimaryKey(T record) {
		return 0;
	}

	@Override
	public int delete(T record) {
		return 0;
	}

	@Override
	public int deleteByPrimaryKey(Object key) {
		return 0;
	}

	@Override
	public final void loadData() {
		try {
			this.impl.get().record = entityClass.newInstance();
			afterLoadData();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void afterLoadData() {
	}
	
	@Override
	public void sync() {		
	}

	@Override
	public DaoImpl createDaoImpl() {
		return new SingletonDaoImpl<T>();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void switchDaoImpl(DaoImpl impl) {
		this.impl.set((SingletonDaoImpl<T>)impl);
	}

}
