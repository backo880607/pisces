package com.pisces.rds.provider.base;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.type.JdbcType;

import com.pisces.core.entity.MultiEnum;

import tk.mybatis.mapper.entity.EntityColumn;

public abstract class SQLProvider {
	private String database;
	private DatabaseMetaData dbMetaData;
	
	public String getDatabase() {
		return database;
	}
	
	public void setDatabase(String database) {
		this.database = database;
	}

	public DatabaseMetaData getDbMetaData() {
		return dbMetaData;
	}

	public void setDbMetaData(DatabaseMetaData dbMetaData) {
		this.dbMetaData = dbMetaData;
	}
	
	public abstract String getSQLType(EntityColumn column) throws SQLException;
	protected JdbcType getJdbcType(EntityColumn column) {
		if (column.getJdbcType() != null) {
			return column.getJdbcType();
		}
		if (column.getJavaType() == Boolean.class) {
			return JdbcType.BOOLEAN;
		} else if (column.getJavaType() == Short.class) {
			return JdbcType.SMALLINT;
		} else if (column.getJavaType() == Integer.class) {
			return JdbcType.INTEGER;
		} else if (column.getJavaType() == Long.class) {
			return JdbcType.BIGINT;
		} else if (column.getJavaType() == Float.class) {
			return JdbcType.FLOAT;
		} else if (column.getJavaType() == Double.class) {
			return JdbcType.DOUBLE;
		} else if (column.getJavaType() == Character.class) {
			return JdbcType.CHAR;
		} else if (column.getJavaType() == String.class) {
			return JdbcType.VARCHAR;
		} else if (column.getJavaType() == Date.class) {
			return JdbcType.DATE;
		} else if (column.getJavaType().isEnum()) {
			return JdbcType.VARCHAR;
		} else if (MultiEnum.class.isAssignableFrom(column.getJavaType())) {
			return JdbcType.VARCHAR;
		}
		
		return JdbcType.OTHER;
	}
	
	ResultSet getColumns(String tableName) throws SQLException {
		return this.dbMetaData.getColumns(this.getDatabase(), null, tableName, "mediaType");
	}

	public abstract String existedTable(String tableName);
	public abstract boolean existedTable(ResultSet resultSet) throws SQLException;
	public abstract String createTable(String tableName, Class<?> entityClass) throws SQLException;
	public abstract String addColumns(String tableName, List<EntityColumn> columns) throws SQLException;
	public abstract String changeColumns(String tableName, List<EntityColumn> columns) throws SQLException;
	public abstract String dropColumns(String tableName, Map<String, EntityColumn> columns) throws SQLException;
}
