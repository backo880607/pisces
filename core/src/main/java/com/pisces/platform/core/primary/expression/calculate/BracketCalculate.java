package com.pisces.platform.core.primary.expression.calculate;

import com.pisces.platform.core.entity.EntityObject;
import com.pisces.platform.core.primary.expression.Expression;
import com.pisces.platform.core.primary.expression.ExpressionNode;

import java.util.Map.Entry;

public class BracketCalculate implements Calculate {
    private Expression value = new Expression();

    @Override
    public Object getValue(EntityObject entity) {
        return this.value.getValueImpl(entity);
    }

    @Override
    public int parse(String str, int index) {
        Entry<Integer, ExpressionNode> nd = this.value.create(str, ++index, false);
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
