package com.pisces.user.bean;

import com.pisces.core.entity.EntityCoding;
import com.pisces.core.entity.EffectTaskType;

public class DataSet extends EntityCoding {
	private EffectTaskType type;

	public EffectTaskType getType() {
		return type;
	}

	public void setType(EffectTaskType type) {
		this.type = type;
	}
	
}
