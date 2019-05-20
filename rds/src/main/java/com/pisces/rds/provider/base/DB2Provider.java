package com.pisces.rds.provider.base;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.type.JdbcType;

import tk.mybatis.mapper.entity.EntityColumn;

public class DB2Provider extends SQLProvider {
	
	@Override
	public String getSQLType(EntityColumn column) throws SQLException {
		JdbcType type = getJdbcType(column);
		switch (type) {
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
	public String existedTable(String tableName) {
		return null;
	}

	@Override
	public String createTable(String tableName, Class<?> entityClass) throws SQLException {
		return null;
	}

	@Override
	public String addColumns(String tableName, List<EntityColumn> columns) throws SQLException {
		return null;
	}

	@Override
	public String changeColumns(String tableName, List<EntityColumn> columns) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String dropColumns(String tableName, Map<String, EntityColumn> columns) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
