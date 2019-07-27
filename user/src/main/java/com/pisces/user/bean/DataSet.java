package com.pisces.user.bean;

import com.pisces.core.entity.EntityCoding;

import javax.persistence.Table;

import com.pisces.core.entity.EffectTaskType;

@Table(name = "user_data_set")
public class DataSet extends EntityCoding {
	private EffectTaskType type;

	public EffectTaskType getType() {
		return type;
	}

	public void setType(EffectTaskType type) {
		this.type = type;
	}
	
}
