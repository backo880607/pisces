package com.pisces.platform.core.converter;

import com.pisces.platform.core.entity.BaseSerializer;
import com.pisces.platform.core.entity.DateDur;

public class DurationSerializer extends BaseSerializer<DateDur> {

    @Override
    public String serialize(DateDur value) {
        return value.getString();
    }

}
