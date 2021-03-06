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
			throw new UnsupportedOperationException(e);
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
	public List<T> selectByIds(Collection<Long> ids) {
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
			throw new IllegalArgumentException("invalid entity id: " + record.getId());
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
	public int updateList(Collection<T> recordList) {
		for (T record : recordList) {
			update(record);
		}
		return recordList.size();
	}

	@Override
	public int delete(T record) {
		return deleteByPrimaryKey(record.getId());
	}
	
	@Override
	public int deleteList(Collection<T> recordList) {
		int count = 0;
		for (T record : recordList) {
			count += delete(record);
		}
		return count;
	}

	@Override
	public int deleteByPrimaryKey(Object key) {
		if (impl.get().records.remove(key) != null) {
			impl.get().deleteds.add((Long)key);
			return 1;
		}
		return 0;
	}
	
	@Override
	public int deleteByPrimaryKeys(Collection<Long> keyList) {
		int count = 0;
		for (Long key : keyList) {
			count += deleteByPrimaryKey(key);
		}
		return count;
	}
	
	@Override
	public void loadData() {
		List<T> objects = mapper.selectAll();
		for (T object : objects) {
			impl.get().records.put(object.getId(), object);
		}
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
			this.mapper.deleteByPrimaryKeys(impl.get().deleteds);
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
