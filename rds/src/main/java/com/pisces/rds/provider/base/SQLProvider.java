package com.pisces.rds.provider.base;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import org.apache.ibatis.type.JdbcType;

import com.pisces.core.entity.MultiEnum;
import com.pisces.core.enums.PROPERTY_TYPE;

import tk.mybatis.mapper.entity.EntityColumn;

public abstract class SQLProvider {
	
	public abstract String getSQLType(JdbcType jdbcType);
	
	public JdbcType getJdbcType(Class<?> javaType, PROPERTY_TYPE type, boolean large) {
		if (javaType == Boolean.class) {
			return JdbcType.BOOLEAN;
		} else if (javaType == Short.class) {
			return JdbcType.SMALLINT;
		} else if (javaType == Integer.class) {
			return JdbcType.INTEGER;
		} else if (javaType == Long.class) {
			return JdbcType.BIGINT;
		} else if (javaType == Float.class) {
			return JdbcType.FLOAT;
		} else if (javaType == Double.class) {
			return JdbcType.DOUBLE;
		} else if (javaType == Character.class) {
			return JdbcType.CHAR;
		} else if (javaType == String.class) {
			return large ? JdbcType.LONGNVARCHAR : JdbcType.NVARCHAR;
		} else if (javaType == Date.class) {
			return JdbcType.DATE;
		} else if (javaType.isEnum()) {
			return JdbcType.VARCHAR;
		} else if (MultiEnum.class.isAssignableFrom(javaType)) {
			return JdbcType.VARCHAR;
		}
		
		return JdbcType.OTHER;
	}

	public abstract String getDriverName();
	public abstract String getConnection(String host, int port, String dataBase, String charset);
	
	public abstract boolean existedDataBase(Connection conn, String dataBase) throws SQLException;
	public abstract void createDataBase(Connection conn, String dataBase) throws SQLException;
	public abstract void dropDataBase(Connection conn, String dataBase) throws SQLException;
	
	public abstract boolean existedTable(Connection conn, String dataBase, String tableName) throws SQLException;
	public abstract void createTable(Connection conn, String tableName, Collection<EntityColumn> columns) throws SQLException;
	public abstract void dropTable(Connection conn, String tableName) throws SQLException;
	
	public abstract String addColumns(String tableName, Collection<EntityColumn> columns);
	public abstract String changeColumns(String tableName, Collection<EntityColumn> columns);
	public abstract String dropColumns(String tableName, Map<String, EntityColumn> columns);
}
