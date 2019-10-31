package com.pisces.integration.service.impl.sql;

import org.springframework.stereotype.Service;

import com.pisces.integration.enums.SQL_TYPE;
import com.pisces.rds.provider.base.SQLiteProvider;

@Service
class DsSQLiteService extends SqlDataSourceService<SQL_TYPE> {
	
	public DsSQLiteService() {
		super(new SQLiteProvider());
	}

	@Override
	public SQL_TYPE getType() {
		return SQL_TYPE.SQLITE;
	}
}
