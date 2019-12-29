package com.pisces.platform.user.bean;

import com.pisces.platform.core.entity.EntityCoding;

import javax.persistence.Table;

@Table(name = "USER_DATA_SET")
public class DataSet extends EntityCoding {
	
	@Override
	public void init() {
		super.init();
	}
}
