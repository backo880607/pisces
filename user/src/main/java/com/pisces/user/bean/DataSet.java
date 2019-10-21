package com.pisces.user.bean;

import javax.persistence.Table;

import com.pisces.core.entity.EntityCoding;

@Table(name = "USER_DATA_SET")
public class DataSet extends EntityCoding {
	
	@Override
	public void init() {
		super.init();
	}
}
