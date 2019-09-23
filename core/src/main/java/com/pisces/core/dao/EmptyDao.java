package com.pisces.core.dao;

import java.util.Collection;
import java.util.List;

import com.pisces.core.dao.impl.DaoImpl;
import com.pisces.core.entity.EntityObject;

public class EmptyDao<T extends EntityObject> implements BaseDao<T> {

	@Override
	public T select() {
		throw new UnsupportedOperationException("select empty dao is not allowed");
	}

	@Override
	public List<T> selectAll() {
		throw new UnsupportedOperationException("select empty dao is not allowed");
	}

	@Override
	public T selectByPrimaryKey(Object key) {
		throw new UnsupportedOperationException("select empty dao is not allowed");
	}

	@Override
	public List<T> selectByIds(Collection<Long> ids) {
		throw new UnsupportedOperationException("select empty dao is not allowed");
	}

	@Override
	public boolean existsWithPrimaryKey(Object key) {
		throw new UnsupportedOperationException("check empty dao is not allowed");
	}

	@Override
	public int insert(T record) {
		throw new UnsupportedOperationException("insert empty dao is not allowed");
	}

	@Override
	public int insertList(Collection<T> recordList) {
		throw new UnsupportedOperationException("insert empty dao is not allowed");
	}

	@Override
	public int update(T record) {
		throw new UnsupportedOperationException("update empty dao is not allowed");
	}
	
	@Override
	public int updateList(Collection<T> recordList) {
		throw new UnsupportedOperationException("update empty dao is not allowed");
	}

	@Override
	public int delete(T record) {
		throw new UnsupportedOperationException("delete empty dao is not allowed");
	}
	
	@Override
	public int deleteList(Collection<T> recordList) {
		throw new UnsupportedOperationException("delete empty dao is not allowed");
	}

	@Override
	public int deleteByPrimaryKey(Object key) {
		throw new UnsupportedOperationException("delete empty dao is not allowed");
	}
	
	@Override
	public int deleteByPrimaryKeys(Collection<Long> keyList) {
		throw new UnsupportedOperationException("delete empty dao is not allowed");
	}

	@Override
	public DaoImpl createDaoImpl() {
		throw new UnsupportedOperationException("empty dao is not allowed");
	}

	@Override
	public void switchDaoImpl(DaoImpl impl) {
		throw new UnsupportedOperationException("empty dao is not allowed");
	}

	@Override
	public void loadData() {
		throw new UnsupportedOperationException("empty dao is not allowed");
	}

	@Override
	public void sync() {
		throw new UnsupportedOperationException("empty dao is not allowed");
	}
}
