package com.pisces.integration.service.impl;

import org.springframework.stereotype.Service;

import com.pisces.integration.enums.SQL_TYPE;
import com.pisces.rds.provider.base.SqlServerProvider;

@Service
class DsSQLServerServiceImpl extends SqlDataSourceServiceImpl<SQL_TYPE> {
	
	public DsSQLServerServiceImpl() {
		super(new SqlServerProvider());
	}

	@Override
	public SQL_TYPE getType() {
		return SQL_TYPE.SQLSERVER;
	}

}