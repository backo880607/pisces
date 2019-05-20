package com.pisces.core.primary.expression.value;

import com.pisces.core.entity.Property;
import com.pisces.core.primary.expression.exception.ValueException;

public class ValueNull extends ValueAbstract {
	public Type type;

	public ValueNull(Type vt) {
		this.type = vt;
	}
	
	@Override
	public Type getType() {
		return type;
	}

	@Override
	public Object getValue() {
		return null;
	}

	@Override
	public ValueText toText() {
		return new ValueText("");
	}
	
	public static ValueNull get(Property property) {
		Type vt = Type.None;
		switch (property.getType()) {
		case Boolean:
			break;
		default:
			throw new ValueException(property.getType() + " is not supported!");
		}
		
		return new ValueNull(vt);
	}
	
	public static ValueNull get(Class<?> clazz) {
		Type vt = Type.None;
		return new ValueNull(vt);
	}
}
