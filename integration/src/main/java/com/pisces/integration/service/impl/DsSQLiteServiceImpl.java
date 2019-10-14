package com.pisces.integration.service.impl;

import org.springframework.stereotype.Service;

import com.pisces.integration.enums.SQL_TYPE;
import com.pisces.rds.provider.base.SQLiteProvider;

@Service
class DsSQLiteServiceImpl extends SqlDataSourceServiceImpl<SQL_TYPE> {
	
	public DsSQLiteServiceImpl() {
		super(new SQLiteProvider());
	}

	@Override
	public SQL_TYPE getType() {
		return SQL_TYPE.SQLITE;
	}
}
