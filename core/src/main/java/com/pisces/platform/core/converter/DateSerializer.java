package com.pisces.platform.core.converter;

import com.pisces.platform.core.entity.BaseSerializer;
import com.pisces.platform.core.utils.DateUtils;

import java.util.Date;

public class DateSerializer extends BaseSerializer<Date> {

    @Override
    public String serialize(Date value) {
        return DateUtils.format(value, DateUtils.DATE_FORMAT);
    }
}
