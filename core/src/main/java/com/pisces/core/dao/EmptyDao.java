package com.pisces.core.dao;

import java.util.Collection;
import java.util.List;

import com.pisces.core.dao.impl.DaoImpl;
import com.pisces.core.entity.EntityObject;
import com.pisces.core.exception.NotImplementedException;

public class EmptyDao<T extends EntityObject> implements BaseDao<T> {

	@Override
	public T select() {
		throw new NotImplementedException("insert global entity is not allowed");
	}

	@Override
	public List<T> selectAll() {
		throw new NotImplementedException("insert global entity is not allowed");
	}

	@Override
	public T selectByPrimaryKey(Object key) {
		throw new NotImplementedException("insert global entity is not allowed");
	}

	@Override
	public List<T> selectMap(Collection<Long> ids) {
		throw new NotImplementedException("insert global entity is not allowed");
	}

	@Override
	public boolean existsWithPrimaryKey(Object key) {
		throw new NotImplementedException("insert global entity is not allowed");
	}

	@Override
	public int insert(T record) {
		throw new NotImplementedException("insert global entity is not allowed");
	}

	@Override
	public int insertList(Collection<T> recordList) {
		throw new NotImplementedException("insert global entity is not allowed");
	}

	@Override
	public int update(T record) {
		throw new NotImplementedException("insert global entity is not allowed");
	}

	@Override
	public int delete(T record) {
		throw new NotImplementedException("insert global entity is not allowed");
	}

	@Override
	public int deleteByPrimaryKey(Object key) {
		throw new NotImplementedException("insert global entity is not allowed");
	}

	@Override
	public DaoImpl createDaoImpl() {
		throw new NotImplementedException("insert global entity is not allowed");
	}

	@Override
	public void switchDaoImpl(DaoImpl impl) {
		throw new NotImplementedException("insert global entity is not allowed");
	}

	@Override
	public void loadData() {
		throw new NotImplementedException("insert global entity is not allowed");
	}

	@Override
	public void sync() {
		throw new NotImplementedException("insert global entity is not allowed");
	}

}
