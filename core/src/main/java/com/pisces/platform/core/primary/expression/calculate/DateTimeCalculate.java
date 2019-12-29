package com.pisces.platform.core.primary.expression.calculate;

import com.pisces.platform.core.config.CoreMessage;
import com.pisces.platform.core.entity.EntityObject;
import com.pisces.platform.core.exception.ExpressionException;
import com.pisces.platform.core.utils.DateUtils;

import java.text.ParseException;
import java.util.Date;

public class DateTimeCalculate implements Calculate {
    private Date value;

    @Override
    public Object getValue(EntityObject entity) {
        return this.value;
    }

    @Override
    public int parse(String str, int index) {
        int temp = ++index;
        while (index < str.length()) {
            if (str.charAt(index) == '#') {
                String text = str.substring(temp, index);
                try {
                    this.value = DateUtils.parse(text);
                } catch (ParseException e) {
                    try {
                        this.value = DateUtils.parse(text, DateUtils.DATE_FORMAT);
                    } catch (ParseException e2) {
                        try {
                            this.value = DateUtils.parse(text, DateUtils.TIME_FORMAT);
                        } catch (ParseException e3) {
                            throw new ExpressionException(CoreMessage.DateTimeError, str);
                        }
                    }
                }
                return ++index;
            }
            ++index;
        }

        return -1;
    }

    @Override
    public Class<?> getReturnClass() {
        return Date.class;
    }
}
