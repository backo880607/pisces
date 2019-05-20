package com.pisces.core.primary.expression.function;

import java.util.Comparator;

import com.pisces.core.entity.EntityObject;
import com.pisces.core.primary.expression.value.ValueAbstract;
import com.pisces.core.primary.expression.value.ValueBoolean;
import com.pisces.core.primary.expression.value.ValueListAbstract;
import com.pisces.core.primary.expression.value.ValueNull;

public class EntitySortRule implements Comparator<EntityObject> {
	private ValueListAbstract value;
	public EntitySortRule(ValueListAbstract sortValue) {
		this.value = sortValue;
	}
	
	private boolean isNull(ValueAbstract val) {
		return val == null || val.getClass() == ValueNull.class;
	}

	@Override
	public int compare(EntityObject arg0, EntityObject arg1) {
		ValueAbstract val1 = value.value.get(arg0.getId());
		ValueAbstract val2 = value.value.get(arg1.getId());
		if (isNull(val1) && isNull(val2)) {
			return 0;
		}
		if (isNull(val1) && !isNull(val2)) {
			return -1;
		}
		if (!isNull(val1) && isNull(val2)) {
			return 1;
		}
		
		if (((ValueBoolean)val1.less(val2)).value) {
			return -1;
		} else if (((ValueBoolean)val1.greater(val2)).value) {
			return 1;
		}
		return (arg0.getId() < arg1.getId()) ? -1 : 1;
	}

}
