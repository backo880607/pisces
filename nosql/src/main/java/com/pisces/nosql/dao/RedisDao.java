package com.pisces.nosql.dao;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import com.pisces.core.dao.BaseDao;
import com.pisces.core.dao.DaoManager;
import com.pisces.core.dao.impl.DaoImpl;
import com.pisces.core.entity.EntityObject;
import com.pisces.core.utils.EntityUtils;
import com.pisces.nosql.utils.RedisUtils;

public class RedisDao<T extends EntityObject> implements BaseDao<T> {
	private Class<T> clazz;
	private RedisTemplate<Long, T> redisTemplate;
	
	@Autowired
	private RedisConnectionFactory factory;
	
	@SuppressWarnings("unchecked")
	public RedisDao() {
		this.clazz = (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		EntityUtils.registerEntityClass(this.clazz);
		DaoManager.register(this);
	}
	
	private RedisTemplate<Long, T> obtain() {
		if (redisTemplate == null) {
			synchronized (this) {
				if (redisTemplate == null) {
					redisTemplate = RedisUtils.obtainTemplate(this.clazz, factory);
				}
			}
		}
		return redisTemplate;
	}

	@Override
	public T select() {
		return null;
	}

	@Override
	public List<T> selectAll() {
		return null;
	}

	@Override
	public T selectByPrimaryKey(Object key) {
		return obtain().opsForValue().get(key);
	}

	@Override
	public boolean existsWithPrimaryKey(Object key) {
		return obtain().hasKey((Long)key);
	}

	@Override
	public int insert(T record) {
		obtain().opsForValue().set(record.getId(), record);
		return 1;
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
		return obtain().delete(record.getId()) ? 1 : 0;
	}

	@Override
	public int deleteByPrimaryKey(Object key) {
		return obtain().delete((Long)key) ? 1 : 0;
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
