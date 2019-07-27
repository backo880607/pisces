package com.pisces.core.primary.expression.calculate;

import java.util.List;
import java.util.function.Function;

import com.pisces.core.entity.EntityObject;
import com.pisces.core.primary.expression.OperType;
import com.pisces.core.primary.expression.exception.FunctionException;
import com.pisces.core.primary.expression.function.FunctionManager;
import com.pisces.core.primary.expression.value.ValueAbstract;

public class OperTypeCalculate implements Calculate {
	Function<List<ValueAbstract>, ValueAbstract> fun;
	
	public OperTypeCalculate(OperType operType) {
		this.fun = FunctionManager.instance.getFunction(operType);
	}
	
	@Override
	public ValueAbstract GetValue(EntityObject entity) {
		throw new FunctionException();
	}
	
	public ValueAbstract GetValue(List<ValueAbstract> params, Object obj) {
		return fun.apply(params);
	}
	
	@Override
	public int Parse(String str, int index) {
		return -1;
	}

	@Override
	public Class<?> getReturnClass() {
		return null;
	}
}
