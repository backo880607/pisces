package com.pisces.nosql.dao;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.mongodb.client.result.UpdateResult;
import com.pisces.core.dao.BaseDao;
import com.pisces.core.dao.impl.DaoImpl;
import com.pisces.core.entity.EntityObject;
import com.pisces.core.entity.Property;
import com.pisces.core.utils.AppUtils;
import com.pisces.core.utils.EntityUtils;

public class MongoDao<T extends EntityObject> implements BaseDao<T> {
	private Class<T> clazz;
	@Autowired
    private MongoTemplate mongoTemplate;
	
	@SuppressWarnings("unchecked")
	public MongoDao() {
		this.clazz = (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		EntityUtils.registerEntityClass(clazz);
	}

	@Override
	public T select() {
		return null;
	}

	@Override
	public List<T> selectAll() {
		Query query = new Query(Criteria.where("createBy").is(AppUtils.getUsername()));
		return mongoTemplate.find(query, this.clazz);
	}

	@Override
	public T selectByPrimaryKey(Object key) {
		Query query = new Query(Criteria.where("id").is(key));
		return mongoTemplate.findOne(query, this.clazz);
	}

	@Override
	public boolean existsWithPrimaryKey(Object key) {
		return false;
	}

	@Override
	public int insert(T record) {
		mongoTemplate.save(record);
		return 0;
	}

	@Override
	public int insertList(Collection<T> recordList) {
		return 0;
	}

	@Override
	public int updateByPrimaryKey(T record) {
		Query query = new Query(Criteria.where("id").is(record.getId()));
        Update update= new Update();
        List<Property> properties = EntityUtils.getProperties(this.clazz);
        for (Property property : properties) {
        	update.set(property.getName(), EntityUtils.getValue(record, property));
        }
        //更新查询返回结果集的第一条
        UpdateResult result = mongoTemplate.updateFirst(query, update, this.clazz);
        if(result != null)
            return (int) result.getMatchedCount();
        else
            return 0;
	}

	@Override
	public int delete(T record) {
		Query query = new Query(Criteria.where("id").is(record.getId()));
        mongoTemplate.remove(query, this.clazz);
		return 0;
	}

	@Override
	public int deleteByPrimaryKey(Object key) {
		Query query = new Query(Criteria.where("id").is(key));
        return mongoTemplate.remove(query, this.clazz) != null ? 1 : 0;
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
