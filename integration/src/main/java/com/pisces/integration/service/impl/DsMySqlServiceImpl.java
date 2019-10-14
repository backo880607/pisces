package com.pisces.integration.service.impl;

import org.springframework.stereotype.Service;

import com.pisces.integration.enums.SQL_TYPE;
import com.pisces.rds.provider.base.MySqlProvider;

@Service
class DsMySqlServiceImpl extends SqlDataSourceServiceImpl<SQL_TYPE> {

	public DsMySqlServiceImpl() {
		super(new MySqlProvider());
	}

	@Override
	public SQL_TYPE getType() {
		return SQL_TYPE.MYSQL;
	}
}
