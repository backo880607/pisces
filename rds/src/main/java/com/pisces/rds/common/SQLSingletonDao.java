package com.pisces.rds.common;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;

import com.pisces.core.dao.BaseDao;
import com.pisces.core.dao.DaoManager;
import com.pisces.core.dao.impl.DaoImpl;
import com.pisces.core.dao.impl.SingletonModifyDaoImpl;
import com.pisces.core.entity.EntityObject;

public class SQLSingletonDao<T extends EntityObject> extends SqlSessionDaoSupport implements BaseDao<T> {
	private ThreadLocal<SingletonModifyDaoImpl<T>> impl = new ThreadLocal<>();
	private Class<T> entityClass;
	
	@Autowired
	private SQLDao<T> mapper;
	
	@SuppressWarnings("unchecked")
	public SQLSingletonDao() {
		this.entityClass = (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	@Autowired
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		super.setSqlSessionTemplate(sqlSessionTemplate);
	}

	@Override
	protected void initDao() throws Exception {
		DaoManager.register(this);
	}
	
	public SQLDao<T> getMapper() { return mapper; }
	
	@Override
	public T select() {
		return impl.get().record;
	}

	@Override
	public List<T> selectAll() {
		ArrayList<T> result = new ArrayList<T>();
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
		if (record.getId() == impl.get().record.getId()) {
			impl.get().modified = true;
			return 1;
		}

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
	public DaoImpl createDaoImpl() {
		return new SingletonModifyDaoImpl<EntityObject>();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void switchDaoImpl(DaoImpl impl) {
		this.impl.set((SingletonModifyDaoImpl<T>)impl);
	}

	@Override
	public final void loadData() {
		List<T> objects = mapper.selectAll();
		if (objects.isEmpty()) {
			try {
				this.impl.get().record = entityClass.newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
			mapper.insert(this.impl.get().record);
		} else {
			for (T object : objects) {
				impl.get().record = object;
				break;
			}
		}
		
		this.impl.get().modified = false;
		afterLoadData();
	}
	
	@Override
	public void afterLoadData() {
	}

	@Override
	public void sync() {
		if (impl.get().modified) {
			mapper.updateByPrimaryKey(impl.get().record);
		}
	}

}
