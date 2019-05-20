package com.pisces.integration.service.impl;

import org.springframework.stereotype.Service;

import com.pisces.integration.bean.DsSqlite;
import com.pisces.integration.bean.SqlDataSource;
import com.pisces.integration.dao.DsSQLiteDao;
import com.pisces.integration.service.DsSQLiteService;

@Service
class DsSQLiteServiceImpl extends SqlDataSourceServiceImpl<DsSqlite, DsSQLiteDao> implements DsSQLiteService {

	@Override
	protected String getDriverName() {
		return null;
	}

	@Override
	protected String getConnection(SqlDataSource dataSource) {
		return null;
	}

	@Override
	protected String existed(SqlDataSource dataSource, String tableName) {
		return null;
	}

}
