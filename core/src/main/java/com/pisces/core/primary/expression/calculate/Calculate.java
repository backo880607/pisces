package com.pisces.core.primary.expression.calculate;

import com.pisces.core.entity.EntityObject;

public interface Calculate {
	Object GetValue(EntityObject entity);
	int Parse(String str, int index);
	Class<?> getReturnClass();
}
