package com.pisces.platform.core.converter;

import com.pisces.platform.core.entity.BaseDeserializer;
import com.pisces.platform.core.entity.Property;
import com.pisces.platform.core.utils.DateUtils;

import java.text.ParseException;
import java.util.Date;

public class DateTimeDeserializer extends BaseDeserializer<Date> {

    @Override
    public Date deserialize(Property property, String value) {
        try {
            return DateUtils.parse(value);
        } catch (ParseException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
