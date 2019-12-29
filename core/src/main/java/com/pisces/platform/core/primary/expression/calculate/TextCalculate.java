package com.pisces.platform.core.primary.expression.calculate;

import com.pisces.platform.core.entity.EntityObject;

public class TextCalculate implements Calculate {
    private String value;

    @Override
    public Object getValue(EntityObject entity) {
        return value;
    }

    public int parse(String str, int index) {
        int temp = ++index;
        while (index < str.length()) {
            if (str.charAt(index) == '\'') {
                value = str.substring(temp, index);
                return ++index;
            }
            ++index;
        }

        return -1;
    }

    @Override
    public Class<?> getReturnClass() {
        return String.class;
    }
}
