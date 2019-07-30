package com.pisces.core.primary.expression.function;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.pisces.core.annotation.ELFunction;
import com.pisces.core.entity.EntityObject;
import com.pisces.core.primary.expression.value.Type;
import com.pisces.core.primary.expression.value.ValueAbstract;
import com.pisces.core.primary.expression.value.ValueBoolean;
import com.pisces.core.primary.expression.value.ValueList;
import com.pisces.core.primary.expression.value.ValueListAbstract;
import com.pisces.core.primary.expression.value.ValueObject;
import com.pisces.core.primary.expression.value.ValueText;
import com.pisces.core.relation.RefBase;
import com.pisces.core.relation.RefList;

class ObjectFunction {
	static void register(FunctionManager manager) {
		manager.registerUserFunction(ObjectFunction.class);
	}
	
	@ELFunction
	public static EntityObject funGet(RefBase entities, Map<Long, ValueAbstract> filter) {
		for (EntityObject entity : entities) {
			ValueAbstract subFilter = filter.get(entity.getId());
			if (subFilter.getType() != Type.BOOLEAN) {
				continue;
			}
			
			if (((ValueBoolean)subFilter).value) {
				return entity;
			}
		}
		return null;
	}
	
	static ValueAbstract funGetList(ValueAbstract param1, ValueAbstract param2) {
		RefBase entities = ((ValueList)param1).value;
		ValueList result = new ValueList(new RefList());
		ValueListAbstract filter = (ValueListAbstract)param2;
		for (EntityObject entity : entities) {
			ValueAbstract subFilter = filter.value.get(entity.getId());
			if (subFilter.getType() != Type.BOOLEAN) {
				continue;
			}
			
			if (((ValueBoolean)subFilter).value == true) {
				result.value.add(entity);
			}
		}
		return result;
	}
	
	static ValueAbstract funGetSortedList(ValueAbstract param1, ValueAbstract param2, ValueAbstract param3) {
		RefBase entities = ((ValueList)param1).value;
		
		if (param3 != null) {
			ValueListAbstract filter = (ValueListAbstract)param3;
			Iterator<EntityObject> iter = entities.iterator();
			while (iter.hasNext()) {
				EntityObject entity = iter.next();
				ValueAbstract subFilter = filter.value.get(entity.getId());
				if (subFilter.getType() != Type.BOOLEAN || !((ValueBoolean)subFilter).value) {
					iter.remove();
				}
			}
		}
		
		ValueListAbstract sortValue = (ValueListAbstract)param2;
		//Collections.sort(entities, new EntitySortRule(sortValue));
		return param1;
	}
	
	static ValueAbstract funGetMax(ValueAbstract param1, ValueAbstract param2, ValueAbstract param3) {
		ValueAbstract resultAbstract = funGetSortedList(param1, param2, param3);
		ValueList result = (ValueList)resultAbstract;
		if (result.value.isEmpty()) {
			return null;
		}
		
		return new ValueObject(result.value.last());
	}
	
	static ValueAbstract funGetMin(ValueAbstract param1, ValueAbstract param2, ValueAbstract param3) {
		ValueAbstract resultAbstract = funGetSortedList(param1, param2, param3);
		ValueList result = (ValueList)resultAbstract;
		if (result.value.isEmpty()) {
			return null;
		}
		
		return new ValueObject(result.value.first());
	}
	
	static ValueAbstract funFirst(ValueAbstract param) {
		return new ValueObject(((ValueList)param).value.get());
	}
	
	static ValueAbstract funLast(ValueAbstract param) {
		ValueList param1 = (ValueList)param;
		return new ValueObject(param1.value.isEmpty() ? null : param1.value.last());
	}
	
