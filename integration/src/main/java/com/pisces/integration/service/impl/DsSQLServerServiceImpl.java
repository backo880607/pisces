package com.pisces.integration.service.impl;

import org.springframework.stereotype.Service;

import com.pisces.integration.bean.DsSqlServer;
import com.pisces.integration.bean.SqlDataSource;
import com.pisces.integration.dao.DsSQLServerDao;
import com.pisces.integration.service.DsSQLServerService;

@Service
class DsSQLServerServiceImpl extends SqlDataSourceServiceImpl<DsSqlServer, DsSQLServerDao> implements DsSQLServerService {

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
