package com.pisces.core.primary.expression.value;

import com.pisces.core.entity.EntityObject;

public class ValueObject extends ValueAbstract {
	public EntityObject value;

	public ValueObject(EntityObject value) {
		this.value = value;
	}
	
	@Override
	public Type getType() {
		return Type.Object;
	}
	
	@Override
	public Object getValue() {
		return this.value;
	}

	@Override
	public ValueText toText() {
		return new ValueText(this.value != null ? String.valueOf(this.value.getId()) : "");
	}
}
