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
import com.pisces.core.utils.EntityUtils;

import tk.mybatis.mapper.common.Mapper;

public class SQLSingletonDao<T extends EntityObject> extends SqlSessionDaoSupport implements BaseDao<T> {
	private ThreadLocal<SingletonModifyDaoImpl<T>> impl = new ThreadLocal<>();
	private Class<T> entityClass;
	
	@Autowired
	private Mapper<T> mapper;
	
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
		
		impl.get().modified = true;
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
	public DaoImpl createDaoImpl() {
		return new SingletonModifyDaoImpl<EntityObject>();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void switchDaoImpl(DaoImpl impl) {
		this.impl.set((SingletonModifyDaoImpl<T>)impl);
	}

	@Override
	public void loadData() {
		List<T> objects = mapper.selectAll();
		if (objects.isEmpty()) {
			try {
				this.impl.get().record = entityClass.newInstance();
				this.impl.get().record.init();
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
	}

	@Override
	public void sync() {
		if (impl.get().modified) {
			mapper.updateByPrimaryKey(impl.get().record);
		}
	}
}
