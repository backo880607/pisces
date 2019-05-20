package com.pisces.core.primary.expression.calculate;

import com.pisces.core.entity.EntityObject;
import com.pisces.core.primary.expression.value.ValueAbstract;

public interface Calculate {
	ValueAbstract GetValue(EntityObject entity);
	int Parse(String str, int index);
	Class<?> getReturnClass();
}
