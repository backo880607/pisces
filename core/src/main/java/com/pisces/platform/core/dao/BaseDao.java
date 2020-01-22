package com.pisces.platform.core.dao;

import com.pisces.platform.core.dao.impl.DaoImpl;
import com.pisces.platform.core.entity.EntityObject;

import java.util.Collection;
import java.util.List;

public interface BaseDao<T extends EntityObject> {
    T select();

    List<T> selectAll();

    T selectByPrimaryKey(Object key);

    List<T> selectByIds(Collection<Long> idList);

    boolean existsWithPrimaryKey(Object key);

    int insert(T record);

    int insertList(List<T> recordList);

    int update(T record);

    int updateList(Collection<T> recordList);

    int delete(T record);

    int deleteList(Collection<T> recordList);

    int deleteByPrimaryKey(Object key);

    int deleteByPrimaryKeys(Collection<Long> keyList);

    DaoImpl createDaoImpl();

    void switchDaoImpl(DaoImpl impl);

    void loadData();

    void sync();
}