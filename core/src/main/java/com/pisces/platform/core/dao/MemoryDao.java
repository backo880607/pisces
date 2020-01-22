package com.pisces.platform.core.dao;

import com.pisces.platform.core.dao.impl.DaoImpl;
import com.pisces.platform.core.dao.impl.MemoryDaoImpl;
import com.pisces.platform.core.entity.EntityObject;
import com.pisces.platform.core.utils.EntityUtils;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

public class MemoryDao<T extends EntityObject> implements BaseDao<T> {
    private ThreadLocal<MemoryDaoImpl<T>> impl = new ThreadLocal<>();

    public MemoryDao() {
        DaoManager.register(this);
    }

    @SuppressWarnings("unchecked")
    private Class<T> getEntityClass() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
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
    public List<T> selectByIds(Collection<Long> ids) {
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
    public int insertList(List<T> recordList) {
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
        return impl.get().records.remove(key) != null ? 1 : 0;
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
        this.impl.set((MemoryDaoImpl<T>) impl);
    }
}