	/**
	 * 将多个文本字符串合并成一个
	 * @param params
	 * @return
	 */
	static ValueAbstract funJoin(ValueAbstract param1, ValueAbstract param2, ValueAbstract param3, ValueAbstract param4) {
		RefBase entities = ((ValueList)param1).value;
		List<EntityObject> result = new ArrayList<>();
		if (param4 != null) {
			ValueListAbstract filter = (ValueListAbstract)param4;
			for (EntityObject entity : entities) {
				ValueAbstract subFilter = filter.value.get(entity.getId());
				if (subFilter.getType() != Type.BOOLEAN) {
					continue;
				}
				
				if (((ValueBoolean)subFilter).value) {
					result.add(entity);
				}
			}
		} else {
			result.addAll(entities.collection());
		}
		
		boolean bFirst = true;
		StringBuffer buffer = new StringBuffer();
		ValueListAbstract attr = (ValueListAbstract)param2;
		ValueText split = (ValueText)param3;
		for (EntityObject entity : result) {
			ValueAbstract subValue = attr.value.get(entity.getId());
			ValueText text = subValue.toText();
			if (text != null && !text.value.isEmpty()) {
				if (!bFirst) {
					buffer.append(split.value);
				}
				buffer.append(text.value);
				bFirst = false;
			}
		}
		
		return new ValueText(buffer.toString());
	}
	
	static ValueAbstract funIsType(ValueAbstract param1, ValueAbstract param2) {
		EntityObject entity = ((ValueObject)param1).value;
		String name = ((ValueText)param2).value;
		return new ValueBoolean(entity.getClass().getSimpleName().equalsIgnoreCase(name));
	}
	
	static ValueAbstract funAsType(ValueAbstract param1, ValueAbstract param2) {
		EntityObject entity = ((ValueObject)param1).value;
		String name = ((ValueText)param2).value;
		if (!entity.getClass().getSimpleName().equalsIgnoreCase(name)) {
			return null;
		}
		
		return param1;
	}
	
	static ValueAbstract funLastList(List<ValueAbstract> params) {
		return null;
	}
	
	static ValueAbstract funAny(ValueAbstract param1, ValueAbstract param2, ValueAbstract param3) {
		RefBase entities = ((ValueList)param1).value;
		
		ValueListAbstract condition = (ValueListAbstract)param2;
		if (param3 != null) {
			ValueListAbstract filter = (ValueListAbstract)param3;
			Iterator<EntityObject> iter = entities.iterator();
			while (iter.hasNext()) {
				EntityObject entity = iter.next();
				ValueAbstract subFilter = filter.value.get(entity.getId());
				if (subFilter.getType() != Type.BOOLEAN || !((ValueBoolean)subFilter).value) {
					iter.remove();
					condition.value.remove(entity.getId());
				}
			}
		}
		
		boolean bOK = false;
		for (Entry<Long, ValueAbstract> entry : condition.value.entrySet()) {
			ValueAbstract value = entry.getValue();
			if (value.getType() == Type.BOOLEAN && ((ValueBoolean)value).value) {
				bOK = true;
				break;
			}
		}
		return new ValueBoolean(bOK);
	}
	
	static ValueAbstract funAll(ValueAbstract param1, ValueAbstract param2, ValueAbstract param3) {
		RefBase entities = ((ValueList)param1).value;
		ValueListAbstract condition = (ValueListAbstract)param2;
		if (param3 != null) {
			ValueListAbstract filter = (ValueListAbstract)param3;
			Iterator<EntityObject> iter = entities.iterator();
			while (iter.hasNext()) {
				EntityObject entity = iter.next();
				ValueAbstract subFilter = filter.value.get(entity.getId());
				if (subFilter.getType() != Type.BOOLEAN || !((ValueBoolean)subFilter).value) {
					iter.remove();
					condition.value.remove(entity.getId());
				}
			}
		}
		
		boolean bOK = !condition.value.isEmpty();
		for (Entry<Long, ValueAbstract> entry : condition.value.entrySet()) {
			ValueAbstract value = entry.getValue();
			if (value.getType() != Type.BOOLEAN || ((ValueBoolean)value).value != true) {
				bOK = false;
				break;
			}
		}
		return new ValueBoolean(bOK);
	}
}
