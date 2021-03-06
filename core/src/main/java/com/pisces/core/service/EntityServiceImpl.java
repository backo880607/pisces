package com.pisces.core.service;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.pisces.core.dao.BaseDao;
import com.pisces.core.entity.EntityObject;
import com.pisces.core.relation.Ioc;
import com.pisces.core.utils.AppUtils;
import com.pisces.core.utils.DateUtils;
import com.pisces.core.utils.IExpression;
import com.pisces.core.utils.PageParam;
import com.pisces.core.utils.Primary;

public abstract class EntityServiceImpl<T extends EntityObject, D extends BaseDao<T>> extends BaseServiceImpl implements EntityService<T> {
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
	public T create() {
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
	public T get() {
		return this.dao.select();
	}

	@Override
	public List<T> getAll() {
		return this.dao.selectAll();
	}
	
	@Override
	public T getById(long id) {
		return this.dao.selectByPrimaryKey(id);
	}
	
	@Override
	public List<T> getByIds(List<Long> ids) {
		return this.dao.selectByIds(ids);
	}
	
	@Override
	public List<T> get(PageParam param) {
		List<T> result = new LinkedList<>();
		List<T> records = this.getAll();
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
		if (param.getPageSize() <= 0) {
			return result;
		}
		
		int begin = (param.getPageNum() - 1) * param.getPageSize();
		if (begin < 0) {
			begin = 0;
		}
		
		final int end = Math.min(begin + param.getPageSize(), result.size());
		return result.subList(begin, end);
	}
	
	@Override
	public T get(Predicate<T> filter) {
		T result = null;
		final List<T> records = this.getAll();
		for (T record : records) {
			if (filter.test(record)) {
				result = record;
				break;
			}
		}
		
		return result;
	}
	
	@Override
	public List<T> getList(Predicate<T> filter) {
		List<T> result = new ArrayList<>();
		final List<T> records = this.getAll();
		for (T record : records) {
			if (filter.test(record)) {
				result.add(record);
			}
		}
		
		return result;
	}
	
	@Override
	public boolean exist(long id) {
		return this.dao.existsWithPrimaryKey(id);
	}

	@Override
	public int insert(T entity) {
		if (StringUtils.isEmpty(entity.getCreateBy())) {
			entity.setCreateBy(AppUtils.getUsername());
		}
		entity.setUpdateBy(AppUtils.getUsername());
		
		if (!DateUtils.isValid(entity.getCreateDate())) {
			entity.setCreateDate(new Date());
		}
		entity.setUpdateDate(new Date());
		return this.dao.insert(entity);
	}
	
	@Override
	public int insertList(List<T> entities) {
		for (T entity : entities) {
			if (StringUtils.isEmpty(entity.getCreateBy())) {
				entity.setCreateBy(AppUtils.getUsername());
			}
			entity.setUpdateBy(AppUtils.getUsername());
			
			if (!DateUtils.isValid(entity.getCreateDate())) {
				entity.setCreateDate(new Date());
			}
			entity.setUpdateDate(new Date());
		}
		return this.dao.insertList(entities);
	}

	@Override
	public int update(T entity) {
		entity.setUpdateBy(AppUtils.getUsername());
		entity.setUpdateDate(new Date());
		return this.dao.update(entity);
	}
	
	@Override
	public int updateList(List<T> entities) {
		for (T entity : entities) {
			entity.setUpdateBy(AppUtils.getUsername());
			entity.setUpdateDate(new Date());
		}
		
		return this.dao.updateList(entities);
	}

	@Override
	public int delete(T entity) {
		Ioc.delete(entity);
		return this.dao.delete(entity);
	}
	
	@Override
	public int deleteList(List<T> entities) {
		for (T entity : entities) {
			Ioc.delete(entity);
		}
		return this.dao.deleteList(entities);
	}

	@Override
	public int deleteById(long id) {
		Ioc.delete(getById(id));
		return this.dao.deleteByPrimaryKey(id);
	}
	
	@Override
	public int deleteByIds(List<Long> ids) {
		for (Long id : ids) {
			Ioc.delete(getById(id));
		}
		return this.dao.deleteByPrimaryKeys(ids);
	}
	
	@Override
	public void deleteImpl(EntityObject entity) {
		
	}
	
	@Override
	public void clear() {
		for (T entity : getAll()) {
			Ioc.delete(entity);
		}
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
			if (exp == null) {
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
}
