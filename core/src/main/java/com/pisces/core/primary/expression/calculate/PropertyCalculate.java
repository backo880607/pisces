package com.pisces.core.primary.expression.calculate;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.pisces.core.config.CoreMessage;
import com.pisces.core.entity.EntityObject;
import com.pisces.core.entity.Property;
import com.pisces.core.enums.PROPERTY_TYPE;
import com.pisces.core.exception.ExpressionException;
import com.pisces.core.primary.expression.value.InvalidEnum;
import com.pisces.core.primary.expression.value.ValueAbstract;
import com.pisces.core.primary.expression.value.ValueBoolean;
import com.pisces.core.primary.expression.value.ValueDateTime;
import com.pisces.core.primary.expression.value.ValueDouble;
import com.pisces.core.primary.expression.value.ValueDuration;
import com.pisces.core.primary.expression.value.ValueEnum;
import com.pisces.core.primary.expression.value.ValueInt;
import com.pisces.core.primary.expression.value.ValueList;
import com.pisces.core.primary.expression.value.ValueText;
import com.pisces.core.relation.RefBase;
import com.pisces.core.relation.RefList;
import com.pisces.core.utils.AppUtils;
import com.pisces.core.utils.EntityUtils;

public class PropertyCalculate implements Calculate {
	private Property property = null;
	private List<Property> paths = new ArrayList<>();
	private boolean isList = false;
	
	private ValueAbstract convertValue(Object value) {
		if (value == null) {
			switch (property.getType()) {
			case BOOLEAN:
				return new ValueBoolean(false);
			case LONG:
				return new ValueInt(0);
			case DOUBLE:
				return new ValueDouble(0.0);
			case DATE:
				return new ValueDateTime(new Date(0), property.getType());
			case DURATION:
				return new ValueDuration("");
			case ENUM:
				return new ValueEnum(InvalidEnum.NONE);
			case STRING:
				return new ValueText("");
			case ENTITY:
			case LIST:
			default:
				break;
			}
		} else {
			switch (property.getType()) {
			case BOOLEAN:
				return new ValueBoolean((boolean)value);
			case LONG:
				return new ValueInt((long)value);
			case DOUBLE:
				return new ValueDouble((double)value);
			case DATE:
			case TIME:
			case DATE_TIME:
				return new ValueDateTime((Date)value, property.getType());
			case DURATION:
				return new ValueDuration((String)value);
			case ENUM:
				return new ValueEnum((Enum<?>)value);
			case STRING:
				return new ValueText((String)value);
			case ENTITY:
				break;
			case LIST:
				return new ValueList((RefBase)value);
			default:
				break;
			}
		}
		
		throw new UnsupportedOperationException(this.property.getType() + " is not supported!");
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
				Object relaEntity = paths.get(index).getMethod.invoke(entity);
				if (relaEntity != null) {
					if (paths.get(index).getType() == PROPERTY_TYPE.ENTITY) {
						getListImpl(result, (EntityObject)relaEntity, paths, index + 1);
					} else if (paths.get(index).getType() == PROPERTY_TYPE.LIST) {
						for (EntityObject rela : (RefBase)relaEntity) {
							getListImpl(result, rela, paths, index + 1);
						}
					}
				}
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				throw new UnsupportedOperationException(e.getMessage());
			}
		}
	}
	
	private RefBase GetListValue(EntityObject entity) {
		List<EntityObject> entities = new ArrayList<>();
		getListImpl(entities, entity, this.paths, 0);
		RefBase result = new RefList();
		for (EntityObject curEntity : entities) {
			Object val = EntityUtils.getValue(curEntity, this.property);
			if (val == null) {
				continue;
			}
			if (this.property.getType() == PROPERTY_TYPE.ENTITY) {
				result.add((EntityObject)val);
			} else if (this.property.getType() == PROPERTY_TYPE.LIST) {
				result.addAll(((RefBase)val).collection());
			}
		}
		return result;
	}
	
	public ValueAbstract GetValue(EntityObject entity) {
		if (this.isList) {
			return new ValueList(GetListValue(entity));
		}
		EntityObject relaEntity = entity;
		for (Property path : this.paths) {
			relaEntity = (EntityObject)EntityUtils.getValue(relaEntity, path);
			if (relaEntity == null) {
				throw new NullPointerException();
			}
		}
		return convertValue(EntityUtils.getValue(relaEntity, this.property));
	}
	
	@Override
	public int Parse(String str, int index) {
		return Parse(str, index, null);
	}
	
	@SuppressWarnings("unchecked")
	public int Parse(String str, int index, Class<? extends EntityObject> propertyClazz) {
		final int origin = index;
		int temp = index;
		
		this.property = null;
		this.paths.clear();
		this.isList = false;
		while (index < str.length()) {
			char curChar = str.charAt(index);
			if (curChar == '.') {
				String name = str.substring(temp, index);
				if (propertyClazz == null) {
					propertyClazz = EntityUtils.getEntityClass(name);
					if (propertyClazz == null) {
						throw new ExpressionException(CoreMessage.InvalidObjectName, name);
					}
				} else {
					Property path = AppUtils.getPropertyService().get(propertyClazz, name);
					if (path == null) {
						throw new ExpressionException(CoreMessage.InvalidProperty, propertyClazz.getName(), name);
					}
					if (path.getType() != PROPERTY_TYPE.ENTITY && path.getType() != PROPERTY_TYPE.LIST) {
						throw new ExpressionException(CoreMessage.NotEntityOrList, propertyClazz.getName(), name);
					}
					this.paths.add(path);
					propertyClazz = (Class<? extends EntityObject>)path.clazz;
					if (path.getType() == PROPERTY_TYPE.LIST) {
						this.isList = true;
					}
				}
				
				temp = index + 1;
			} else if (!Character.isAlphabetic(curChar) && !Character.isDigit(curChar) && curChar != '_') {
				break;
			}
			
			++index;
		}
		
		if (temp < index) {
			String name = str.substring(temp, index);
			this.property = AppUtils.getPropertyService().get(propertyClazz, name);
			if (this.property == null) {
				throw new ExpressionException(CoreMessage.InvalidProperty, propertyClazz.getName(), name);
			}
			if (this.property.getType() == PROPERTY_TYPE.ENTITY) {
				//throw new ExpressionException("last property: " + name + " cannot be object");
			}
			if (this.property.getType() == PROPERTY_TYPE.LIST) {
				this.isList = true;
			}
			
			return index;
		}
		
		throw new ExpressionException(CoreMessage.ExpressionError, str.substring(origin));
	}

	@Override
	public Class<?> getReturnClass() {
		return this.property.getType() != PROPERTY_TYPE.LIST ? this.property.clazz : RefBase.class;
	}
}
