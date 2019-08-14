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
		return "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	}

	@Override
	protected String getConnection(SqlDataSource dataSource) {
		StringBuffer buffer = new StringBuffer("jdbc:sqlserver://");
		buffer.append(dataSource.getIp()).append(":").append(dataSource.getPort()).append(";DatabaseName=").append(dataSource.getDataBase());
		return buffer.toString();
	}

	@Override
	protected String existed(SqlDataSource dataSource, String tableName) {
		StringBuffer buffer = new StringBuffer("SELECT COUNT(*) FROM sysobjects WHERE id= object_id('");
		buffer.append(tableName).append("')");
		return buffer.toString();
	}

}