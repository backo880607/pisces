package com.pisces.rds.provider.base;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Map;

import org.apache.ibatis.type.JdbcType;

import tk.mybatis.mapper.entity.EntityColumn;

public class SQLiteProvider extends SQLProvider {
	
	@Override
	public String getSQLType(JdbcType jdbcType) {
		if (jdbcType == null) {
			return "";
		}
		
		switch (jdbcType) {
		case BIT:
		case CHAR:
		case INTEGER:
		case BIGINT:
			return "INTEGER";
		case DOUBLE:
			return "REAL";
		case VARCHAR:
		case LONGVARCHAR:
		case DATE:
		case TIME:
		case TIMESTAMP:
			return "TEXT";
		default:
			break;
		}
		return "";
	}
	
	@Override
	public boolean compatible(JdbcType lhs, JdbcType rhs) {
		switch (lhs) {
		case BIT:
		case CHAR:
		case INTEGER:
		case BIGINT:
			return rhs == JdbcType.INTEGER;
		case DOUBLE:
			return rhs == JdbcType.REAL;
		case VARCHAR:
		case LONGVARCHAR:
		case DATE:
		case TIME:
		case TIMESTAMP:
			return rhs == JdbcType.VARCHAR;
		default:
			break;
		}
		
		return false;
	}

	@Override
	public String getDriverName() {
		return "org.sqlite.JDBC";
	}

	@Override
	public String getConnection(String host, int port, String dataBase, String charset) {
		StringBuffer sql = new StringBuffer("jdbc:sqlite:");
		sql.append(host).append(File.separator).append(dataBase).append(".db");
		return sql.toString();
	}

	@Override
	public boolean existedDataBase(Connection conn, String dataBase) throws SQLException {
		return false;
	}

	@Override
	public void createDataBase(Connection conn, String dataBase) throws SQLException {
		
	}

	@Override
	public void dropDataBase(Connection conn, String dataBase) throws SQLException {
		
	}

	@Override
	public boolean existedTable(Connection conn, String dataBase, String tableName) throws SQLException {
		StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM sqlite_master WHERE type='table' AND name='").append(tableName).append("'");
        try (Statement stmt = conn.createStatement()) {
			ResultSet resultSet = stmt.executeQuery(sql.toString());
			if (resultSet != null && resultSet.next()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void createTable(Connection conn, String tableName, Collection<EntityColumn> columns) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("CREATE TABLE ").append(tableName).append(" (");
		boolean bFind = false;
		for (EntityColumn column : columns) {
			if (bFind) {
				sql.append(",");
			}
			sql.append(column.getColumn()).append(" ").append(getSQLType(column.getJdbcType()));
			if (column.getProperty().equals("id")) {
				sql.append(" PRIMARY KEY");
			}
			bFind = true;
		}
		sql.append(")");
		try (Statement stmt = conn.createStatement()) {
			stmt.execute(sql.toString());
		}
	}

	@Override
	public void dropTable(Connection conn, String tableName) throws SQLException {
		
	}

	@Override
	public String addColumns(String tableName, Collection<EntityColumn> columns) {
		return null;
	}

	@Override
	public String changeColumns(String tableName, Collection<EntityColumn> columns) {
		return null;
	}

	@Override
	public String dropColumns(String tableName, Map<String, EntityColumn> columns) {
		return null;
	}

}
