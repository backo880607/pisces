package com.pisces.nosql.dao;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
/*import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;*/

//import com.mongodb.client.result.UpdateResult;
import com.pisces.core.dao.BaseDao;
import com.pisces.core.dao.impl.DaoImpl;
import com.pisces.core.entity.EntityObject;
import com.pisces.core.entity.Property;
import com.pisces.core.utils.AppUtils;
import com.pisces.core.utils.EntityUtils;

public class MongoDao<T extends EntityObject> implements BaseDao<T> {
	private Class<T> clazz;
	//@Autowired
    //private MongoTemplate mongoTemplate;
	
	@SuppressWarnings("unchecked")
	public MongoDao() {
		this.clazz = (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	@Override
	public T select() {
		return null;
	}

	@Override
	public List<T> selectAll() {
		/*
		 * Query query = new
		 * Query(Criteria.where("createBy").is(AppUtils.getUsername())); return
		 * mongoTemplate.find(query, this.clazz);
		 */
		return null;
	}

	@Override
	public T selectByPrimaryKey(Object key) {
		/*
		 * Query query = new Query(Criteria.where("id").is(key)); return
		 * mongoTemplate.findOne(query, this.clazz);
		 */
		return null;
	}

	@Override
	public boolean existsWithPrimaryKey(Object key) {
		return false;
	}

	@Override
	public int insert(T record) {
		//mongoTemplate.save(record);
		return 1;
	}

	@Override
	public int insertList(Collection<T> recordList) {
		return 0;
	}

	@Override
	public int update(T record) {
		/*
		 * Query query = new Query(Criteria.where("id").is(record.getId())); Update
		 * update= new Update(); List<Property> properties =
		 * AppUtils.getPropertyService().get(this.clazz); for (Property property :
		 * properties) { update.set(property.getName(), EntityUtils.getValue(record,
		 * property)); } //更新查询返回结果集的第一条 UpdateResult result =
		 * mongoTemplate.updateFirst(query, update, this.clazz); if(result != null)
		 * return (int) result.getMatchedCount();
		 */
        return 0;
	}
	
	@Override
	public int updateList(Collection<T> recordList) {
		return 0;
	}

	@Override
	public int delete(T record) {
		/*
		 * Query query = new Query(Criteria.where("id").is(record.getId()));
		 * mongoTemplate.remove(query, this.clazz);
		 */
		return 0;
	}
	
	@Override
	public int deleteList(Collection<T> recordList) {
		return 0;
	}

	@Override
	public int deleteByPrimaryKey(Object key) {
		/*
		 * Query query = new Query(Criteria.where("id").is(key)); return
		 * mongoTemplate.remove(query, this.clazz) != null ? 1 : 0;
		 */
		return 0;
	}
	
	@Override
	public int deleteByPrimaryKeys(Collection<Long> keyList) {
		return 0;
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
	public List<T> selectByIds(Collection<Long> ids) {
		return null;
	}
}
