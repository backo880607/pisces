package com.pisces.core.primary.expression.value;

import com.pisces.core.entity.EntityObject;
import com.pisces.core.relation.RefBase;
import com.pisces.core.utils.StringUtils;

public class ValueList extends ValueAbstract {
	public RefBase value;
	
	public ValueList(RefBase value) {
		this.value = value;
	}
	
	@Override
	public Type getType() {
		return Type.List;
	}
	
	@Override
	public Class<?> getReturnClass() {
		return RefBase.class;
	}
	
	@Override
	public Object getValue() {
		return this.value;
	}
	
	@Override
	public ValueText toText() {
		return new ValueText(StringUtils.join(this.value, ";", (EntityObject entity) -> { 
			return String.valueOf(entity.getId()); 
		}));
	}
}
