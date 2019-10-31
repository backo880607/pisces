package com.pisces.integration.service.impl.sql;

import org.springframework.stereotype.Service;

import com.pisces.integration.enums.SQL_TYPE;
import com.pisces.rds.provider.base.MySqlProvider;

@Service
class DsMySqlService extends SqlDataSourceService<SQL_TYPE> {

	public DsMySqlService() {
		super(new MySqlProvider());
	}

	@Override
	public SQL_TYPE getType() {
		return SQL_TYPE.MYSQL;
	}
}
