package com.pisces.core.dao;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import com.pisces.core.dao.impl.DaoImpl;
import com.pisces.core.entity.EntityObject;
import com.pisces.core.utils.EntityUtils;

public class GlobalDao<T extends EntityObject> implements BaseDao<T> {
	private T record;

	public GlobalDao() {
		@SuppressWarnings("unchecked")
		Class<T> clazz = (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		try {
			record = clazz.newInstance();
			record.init();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public T select() {
		return this.record;
	}

	@Override
	public List<T> selectAll() {
		List<T> result = new LinkedList<>();
		result.add(this.record);
		return result;
	}

	@Override
	public T selectByPrimaryKey(Object key) {
		return this.record;
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
		throw new UnsupportedOperationException("insert global entity is not allowed");
	}

	@Override
	public int insertList(Collection<T> recordList) {
		throw new UnsupportedOperationException("insert global entity is not allowed");
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
		throw new UnsupportedOperationException("delete global entity is not allowed");
	}
	
	@Override
	public int deleteList(Collection<T> recordList) {
		throw new UnsupportedOperationException("delete global entity is not allowed");
	}

	@Override
	public int deleteByPrimaryKey(Object key) {
		throw new UnsupportedOperationException("delete global entity is not allowed");
	}
	
	@Override
	public int deleteByPrimaryKeys(Collection<Long> keyList) {
		throw new UnsupportedOperationException("delete global entity is not allowed");
	}

	@Override
	public void loadData() {
		throw new UnsupportedOperationException("global dao is not allowed");
	}
	
	@Override
	public void sync() {
		throw new UnsupportedOperationException("global dao is not allowed");
	}

	@Override
	public DaoImpl createDaoImpl() {
		throw new UnsupportedOperationException("global dao is not allowed");
	}

	@Override
	public void switchDaoImpl(DaoImpl impl) {
		throw new UnsupportedOperationException("global dao is not allowed");
	}
}
