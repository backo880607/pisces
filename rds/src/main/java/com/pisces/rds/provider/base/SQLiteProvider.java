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
		case ARRAY:
		case BIT:
		case TINYINT:
		case SMALLINT:
		case INTEGER:
			return "int";
		case BIGINT:
			return "";
		case FLOAT:
			return "";
		case REAL:
			return "";
		case DOUBLE:
			return "";
		case NUMERIC:
			return "";
		case DECIMAL:
			return "";
		case CHAR:
			return "";
		case VARCHAR:
			return "";
		case LONGVARCHAR:
		case DATE:
		case TIME:
		case TIMESTAMP:
		case BINARY:
		case VARBINARY:
		case LONGVARBINARY:
		case NULL:
		case OTHER:
		case BLOB:
		case CLOB:
		case BOOLEAN:
		case CURSOR:
		case UNDEFINED:
		case NVARCHAR:
		case NCHAR:
		case NCLOB:
		case STRUCT:
		case JAVA_OBJECT:
		case DISTINCT:
		case REF:
		case DATALINK:
		case ROWID:
		case LONGNVARCHAR:
		case SQLXML:
		case DATETIMEOFFSET:
		default:
			break;
		}
		return "";
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
