package com.pisces.integration.service.impl.sql;

import org.springframework.stereotype.Service;

import com.pisces.integration.enums.SQL_TYPE;
import com.pisces.rds.provider.base.OracleProvider;

@Service
class DsOracleService extends SqlDataSourceService<SQL_TYPE> {
	
	public DsOracleService() {
		super(new OracleProvider());
	}

	@Override
	public SQL_TYPE getType() {
		return SQL_TYPE.ORACLE;
	}
}
