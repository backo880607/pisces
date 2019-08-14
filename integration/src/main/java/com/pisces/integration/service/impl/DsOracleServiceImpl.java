package com.pisces.integration.service.impl;

import org.springframework.stereotype.Service;

import com.pisces.integration.bean.DsOracle;
import com.pisces.integration.bean.SqlDataSource;
import com.pisces.integration.dao.DsOracleDao;
import com.pisces.integration.service.DsOracleService;

@Service
class DsOracleServiceImpl extends SqlDataSourceServiceImpl<DsOracle, DsOracleDao> implements DsOracleService {

	@Override
	protected String getDriverName() {
		return "oracle.jdbc.driver.OracleDriver";
	}

	@Override
	protected String getConnection(SqlDataSource dataSource) {
		StringBuffer buffer = new StringBuffer("jdbc:oracle:thin:@");
		buffer.append(dataSource.getIp()).append(":").append(dataSource.getPort());
		buffer.append(":").append(dataSource.getDataBase());
		return buffer.toString();
	}

	@Override
	protected String existed(SqlDataSource dataSource, String tableName) {
		return null;
	}
}
