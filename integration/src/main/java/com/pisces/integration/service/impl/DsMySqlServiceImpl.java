package com.pisces.integration.service.impl;

import org.springframework.stereotype.Service;

import com.pisces.integration.bean.DsMySql;
import com.pisces.integration.bean.SqlDataSource;
import com.pisces.integration.dao.DsMySqlDao;
import com.pisces.integration.service.DsMySqlService;

@Service
class DsMySqlServiceImpl extends SqlDataSourceServiceImpl<DsMySql, DsMySqlDao> implements DsMySqlService {

	@Override
	protected String getDriverName() {
		return "com.mysql.jdbc.Driver";
	}

	@Override
	protected String getConnection(SqlDataSource dataSource) {
		StringBuffer buffer = new StringBuffer("jdbc:mysql://");
		buffer.append(dataSource.getIp()).append(":").append(dataSource.getPort()).append("/").append(dataSource.getDataBase());
		buffer.append("?zeroDateTimeBehavior=convertToNull&characterEncoding=").append(dataSource.getCharset()).append("&amp;allowMultiQueries=true");
		return buffer.toString();
	}

	@Override
	protected String existed(SqlDataSource dataSource, String tableName) {
		StringBuffer buffer = new StringBuffer("select * from information_schema.TABLES t where t.TABLE_SCHEMA ='");
		buffer.append(dataSource.getDataBase()).append("' and t.TABLE_NAME ='").append(tableName).append("'");
		return buffer.toString();
	}
}
