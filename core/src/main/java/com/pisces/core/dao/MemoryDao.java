package com.pisces.core.dao;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import com.pisces.core.dao.impl.DaoImpl;
import com.pisces.core.dao.impl.MemoryDaoImpl;
import com.pisces.core.entity.EntityObject;
import com.pisces.core.exception.ExistedException;
import com.pisces.core.exception.OperandException;
import com.pisces.core.utils.EntityUtils;

public class MemoryDao<T extends EntityObject> implements BaseDao<T> {
	private ThreadLocal<MemoryDaoImpl<T>> impl = new ThreadLocal<>();
	
	public MemoryDao() {
		EntityUtils.registerEntityClass(getEntityClass());
		DaoManager.register(this);
	}
	
	@SuppressWarnings("unchecked")
	private Class<T> getEntityClass() {
		return (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	private T create() {
		T entity = null;
		try {
			entity = getEntityClass().newInstance();
			entity.init();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new OperandException(e);
		}
		return entity;
	}
	
	@Override
	public T select() {
		Iterator<Entry<Long, T>> iter = impl.get().records.entrySet().iterator();
		return iter.hasNext() ? iter.next().getValue() : null;
	}

	@Override
	public List<T> selectAll() {
		return new ArrayList<>(impl.get().records.values());
	}

	@Override
	public T selectByPrimaryKey(Object key) {
		return impl.get().records.get(key);
	}
	
	@Override
	public List<T> selectMap(Collection<Long> ids) {
		List<T> result = new ArrayList<>();
		for (Long id : ids) {
			T record = this.selectByPrimaryKey(id);
			if (record != null) {
				result.add(record);
			}
		}
		return result;
	}

	@Override
	public boolean existsWithPrimaryKey(Object key) {
		return impl.get().records.containsKey(key);
	}

	@Override
	public int insert(T record) {
		if (!record.getInitialized()) {
			T newRecord = create();
			EntityUtils.copyIgnoreNull(record, newRecord);
			record = newRecord;
		}
		impl.get().records.put(record.getId(), record);
		return 1;
	}
	
	@Override
	public int insertList(Collection<T> recordList) {
		for (T record : recordList) {
			insert(record);
		}
		return recordList.size();
	}

	@Override
	public int update(T record) {
		T oldRecord = selectByPrimaryKey(record.getId());
		if (oldRecord == null) {
			throw new ExistedException("update a not existed entity");
		}
		
		if (oldRecord != record) {
			EntityUtils.copyIgnoreNull(record, oldRecord);
		}
		return 1;
	}

	@Override
	public int delete(T record) {
		return deleteByPrimaryKey(record.getId());
	}

	@Override
	public int deleteByPrimaryKey(Object key) {
		impl.get().records.remove(key);
		return 1;
	}
	
	@Override
	public final void loadData() {
		afterLoadData();
	}
	
	@Override
	public void afterLoadData() {
	}
	
	@Override
	public void sync() {
	}

	@Override
	public DaoImpl createDaoImpl() {
		return new MemoryDaoImpl<T>();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void switchDaoImpl(DaoImpl impl) {
		this.impl.set((MemoryDaoImpl<T>)impl);
	}
}
