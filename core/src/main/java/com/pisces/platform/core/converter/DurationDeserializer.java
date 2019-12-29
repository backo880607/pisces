package com.pisces.platform.core.converter;

import com.pisces.platform.core.entity.BaseDeserializer;
import com.pisces.platform.core.entity.DateDur;
import com.pisces.platform.core.entity.Property;

public class DurationDeserializer extends BaseDeserializer<DateDur> {

    @Override
    public DateDur deserialize(Property property, String value) {
        return new DateDur(value);
    }
}
