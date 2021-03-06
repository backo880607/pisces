package com.pisces.core.primary.expression.calculate;

import java.util.Map.Entry;

import com.pisces.core.entity.EntityObject;
import com.pisces.core.primary.expression.Expression;
import com.pisces.core.primary.expression.ExpressionNode;

public class BracketCalculate implements Calculate {
	private Expression value = new Expression();

	@Override
	public Object GetValue(EntityObject entity) {
		return this.value.getValueImpl(entity);
	}

	@Override
	public int Parse(String str, int index) {
		Entry<Integer, ExpressionNode> nd = this.value.Create(str, ++index, false);
		if (nd.getValue() == null) {
			return -1;
		}
		this.value.root = nd.getValue();
		return nd.getKey() + 1;
	}

	@Override
	public Class<?> getReturnClass() {
		return this.value.getReturnClass();
	}

}
