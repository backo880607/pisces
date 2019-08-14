package com.pisces.integration.service.impl;

import java.io.File;

import org.springframework.stereotype.Service;

import com.pisces.integration.bean.DsSqlite;
import com.pisces.integration.bean.SqlDataSource;
import com.pisces.integration.dao.DsSQLiteDao;
import com.pisces.integration.service.DsSQLiteService;

@Service
class DsSQLiteServiceImpl extends SqlDataSourceServiceImpl<DsSqlite, DsSQLiteDao> implements DsSQLiteService {

	@Override
	protected String getDriverName() {
		return "org.sqlite.JDBC";
	}

	@Override
	protected String getConnection(SqlDataSource dataSource) {
		StringBuffer buffer = new StringBuffer("jdbc:sqlite:");
		buffer.append(dataSource.getIp()).append(File.separator).append(dataSource.getDataBase()).append(".db");
		return buffer.toString();
	}

	@Override
	protected String existed(SqlDataSource dataSource, String tableName) {
		StringBuffer buffer = new StringBuffer("SELECT COUNT(*) FROM sqlite_master WHERE type='table' AND name='");
		buffer.append(tableName).append("'");
		return buffer.toString();
	}

}
