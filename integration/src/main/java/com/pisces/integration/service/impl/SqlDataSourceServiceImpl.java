package com.pisces.integration.service.impl;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;

import com.pisces.core.dao.BaseDao;
import com.pisces.integration.bean.DataSource;
import com.pisces.integration.bean.FieldInfo;
import com.pisces.integration.bean.SqlDataSource;
import com.pisces.integration.service.SqlDataSourceService;

abstract class SqlDataSourceServiceImpl<T extends SqlDataSource, D extends BaseDao<T>> extends DataSourceServiceImpl<T, D> implements SqlDataSourceService<T> {
	private Connection conn;
	private Statement stmt;
	private ResultSet resultSet;
	
	protected abstract String getDriverName();
	protected abstract String getConnection(SqlDataSource dataSource);
	protected abstract String existed(SqlDataSource dataSource, String tableName);
	
	@Override
	public boolean validConnection(DataSource dataSource, String tableName) throws Exception {
		if (!(dataSource instanceof SqlDataSource)) {
			return false;
		}
		SqlDataSource ds = (SqlDataSource)dataSource;
		Class.forName(getDriverName());
		try (Connection tempConn = DriverManager.getConnection(getConnection(ds), ds.getUsername(), ds.getPassword());
				Statement tempStmt = tempConn.createStatement(); 
				ResultSet rs = tempStmt.executeQuery(existed(ds, tableName))) {
			return rs.next();
		}
	}

	@Override
	public boolean open(DataSource dataSource, String tableName) throws Exception {
		if (!(dataSource instanceof SqlDataSource)) {
			return false;
		}
		SqlDataSource ds = (SqlDataSource)dataSource;
		Class.forName(getDriverName());
		this.conn = DriverManager.getConnection(getConnection(ds), ds.getUsername(), ds.getPassword());
		return !this.conn.isClosed();
	}

	@Override
	public void close() {
		try {
			this.resultSet.close();
			this.stmt.close();
			this.conn.close();
		} catch (SQLException e) {
		}
	}
	
	@Override
	public boolean checkTableStructure(DataSource dataSource, String tableName, Collection<FieldInfo> fields) throws Exception {
		return true;
	}
	
	@Override
	public boolean executeQuery(DataSource dataSource, String tableName, Collection<FieldInfo> fields) throws Exception {
		if (this.conn == null || fields.isEmpty()) {
			return false;
		}
		StringBuilder builder = new StringBuilder("SELECT ");
		for (FieldInfo field : fields) {
			builder.append(field.getExternName()).append(",");
		}
		builder.deleteCharAt(builder.length() - 1);
		builder.append(" FROM ").append(tableName);
		this.stmt = this.conn.createStatement();
		this.resultSet = this.stmt.executeQuery(builder.toString());
		return !this.resultSet.isClosed();
	}

	@Override
	public boolean step() throws Exception {
		return this.resultSet.next();
	}

	@Override
	public String getData(int index) throws Exception {
		return this.resultSet.getString(index);
	}

	@Override
	public String getData(Field field) throws Exception {
		return this.resultSet.getString(field.getName());
	}

	@Override
	public void write(Field field, String data) throws Exception {		
	}
}
