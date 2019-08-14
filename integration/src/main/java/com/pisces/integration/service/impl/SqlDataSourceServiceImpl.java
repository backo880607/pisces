package com.pisces.integration.service.impl;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
		conn = DriverManager.getConnection(getConnection(ds), ds.getUsername(), ds.getPassword());
		stmt = conn.createStatement(); 
		resultSet = stmt.executeQuery(existed(ds, tableName));
		return resultSet.next();
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
			if (this.resultSet != null) {
				this.resultSet.close();
				this.resultSet = null;
			}
			if (this.stmt != null) {
				this.stmt.close();
				this.stmt = null;
			}
			if (this.conn != null) {
				this.conn.close();
				this.conn = null;
			}
		} catch (SQLException e) {
		}
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
	public Collection<FieldInfo> getFields() throws Exception {
		Collection<FieldInfo> result = new ArrayList<FieldInfo>();
		ResultSetMetaData metaData = resultSet.getMetaData();
		for (int i = 1; i <= metaData.getColumnCount(); ++i) {
			FieldInfo field = new FieldInfo();
			field.setName(metaData.getColumnName(i));
			field.setExternName(field.getName());
		}
		return result;
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
