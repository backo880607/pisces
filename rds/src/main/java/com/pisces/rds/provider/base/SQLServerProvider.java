package com.pisces.rds.provider.base;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.ibatis.type.JdbcType;

import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.entity.EntityTable;
import tk.mybatis.mapper.mapperhelper.EntityHelper;

public class SQLServerProvider extends SQLProvider {
	
	@Override
	public String getSQLType(EntityColumn column) throws SQLException {
		JdbcType type = getJdbcType(column);
		switch (type) {
		case ARRAY:
		case BIT:
			return "bit";
		case TINYINT:
			return "tinyint";
		case SMALLINT:
			return "smallint";
		case INTEGER:
			return "int";
		case BIGINT:
			return "bigint";
		case FLOAT:
			return "float";
		case REAL:
			return "real";
		case DOUBLE:
			return "float (2005,2008)";
		case NUMERIC:
			return "numeric(18, 0)";
		case DECIMAL:
			return "decimal(18, 0)";
		case CHAR:
			return "char";
		case VARCHAR:
			return "varchar(255)";
		case LONGVARCHAR:
			return "text";
		case DATE:
			return "date";
		case TIME:
			return "time(7)";
		case TIMESTAMP:
			return "timestamp";
		case BINARY:
			return "binary(50)";
		case VARBINARY:
			return "varbinary(50)";
		case LONGVARBINARY:
			return "varbinary(MAX)";
		case NULL:
		case OTHER:
		case BLOB:
		case CLOB:
			return "text";
		case BOOLEAN:
			return "bit";
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
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM sysobjects WHERE id = object_id('");
		sql.append(tableName).append("')");
		return sql.toString();
	}
	
	@Override
	public boolean existedTable(ResultSet resultSet) throws SQLException {
		return !resultSet.getString(1).isEmpty();
	}

	@Override
	public String createTable(String tableName, Class<?> entityClass) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("CREATE TABLE ").append(tableName).append(" (");
		EntityTable table = EntityHelper.getEntityTable(entityClass);
		for (EntityColumn column : table.getEntityClassColumns()) {
			sql.append(column.getColumn()).append(" ").append(getSQLType(column)).append(",");
		}
		sql.append("PRIMARY KEY (id))");
		return sql.toString();
	}

	@Override
	public String addColumns(String tableName, List<EntityColumn> columns) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("ALTER TABLE ").append(tableName).append(" ADD ");
		sql.append("(");
		boolean bFind = false;
		for (EntityColumn column : columns) {
			if (bFind) {
				sql.append(",");
			}
			sql.append(column.getColumn()).append(" ").append(getSQLType(column));
			bFind = true;
		}
		sql.append(")");
		return sql.toString();
	}

	@Override
	public String changeColumns(String tableName, List<EntityColumn> columns) throws SQLException {
		StringBuilder sql = new StringBuilder();
		for (EntityColumn column : columns) {
			sql.append("ALTER TABLE ").append(tableName).append(" MODIFY ");
			sql.append(column.getColumn()).append(" ").append(getSQLType(column));
			sql.append(";");
		}
		return sql.toString();
	}

	@Override
	public String dropColumns(String tableName, Map<String, EntityColumn> columns) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("ALTER TABLE ").append(tableName).append(" ");
		boolean bFind = false;
		for (Entry<String, EntityColumn> entry : columns.entrySet()) {
			if (bFind) {
				sql.append(",");
			}
			sql.append("DROP ").append(entry.getKey());
			bFind = true;
		}
		return sql.toString();
	}

}
