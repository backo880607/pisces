package com.pisces.integration.service.impl.sql;

import org.springframework.stereotype.Service;

import com.pisces.integration.enums.SQL_TYPE;
import com.pisces.rds.provider.base.SqlServerProvider;

@Service
class DsSQLServerService extends SqlDataSourceService<SQL_TYPE> {
	
	public DsSQLServerService() {
		super(new SqlServerProvider());
	}

	@Override
	public SQL_TYPE getType() {
		return SQL_TYPE.SQLSERVER;
	}

}