package com.pisces.platform.core.converter;

import com.pisces.platform.core.entity.BaseSerializer;
import com.pisces.platform.core.utils.DateUtils;

import java.sql.Date;

public class SqlDateJsonSerializer extends BaseSerializer<Date> {

    @Override
    public String serialize(Date value) {
        return DateUtils.format(value);
    }

}
