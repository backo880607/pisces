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

public class MySQLProvider extends SQLProvider {
	
	@Override
	public String getSQLType(EntityColumn column) throws SQLException {
		JdbcType type = getJdbcType(column);
		switch (type) {
		case ARRAY:
			break;
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
			return "float";
		case DOUBLE:
			return "double";
		case NUMERIC:
			return "decimal";
		case DECIMAL:
			return "decimal";
		case CHAR:
			return "char";
		case VARCHAR:
			return "varchar(255)";
		case LONGVARCHAR:
			return "longtext";
		case DATE:
			return "date";
		case TIME:
			return "time";
		case TIMESTAMP:
			return "timestamp";
		case BINARY:
			return "tinyblob";
		case VARBINARY:
			return "varbinary";
		case LONGVARBINARY:
		case NULL:
		case OTHER:
			break;
		case BLOB:
			return "blob";
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
		throw new SQLException("Unimplemented jdbc type: " + type.name());
	}
	
	@Override
	public String existedTable(String tableName) {
		StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(*) ");
        sql.append("FROM information_schema.TABLES ");
        sql.append("WHERE TABLE_SCHEMA='").append(getDatabase()).append("' ");
        sql.append(" AND binary TABLE_NAME='").append(tableName).append("'");
        return sql.toString();
	}
	
	@Override
	public boolean existedTable(ResultSet resultSet) throws SQLException {
		return resultSet.getInt(1) > 0;
	}
	
	@Override
	public String createTable(String tableName, Class<?> entityClass) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("CREATE TABLE ").append(tableName).append(" (");
		EntityTable table = EntityHelper.getEntityTable(entityClass);
		for (EntityColumn column : table.getEntityClassColumns()) {
			sql.append(column.getColumn()).append(" ").append(getSQLType(column)).append(",");
		}
		sql.append("PRIMARY KEY (id)) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_general_ci;");
		return sql.toString();
	}
	
	/*@Override
	public String existedColumn(MappedStatement ms) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT COUNT(*) ");
		sql.append("FROM information_schema.COLUMNS ");
		sql.append("WHERE TABLE_SCHEMA='test' ");
		sql.append(" AND TABLE_NAME=").append("#{tableName}");
		sql.append(" AND binary COLUMN_NAME=").append("#{columnName}");
		return sql.toString();
	}*/
	
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
