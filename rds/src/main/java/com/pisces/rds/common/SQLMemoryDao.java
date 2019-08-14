package com.pisces.rds.common;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;

import com.pisces.core.dao.BaseDao;
import com.pisces.core.dao.DaoManager;
import com.pisces.core.dao.impl.DaoImpl;
import com.pisces.core.dao.impl.MemoryModifyDaoImpl;
import com.pisces.core.entity.EntityObject;
import com.pisces.core.exception.ExistedException;
import com.pisces.core.exception.OperandException;
import com.pisces.core.utils.EntityUtils;

public class SQLMemoryDao<T extends EntityObject> extends SqlSessionDaoSupport implements BaseDao<T> {
	private ThreadLocal<MemoryModifyDaoImpl<T>> impl = new ThreadLocal<>();
	
	@Autowired
	private SQLDao<T> mapper;
	
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
	
	@Autowired
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		super.setSqlSessionTemplate(sqlSessionTemplate);
	}
	
	@Override
	protected void initDao() throws Exception {
		DaoManager.register(this);
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
	public List<T> selectMap(Collection<Long> ids) {
		List<T> records = new ArrayList<>();
		for (Long id : ids) {
			T record = selectByPrimaryKey(id);
			if (record != null) {
				records.add(record);
			}
		}
		return records;
	}

	@Override
	public T selectByPrimaryKey(Object key) {
		return impl.get().records.get(key);
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
		if (impl.get().records.put(record.getId(), record) != null) {
			record.setModified(true);
		} else {
			record.setCreated(true);
		}
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
		
		if (!oldRecord.getCreated()) {
			oldRecord.setModified(true);
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
		impl.get().deleteds.add((Long)key);
		return 1;
	}
	
	@Override
	public final void loadData() {
		List<T> objects = mapper.selectAll();
		for (T object : objects) {
			impl.get().records.put(object.getId(), object);
		}
		afterLoadData();
	}
	
	@Override
	public void afterLoadData() {
	}

	@Override
	public void sync() {
		List<T> creates = new ArrayList<>();
		List<T> modifies = new ArrayList<>();
		for (Entry<Long, T> entry : impl.get().records.entrySet()) {
			T record = entry.getValue();
			if (record.getCreated()) {
				creates.add(record);
			} else if (record.getModified()) {
				modifies.add(record);
			}
		}
		
		if (!impl.get().deleteds.isEmpty()) {
			this.mapper.deleteList(impl.get().deleteds);
		}
		
		if (!creates.isEmpty()) {
			this.mapper.insertList(creates);
		}
		
		for (Entry<Long, T> entry : impl.get().records.entrySet()) {
			T record = entry.getValue();
			record.setCreated(false);
			record.setModified(false);
		}
	}

	@Override
	public DaoImpl createDaoImpl() {
		return new MemoryModifyDaoImpl<T>();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void switchDaoImpl(DaoImpl impl) {
		this.impl.set((MemoryModifyDaoImpl<T>)impl);
	}
}
