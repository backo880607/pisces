package com.pisces.core.converter;

import com.pisces.core.entity.BaseSerializer;
import com.pisces.core.entity.MultiEnum;

public class MultiEnumSerializer extends BaseSerializer<MultiEnum<? extends Enum<?>>> {

	@Override
	public String serialize(MultiEnum<? extends Enum<?>> value) {
		return value.toString();
	}
}
