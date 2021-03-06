package com.pisces.core.dao;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import com.pisces.core.dao.impl.DaoImpl;
import com.pisces.core.dao.impl.SingletonDaoImpl;
import com.pisces.core.entity.EntityObject;
import com.pisces.core.utils.AppUtils;
import com.pisces.core.utils.EntityUtils;

public class SingletonDao<T extends EntityObject> implements BaseDao<T> {
	private ThreadLocal<SingletonDaoImpl<T>> impl = new ThreadLocal<>();
	private Class<T> entityClass;
	
	@SuppressWarnings("unchecked")
	public SingletonDao() {
		this.entityClass = (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
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
		return select();
	}
	
	@Override
	public List<T> selectByIds(Collection<Long> ids) {
		return selectAll();
	}

	@Override
	public boolean existsWithPrimaryKey(Object key) {
		return true;
	}

	@Override
	public int insert(T record) {
		throw new UnsupportedOperationException("insert Singleton entity is not allowed");
	}

	@Override
	public int insertList(Collection<T> recordList) {
		throw new UnsupportedOperationException("insert Singleton entity is not allowed");
	}

	@Override
	public int update(T record) {
		T oldRecord = select();
		if (oldRecord != record) {
			EntityUtils.copyIgnoreNull(record, oldRecord);
		}
		return 1;
	}
	
	@Override
	public int updateList(Collection<T> recordList) {
		if (recordList.isEmpty()) {
			return 0;
		}
		return update(recordList.iterator().next());
	}

	@Override
	public int delete(T record) {
		throw new UnsupportedOperationException("delete Singleton entity is not allowed");
	}
	
	@Override
	public int deleteList(Collection<T> recordList) {
		throw new UnsupportedOperationException("delete Singleton entity is not allowed");
	}

	@Override
	public int deleteByPrimaryKey(Object key) {
		throw new UnsupportedOperationException("delete Singleton entity is not allowed");
	}
	
	@Override
	public int deleteByPrimaryKeys(Collection<Long> keyList) {
		throw new UnsupportedOperationException("delete Singleton entity is not allowed");
	}

	@Override
	public void loadData() {
		try {
			this.impl.get().record = entityClass.newInstance();
			this.impl.get().record.init();
			this.impl.get().record.setCreateBy(AppUtils.getUsername());
			this.impl.get().record.setUpdateBy(AppUtils.getUsername());
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
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
