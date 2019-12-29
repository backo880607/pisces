package com.pisces.platform.core.primary.expression.calculate;

import com.pisces.platform.core.entity.EntityObject;
import com.pisces.platform.core.primary.expression.OperType;
import com.pisces.platform.core.primary.expression.function.FunctionManager;

import java.util.function.BiFunction;

public class OperTypeCalculate implements Calculate {
    BiFunction<Object, Object, Object> fun;
    BiFunction<Class<?>, Class<?>, Class<?>> returnClass;

    public OperTypeCalculate(OperType operType) {
        this.fun = FunctionManager.instance.getFunction(operType);
        this.returnClass = FunctionManager.instance.getReturnClass(operType);
    }

    @Override
    public Object getValue(EntityObject entity) {
        throw new UnsupportedOperationException();
    }

    public Object getValue(Object param1, Object param2, EntityObject entity) {
        Object value = fun.apply(param1, param2);
        if (value == null) {
            throw new NullPointerException();
        }
        return value;
    }

    @Override
    public int parse(String str, int index) {
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
