package com.pisces.core.primary.expression.calculate;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.pisces.core.entity.EntityObject;
import com.pisces.core.entity.Property;
import com.pisces.core.enums.PropertyType;
import com.pisces.core.exception.ExpressionException;
import com.pisces.core.primary.expression.exception.ValueException;
import com.pisces.core.primary.expression.value.ValueAbstract;
import com.pisces.core.primary.expression.value.ValueBoolean;
import com.pisces.core.primary.expression.value.ValueDateTime;
import com.pisces.core.primary.expression.value.ValueDouble;
import com.pisces.core.primary.expression.value.ValueDuration;
import com.pisces.core.primary.expression.value.ValueEnum;
import com.pisces.core.primary.expression.value.ValueInt;
import com.pisces.core.primary.expression.value.ValueList;
import com.pisces.core.primary.expression.value.ValueNull;
import com.pisces.core.primary.expression.value.ValueObject;
import com.pisces.core.primary.expression.value.ValueText;
import com.pisces.core.relation.RefBase;
import com.pisces.core.relation.RefList;
import com.pisces.core.utils.EntityUtils;

public class FieldCalculate implements Calculate {
	private Property property = null;
	private List<Property> paths = new ArrayList<>();
	private boolean isList = false;
	
	private Object getValueImpl(EntityObject entity) {
		try {
			Object val = entity != null ? this.property.getInherent() ? this.property.getMethod.invoke(entity) :
				this.property.getMethod.invoke(entity, this.property.getCode()) : null;
				
			return val;
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	private ValueAbstract convertValue(Object value) {
		if (value == null) {
			return ValueNull.get(this.property);
		}
		switch (property.getType()) {
		case Boolean:
			return new ValueBoolean((boolean)value);
		case Short:
		case Integer:
		case Long:
			return new ValueInt((long)value);
		case Double:
			return new ValueDouble((double)value);
		case Date:
			return new ValueDateTime((Date)value);
		case Duration:
			return new ValueDuration((String)value);
		case Enum:
			return new ValueEnum((Enum<?>)value);
		case String:
			return new ValueText((String)value);
		case Object:
			return new ValueObject((EntityObject)value);
		case List:
			return new ValueList((RefBase)value);
		default:
			break;
		}
		
		throw new ValueException(this.property.getType() + " is not supported!");
	}
	
	/**
	 * 取级联对象列表实现
	 * 
	 * @param result
	 * @param entity
	 * @param args
	 * @param index
	 */
	private static void getListImpl(List<EntityObject> result, EntityObject entity, List<Property> paths, int index) {
		if (index >= paths.size()) {
			result.add(entity);
		} else {
			try {
				Property path = paths.get(index);
				Object nextObj = path.getMethod.invoke(entity);
				if (nextObj != null) {
					if (EntityObject.class.isAssignableFrom(nextObj.getClass())) {
						getListImpl(result, (EntityObject)nextObj, paths, index + 1);
					} else if (RefBase.class.isAssignableFrom(nextObj.getClass())) {
						for (EntityObject nextEntity : (RefBase)nextObj) {
							getListImpl(result, nextEntity, paths, index + 1);
						}
					}
				}
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private RefBase GetListValue(EntityObject entity) {
		List<EntityObject> entities = new ArrayList<>();
		getListImpl(entities, entity, paths, 0);
		RefBase result = new RefList();
		for (EntityObject curEntity : entities) {
			Object val = getValueImpl(curEntity);
			if (val == null) {
				continue;
			}
			if (this.property.getType() == PropertyType.Object) {
				result.add((EntityObject)val);
			} else if (this.property.getType() == PropertyType.List) {
				result.addAll((Collection<EntityObject>)val);
			}
		}
		return result;
	}
	
	public ValueAbstract GetValue(EntityObject entity) {
		if (this.isList) {
			return new ValueList(GetListValue(entity));
		}
		try {
			EntityObject nextEntity = entity;
			for (Property path : this.paths) {
				nextEntity = (EntityObject)path.getMethod.invoke(nextEntity);
				if (nextEntity == null) {
					break;
				}
			}
			convertValue(getValueImpl(nextEntity));
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
		}
		return convertValue(null);
	}
	
	@Override
	public int Parse(String str, int index) {
		return Parse(str, index, null);
	}
	
	@SuppressWarnings("unchecked")
	public int Parse(String str, int index, Class<? extends EntityObject> propertyClazz) {
		int temp = index;
		
		this.property = null;
		this.paths.clear();
		while (index < str.length()) {
			char curChar = str.charAt(index);
			if (curChar == '.') {
				String name = str.substring(temp, index);
				if (propertyClazz == null) {
					propertyClazz = EntityUtils.getEntityClass(name);
					if (propertyClazz == null) {
						throw new ExpressionException("invalid object name : " + name);
					}
				} else {
					Property path = EntityUtils.getProperty(propertyClazz, name);
					if (path == null) {
						throw new ExpressionException(propertyClazz.getName() + " has not property name : " + name);
					}
					this.paths.add(path);
					propertyClazz = (Class<? extends EntityObject>)EntityUtils.getPropertyClass(path);
				}
				
				if (propertyClazz == null) {
					return -1;
				}
				
				if (Collection.class.isAssignableFrom(propertyClazz)) {
					this.isList = true;
				} else if (!EntityObject.class.isAssignableFrom(propertyClazz)) {
					return -1;
				}
				
				temp = index + 1;
			} else if (!Character.isAlphabetic(curChar) && !Character.isDigit(curChar) && curChar != '_') {
				break;
			}
			
			++index;
		}
		
		if (temp < index) {
			String name = str.substring(temp, index);
			this.property = EntityUtils.getProperty(propertyClazz, name);
			if (this.property == null) {
				return -1;
			}
			propertyClazz = (Class<? extends EntityObject>) EntityUtils.getPropertyClass(this.property);
			if (propertyClazz == null) {
				return -1;
			}
			if (!this.isList) {
				this.isList = Collection.class.isAssignableFrom(propertyClazz);
			}
			
			return index;
		}
		
		return -1;
	}

	@Override
	public Class<?> getReturnClass() {
		return EntityUtils.getPropertyClass(this.property);
	}
}
