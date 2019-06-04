package com.pisces.nosql.dao;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import com.pisces.core.dao.BaseDao;
import com.pisces.core.dao.DaoManager;
import com.pisces.core.dao.impl.DaoImpl;
import com.pisces.core.entity.EntityObject;
import com.pisces.core.utils.EntityUtils;
import com.pisces.nosql.utils.RedisUtils;

public class RedisDao<T extends EntityObject> implements BaseDao<T> {
	private Class<T> clazz;
	private RedisTemplate<String, T> redisTemplate;
	private BoundHashOperations<String, Long, T> operation;
	
	@Autowired
	private RedisConnectionFactory factory;
	
	@SuppressWarnings("unchecked")
	public RedisDao() {
		this.clazz = (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		EntityUtils.registerEntityClass(this.clazz);
		DaoManager.register(this);
	}
	
	private BoundHashOperations<String, Long, T> obtain() {
		if (operation == null) {
			synchronized (this) {
				if (operation == null) {
					redisTemplate = RedisUtils.obtainTemplate(this.clazz, factory);
					operation = redisTemplate.boundHashOps("");
				}
			}
		}
		return operation;
	}

	@Override
	public T select() {
		return null;
	}

	@Override
	public List<T> selectAll() {
		List<T> result = new ArrayList<>();
		Map<Long, T> records = obtain().entries();
		if (records != null) {
			for (Entry<Long, T> entry : records.entrySet()) {
				result.add(entry.getValue());
			}
		}
		
		return result;
	}

	@Override
	public T selectByPrimaryKey(Object key) {
		return obtain().get(key);
	}

	@Override
	public boolean existsWithPrimaryKey(Object key) {
		return obtain().hasKey(key);
	}

	@Override
	public int insert(T record) {
		obtain().put(record.getId(), record);
		return 1;
	}

	@Override
	public int insertList(Collection<T> recordList) {
		Map<Long, T> records = new HashMap<>();
		for (T record : recordList) {
			records.put(record.getId(), record);
		}
		obtain().putAll(records);
		return recordList.size();
	}

	@Override
	public int updateByPrimaryKey(T record) {
		obtain().put(record.getId(), record);
		return 1;
	}

	@Override
	public int delete(T record) {
		return obtain().delete(record.getId()) != null ? 1 : 0;
	}

	@Override
	public int deleteByPrimaryKey(Object key) {
		return obtain().delete(key) != null ? 1 : 0;
	}

	@Override
	public DaoImpl createDaoImpl() {
		return null;
	}

	@Override
	public void switchDaoImpl(DaoImpl impl) {
	}

	@Override
	public void loadData() {
	}
	
	@Override
	public void sync() {
	}

	@Override
	public List<T> selectMap(Collection<Long> ids) {
		return null;
	}

	@Override
	public void afterLoadData() {
	}

}
