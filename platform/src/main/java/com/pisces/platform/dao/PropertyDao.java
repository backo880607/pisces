package com.pisces.platform.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.pisces.core.dao.BaseDao;
import com.pisces.core.dao.DaoManager;
import com.pisces.core.dao.impl.DaoImpl;
import com.pisces.core.entity.EntityObject;
import com.pisces.core.entity.Property;
import com.pisces.core.enums.PROPERTY_TYPE;
import com.pisces.core.utils.EntityUtils;
import com.pisces.core.utils.Primary;
import com.pisces.rds.common.SQLDao;

@Component
public class PropertyDao implements BaseDao<Property> {
	class PropertyDaoImpl implements DaoImpl {
		public Map<Class<? extends EntityObject>, Map<String, Property>> properties = new HashMap<>();
		public void insert(Property property) {
			if (!property.getInitialized()) {
				Property newRecord = new Property();
				newRecord.init();
				EntityUtils.copyIgnoreNull(property, newRecord);
				property = newRecord;
			}
			if (property.belongClazz == null) {
				property.belongClazz = EntityUtils.getEntityClass(property.getBelongName());
			}
			if (property.sign == null) {
				property.sign = Primary.get().getRelationSign(property.belongClazz, property.getCode());
			}
			try {
				if (property.clazz == null) {
					if (property.sign != null) {
						property.clazz = Primary.get().getRelationClass(property.belongClazz, property.sign);
					} else {
						property.clazz = Class.forName(property.getTypeName());
					}
				}
				
				if (property.getMethod == null) {
					if (property.getInherent()) {
						property.getMethod = property.belongClazz.getMethod("get" + StringUtils.capitalize(property.getCode()));
					} else {
						property.getMethod = property.belongClazz.getMethod("getUserFields", String.class);
					}
				}
				
				if (property.setMethod == null) {
					if (property.getInherent()) {
						if (property.getType() != PROPERTY_TYPE.LIST) {
							property.setMethod = property.belongClazz.getMethod("set" + StringUtils.capitalize(property.getCode()), property.clazz);
						}
					} else {
						property.setMethod = property.belongClazz.getMethod("setUserFields", String.class, Object.class);
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			
			if (!StringUtils.isEmpty(property.getExpression())) {
				property.setModifiable(false);
			}
			properties.get(property.belongClazz).put(property.getCode(), property);
		}
	}
	private ThreadLocal<PropertyDaoImpl> impl = new ThreadLocal<>();

	@Autowired
	private SQLDao<Property> mapper;
	
	public PropertyDao() {
		DaoManager.register(this);
	}

	@Override
	public Property select() {
		throw new UnsupportedOperationException("select one property is not allowed");
	}

	@Override
	public List<Property> selectAll() {
		List<Property> properties = new ArrayList<Property>();
		for (Entry<Class<? extends EntityObject>, Map<String, Property>> entry : impl.get().properties.entrySet()) {
			for (Entry<String, Property> iter : entry.getValue().entrySet()) {
				properties.add(iter.getValue());
			}
		}
		return properties;
	}

	@Override
	public Property selectByPrimaryKey(Object key) {
		return mapper.selectByPrimaryKey(key);
	}

	@Override
	public List<Property> selectByIds(Collection<Long> ids) {
		return mapper.selectByIds(ids);
	}

	@Override
	public boolean existsWithPrimaryKey(Object key) {
		return mapper.existsWithPrimaryKey(key);
	}

	@Override
	public int insert(Property record) {
		mapper.insert(record);
		impl.get().insert(record);
		return 1;
	}

	@Override
	public int insertList(Collection<Property> recordList) {
		mapper.insertList(recordList);
		for (Property property : recordList) {
			impl.get().insert(property);
		}
		return recordList.size();
	}

	@Override
	public int update(Property record) {
		Property oldRecord = get(EntityUtils.getEntityClass(record.getBelongName()), record.getCode());
		if (oldRecord == null) {
			throw new UnsupportedOperationException("update a not existed property class:" + record.getBelongName() + " property:" + record.getCode());
		}
		
		mapper.update(record);
		if (oldRecord != record) {
			EntityUtils.copyIgnoreNull(record, oldRecord);
		}
		if (!StringUtils.isEmpty(oldRecord.getExpression())) {
			oldRecord.setModifiable(false);
		}
		return 1;
	}
	
	@Override
	public int updateList(Collection<Property> recordList) {
		return 0;
	}

	@Override
	public int delete(Property record) {
		Map<String, Property> properties = impl.get().properties.get(EntityUtils.getEntityClass(record.getBelongName()));
		if (properties == null) {
			return 0;
		}
		Property oldRecord = properties.get(record.getCode());
		if (oldRecord != null) {
			if (oldRecord.getInherent()) {
				throw new UnsupportedOperationException("can`t delete a inherent property class:" + record.getBelongName() + " property:" + record.getCode());
			}
			
			mapper.delete(oldRecord);
			properties.remove(record.getCode());
			return 1; 
		}
		return 0;
	}
	
	@Override
	public int deleteList(Collection<Property> recordList) {
		return 0;
	}

	@Override
	public int deleteByPrimaryKey(Object key) {
		boolean bFind = false;
		for (Entry<Class<? extends EntityObject>, Map<String, Property>> entry : impl.get().properties.entrySet()) {
			for (Entry<String, Property> iter : entry.getValue().entrySet()) {
				if (iter.getValue().getId().equals(key)) {
					if (iter.getValue().getInherent()) {
						throw new UnsupportedOperationException("can`t delete a inherent property id:" + key);
					}
					entry.getValue().remove(iter.getKey());
					bFind = true;
					break;
				}
			}
			if (bFind) {
				break;
			}
		}
		return bFind ? mapper.deleteByPrimaryKey(key) : 0;
	}
	
	@Override
	public int deleteByPrimaryKeys(Collection<Long> keyList) {
		return 0;
	}

	@Override
	public DaoImpl createDaoImpl() {
		return new PropertyDaoImpl();
	}

	@Override
	public void switchDaoImpl(DaoImpl impl) {
		this.impl.set((PropertyDaoImpl)impl);
	}

	@Override
	public void loadData() {
		for (Class<? extends EntityObject> clazz : EntityUtils.getEntityClasses()) {
			impl.get().properties.put(clazz, new HashMap<String, Property>());
		}
		List<Property> properties = mapper.selectAll();
		if (properties.isEmpty()) {
			properties = EntityUtils.getDefaultProperties();
			mapper.insertList(properties);
		}
		for (Property property : properties) {
			impl.get().insert(property);
		}
	}

	@Override
	public void sync() {
		
	}
	
	public List<Property> get(Class<? extends EntityObject> clazz) {
		List<Property> result = new ArrayList<Property>();
		Map<String, Property> properties = impl.get().properties.get(clazz);
		if (properties != null) {
			for (Entry<String, Property> entry : properties.entrySet()) {
				result.add(entry.getValue());
			}
		}
		
		return result;
	}
	
	public Property get(Class<? extends EntityObject> clazz, String code) {
		Map<String, Property> properties = impl.get().properties.get(clazz);
		return properties != null ? properties.get(code) : null;
	}
	
	public List<Property> getPrimaries(Class<? extends EntityObject> clazz) {
		List<Property> result = new ArrayList<Property>();
		Map<String, Property> properties = impl.get().properties.get(clazz);
		if (properties != null) {
			for (Entry<String, Property> entry : properties.entrySet()) {
				if (entry.getValue().getPrimaryKey()) {
					result.add(entry.getValue());
				}
			}
			
			if (result.isEmpty()) {
				result.add(properties.get("id"));
			}
		}
		
		return result;
	}
}
