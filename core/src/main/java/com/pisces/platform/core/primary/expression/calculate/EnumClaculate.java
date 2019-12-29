package com.pisces.platform.core.primary.expression.calculate;

import com.pisces.platform.core.entity.EntityObject;
import com.pisces.platform.core.primary.expression.EnumHelper;

public class EnumClaculate implements Calculate {
    private Enum<?> value;

    @Override
    public Object getValue(EntityObject entity) {
        return value;
    }

    @Override
    public int parse(String str, int index) {
        int temp = index;
        Class<?> enumCls = null;
        while (index < str.length()) {
            char curChar = str.charAt(index);
            if (curChar == '.') {
                String name = str.substring(temp, index);
                enumCls = EnumHelper.get(name);
                if (enumCls == null) {
                    return -1;
                }

                temp = index + 1;
            } else if (!Character.isAlphabetic(curChar) && !Character.isDigit(curChar) && curChar != '_') {
                break;
            }
            ++index;
        }

        if (temp < index) {
            String name = str.substring(temp, index);
            for (Object tso : enumCls.getEnumConstants()) {
                Enum<?> ts = (Enum<?>) tso;
                if (ts.name().equalsIgnoreCase(name)) {
                    this.value = ts;
                    return index;
                }
            }
        }
        return -1;
    }

    @Override
    public Class<?> getReturnClass() {
        return this.value.getClass();
    }
}
