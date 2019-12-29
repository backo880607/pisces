package com.pisces.platform.core.converter;

import com.pisces.platform.core.entity.BaseDeserializer;
import com.pisces.platform.core.entity.Property;
import com.pisces.platform.core.utils.DateUtils;

import java.sql.Date;
import java.text.ParseException;

public class SqlDateJsonDeserializer extends BaseDeserializer<Date> {

    @Override
    public Date deserialize(Property property, String value) {
        try {
            return new Date(DateUtils.parse(value).getTime());
        } catch (ParseException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
