package com.pisces.integration.service.impl;

import org.springframework.stereotype.Service;

import com.pisces.integration.enums.SQL_TYPE;
import com.pisces.rds.provider.base.OracleProvider;

@Service
class DsOracleServiceImpl extends SqlDataSourceServiceImpl<SQL_TYPE> {
	
	public DsOracleServiceImpl() {
		super(new OracleProvider());
	}

	@Override
	public SQL_TYPE getType() {
		return SQL_TYPE.ORACLE;
	}
}
