package com.pisces.core.primary.expression.calculate;

import java.util.function.BiFunction;

import com.pisces.core.entity.EntityObject;
import com.pisces.core.primary.expression.OperType;
import com.pisces.core.primary.expression.function.FunctionManager;

public class OperTypeCalculate implements Calculate {
	BiFunction<Object, Object, Object> fun;
	BiFunction<Class<?>, Class<?>, Class<?>> returnClass;
	
	public OperTypeCalculate(OperType operType) {
		this.fun = FunctionManager.instance.getFunction(operType);
		this.returnClass = FunctionManager.instance.getReturnClass(operType);
	}
	
	@Override
	public Object GetValue(EntityObject entity) {
		throw new UnsupportedOperationException();
	}
	
	public Object GetValue(Object param1, Object param2, EntityObject entity) {
		Object value = fun.apply(param1, param2);
		if (value == null) {
			throw new NullPointerException();
		}
		return value;
	}
	
	@Override
	public int Parse(String str, int index) {
		return -1;
	}

	@Override
	public Class<?> getReturnClass() {
		throw new UnsupportedOperationException();
	}
	
	public Class<?> getReturnClass(Class<?> lClass, Class<?> rClass) {
		return returnClass.apply(lClass, rClass);
	}
}
