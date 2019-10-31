package com.pisces.core.primary.expression.function;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.pisces.core.annotation.ELFunction;
import com.pisces.core.entity.EntityObject;

class EntityFunction {
	static void register(FunctionManager manager) {
		manager.registerInnerFunction(EntityFunction.class);
	}
	
	@ELFunction
	public static EntityObject funGet(Collection<EntityObject> entities, Map<Long, Object> filter) {
		for (EntityObject entity : entities) {
			Object subFilter = filter.get(entity.getId());
			if (subFilter.getClass() != Boolean.class) {
				continue;
			}
			
			if ((boolean)subFilter) {
				return entity;
			}
		}
		return null;
	}
	
	@ELFunction
	static Collection<EntityObject> funGetList(Collection<EntityObject> entities, Map<Long, Object> filter) {
		Collection<EntityObject> result = new ArrayList<EntityObject>();
		for (EntityObject entity : entities) {
			Object subFilter = filter.get(entity.getId());
			if (subFilter.getClass() != Boolean.class) {
				continue;
			}
			
			if ((boolean)subFilter) {
				result.add(entity);
			}
		}
		return result;
	}
	
	@ELFunction
	static Collection<EntityObject> funGetSortedList(Collection<EntityObject> entities, Map<Long, Object> sortValue, boolean asc, Map<Long, Object> filter) {
		List<EntityObject> copy = null;
		if (entities.getClass() == ArrayList.class) {
			copy = (List<EntityObject>)entities;
		} else {
			copy = new LinkedList<EntityObject>();
			for (EntityObject entity : entities) {
				copy.add(entity);
			}
		}
		if (filter != null) {
			Iterator<EntityObject> iter = copy.iterator();
			while (iter.hasNext()) {
				EntityObject entity = iter.next();
				Object subFilter = filter.get(entity.getId());
				if (subFilter.getClass() != Boolean.class || !(boolean)subFilter) {
					iter.remove();
				}
			}
		}
		
		Collections.sort(copy, new EntitySortRule(sortValue, asc));
		return copy;
	}
	
	@ELFunction
	static EntityObject funGetMax(Collection<EntityObject> entities, Map<Long, Object> sortValue, Map<Long, Object> filter) {
		Collection<EntityObject> result = funGetSortedList(entities, sortValue, false, filter);
		return result.isEmpty() ? null : result.iterator().next();
	}
	
	@ELFunction
	static EntityObject funGetMin(Collection<EntityObject> entities, Map<Long, Object> sortValue, Map<Long, Object> filter) {
		Collection<EntityObject> result = funGetSortedList(entities, sortValue, true, filter);
		return result.isEmpty() ? null : result.iterator().next();
	}
	
	@ELFunction
	static EntityObject funFirst(Collection<EntityObject> entities) {
		return entities.isEmpty() ? null : entities.iterator().next();
	}
	
	@ELFunction
	static EntityObject funLast(Collection<EntityObject> entities) {
		if (entities.isEmpty()) {
			return null;
		}
		
		return null;
	}
	
	@ELFunction
	static String funJoin(Collection<EntityObject> entities, Map<Long, Object> attrValue, String split, Map<Long, Object> filter) {
		List<EntityObject> result = new ArrayList<>();
		if (filter != null) {
			for (EntityObject entity : entities) {
				Object subFilter = filter.get(entity.getId());
				if (subFilter == null || subFilter.getClass() != Boolean.class) {
					continue;
				}
				
				if ((boolean)subFilter) {
					result.add(entity);
				}
			}
		} else {
			result.addAll(entities);
		}
		
		boolean bFirst = true;
		StringBuffer buffer = new StringBuffer();
		for (EntityObject entity : result) {
			if (!bFirst) {
				buffer.append(split);
			}
			final String text = BaseFunction.funToStr(attrValue.get(entity.getId()), null);
			buffer.append(text);
			bFirst = false;
		}
		
		return buffer.toString();
	}
	
	@ELFunction
	static boolean funIsType(EntityObject entity, String name) {
		return entity.getClass().getSimpleName().equalsIgnoreCase(name);
	}
	
	@ELFunction
	static EntityObject funAsType(EntityObject entity, String name) {
		if (!entity.getClass().getSimpleName().equalsIgnoreCase(name)) {
			return null;
		}
		
		return entity;
	}
	
	@ELFunction
	static Boolean funAny(Collection<EntityObject> entities, Map<Long, Object> condition, Map<Long, Object> filter) {
		if (filter != null) {
			Iterator<EntityObject> iter = entities.iterator();
			while (iter.hasNext()) {
				EntityObject entity = iter.next();
				Object subFilter = filter.get(entity.getId());
				if (subFilter == null || subFilter.getClass() != Boolean.class || !(boolean)subFilter) {
					iter.remove();
					condition.remove(entity.getId());
				}
			}
		}
		
		boolean bOK = false;
		for (Entry<Long, Object> entry : condition.entrySet()) {
			Object value = entry.getValue();
			if (value != null && value.getClass() == Boolean.class && (boolean)value) {
				bOK = true;
				break;
			}
		}
		return bOK;
	}
	
	@ELFunction
	static Boolean funAll(Collection<EntityObject> entities, Map<Long, Object> condition, Map<Long, Object> filter) {
		if (filter != null) {
			Iterator<EntityObject> iter = entities.iterator();
			while (iter.hasNext()) {
				EntityObject entity = iter.next();
				Object subFilter = filter.get(entity.getId());
				if (subFilter == null || subFilter.getClass() != Boolean.class || !(boolean)subFilter) {
					iter.remove();
					condition.remove(entity.getId());
				}
			}
		}
		
		boolean bOK = !condition.isEmpty();
		for (Entry<Long, Object> entry : condition.entrySet()) {
			Object value = entry.getValue();
			if (value == null || value.getClass() != Boolean.class || !(boolean)value) {
				bOK = false;
				break;
			}
		}
		return bOK;
	}
}
