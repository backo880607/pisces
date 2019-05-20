package com.pisces.core.service;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.pisces.core.dao.BaseDao;
import com.pisces.core.entity.EntityObject;
import com.pisces.core.entity.Property;
import com.pisces.core.utils.AppUtils;
import com.pisces.core.utils.EntityUtils;
import com.pisces.core.utils.IExpression;
import com.pisces.core.utils.PageParam;
import com.pisces.core.utils.Primary;

public abstract class EntityServiceImpl<T extends EntityObject, D extends BaseDao<T>> extends BaseServiceImpl implements EntityService<T> {
protected Logger log = LoggerFactory.getLogger(this.getClass());
	
	private Class<T> entityClass;

	@Autowired
	private D dao;
	
	@SuppressWarnings("unchecked")
	public EntityServiceImpl() {
		entityClass = (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		ServiceManager.register(entityClass, this);
	}
	
	protected D getDao() {
		return this.dao;
	}
	
	@Override
	public BaseDao<T> getBaseDao() {
		return this.dao;
	}
	
	public Class<T> getEntityClass() {
		return entityClass;
	}
	
	@Override
	public void init() {}
	
	@Override
	public T create() throws InstantiationException, IllegalAccessException {
		T entity = getEntityClass().newInstance();
		return entity;
	}

	@Override
	public List<T> selectAll() {
		return this.dao.selectAll();
	}
	
	@Override
	public List<T> select(PageParam param) {
		List<T> result = new LinkedList<>();
		List<T> records = this.selectAll();
		IExpression filterExp = Primary.get().createExpression(param.getFilter());
		if (filterExp != null) {
			for (T entity : records) {
				if (filterExp.getBoolean(entity)) {
					result.add(entity);
				}
			}
		} else {
			result.addAll(records);
		}
		
		sort(result, param.getOrderBy());
		final int begin = (param.getPageNum() - 1) * param.getPageSize();
		final int end = Math.min(begin + param.getPageSize(), result.size());
		return result.subList(begin, end);
	}

	@Override
	public T selectById(long id) {
		return this.dao.selectByPrimaryKey(id);
	}
	
	public T select() {
		return this.dao.select();
	}
	
	public T select(Predicate<T> filter) {
		T result = null;
		final List<T> records = this.selectAll();
		for (T record : records) {
			if (filter.test(record)) {
				result = record;
				break;
			}
		}
		
		return result;
	}
	
	public List<T> selectList(Predicate<T> filter) {
		List<T> result = new ArrayList<>();
		final List<T> records = this.selectAll();
		for (T record : records) {
			if (filter.test(record)) {
				result.add(record);
			}
		}
		
		return result;
	}

	@Override
	public int insert(T entity) {
		if (entity.getCreateBy() == null) {
			entity.setCreateBy(AppUtils.getUsername());
		}
		entity.setUpdateBy(AppUtils.getUsername());
		
		if (entity.getCreateDate() == null) {
			entity.setCreateDate(new Date());
		}
		entity.setUpdateDate(new Date());
		return this.dao.insert(entity);
	}

	@Override
	public int update(T entity) {
		entity.setUpdateBy(AppUtils.getUsername());
		entity.setUpdateDate(new Date());
		return this.dao.updateByPrimaryKey(entity);
	}

	@Override
	public int delete(T entity) {
		return this.dao.delete(entity);
	}

	@Override
	public int deleteById(long id) {
		return this.dao.deleteByPrimaryKey(id);
	}
	
	public static <T extends EntityObject> boolean sort(List<T> entities, String str) {
		if (entities == null || str == null || str.isEmpty()) {
			return true;
		}
		
		class Data {
			IExpression exp;
			boolean asc;
		}
		List<Data> datas = new ArrayList<>();
		String[] expGroups = str.split(";");
		for (String expGroup : expGroups) {
			String[] strExpes = expGroup.split("\\|");
			if (strExpes.length != 2) {
				return false;
			}
			
			IExpression exp = Primary.get().createExpression(strExpes[0]);
			if (exp == null) {	// 排序表达式错误
				return false;
			}
			
			Data data = new Data();
			data.exp = exp;
			data.asc = strExpes[1].equalsIgnoreCase("ASC");
			datas.add(data);
		}
		
		if (datas.isEmpty()) {
			return true;
		}
		
		Collections.sort(entities, new Comparator<T>() {
			@Override
			public int compare(T o1, T o2) {
				int value = 0;
				for (Data data : datas) {
					value = data.exp.compare(o1, o2);
					if (value != 0) {
						if (!data.asc) {
							value = -value;
						}
						break;
					}
				}
				return value;
			}
		});
		return true;
	}
	
	@Override
	public List<Property> selectProperties(boolean onlyDisplay) {
		List<Property> properties = EntityUtils.getProperties(getEntityClass());
		if (onlyDisplay) {
			Iterator<Property> iter = properties.iterator();
			while (iter.hasNext()) {
				Property property = iter.next();
				if (!property.getDisplay() || !property.getVisiable()) {
					iter.remove();
				}
			}
		}
		return properties;
	}
}
