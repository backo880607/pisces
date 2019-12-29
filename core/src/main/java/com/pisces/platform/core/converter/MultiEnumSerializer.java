package com.pisces.platform.core.converter;

import com.pisces.platform.core.entity.BaseSerializer;
import com.pisces.platform.core.entity.MultiEnum;

public class MultiEnumSerializer extends BaseSerializer<MultiEnum<? extends Enum<?>>> {

    @Override
    public String serialize(MultiEnum<? extends Enum<?>> value) {
        return value.toString();
    }
}
