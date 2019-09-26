package com.pisces.rds.provider.base;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;

import org.apache.ibatis.type.JdbcType;

import tk.mybatis.mapper.entity.EntityColumn;

public class DB2Provider extends SQLProvider {
	
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getConnection(String host, int port, String dataBase, String charset) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean existedDataBase(Connection conn, String dataBase) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void createDataBase(Connection conn, String dataBase) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dropDataBase(Connection conn, String dataBase) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean existedTable(Connection conn, String dataBase, String tableName) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void createTable(Connection conn, String tableName, Collection<EntityColumn> columns) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dropTable(Connection conn, String tableName) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String addColumns(String tableName, Collection<EntityColumn> columns) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String changeColumns(String tableName, Collection<EntityColumn> columns) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String dropColumns(String tableName, Map<String, EntityColumn> columns) {
		// TODO Auto-generated method stub
		return null;
	}
}
