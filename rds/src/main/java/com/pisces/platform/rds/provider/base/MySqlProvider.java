package com.pisces.platform.rds.provider.base;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.ibatis.type.JdbcType;

import tk.mybatis.mapper.entity.EntityColumn;

public class MySqlProvider extends SQLProvider {

    @Override
    public String getSQLType(JdbcType jdbcType) {
        switch (jdbcType) {
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
        throw new UnsupportedOperationException("not support jdbc type: " + jdbcType.name());
    }

    @Override
    public String getDriverName() {
        return "com.mysql.jdbc.Driver";
    }

    @Override
    public String getConnection(String host, int port, String dataBase, String charset) {
        StringBuilder sql = new StringBuilder("jdbc:mysql://");
        sql.append(host).append(":").append(port).append("/").append(dataBase);
        sql.append("?autoReconnect=true&useSSL=false&characterEncoding=utf-8");
        return sql.toString();
    }

    @Override
    public boolean existedDataBase(Connection conn, String dataBase) throws SQLException {
        return true;
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
        sql.append("SELECT COUNT(*) ");
        sql.append("FROM information_schema.TABLES ");
        sql.append("WHERE TABLE_SCHEMA='").append(dataBase).append("' ");
        sql.append(" AND binary TABLE_NAME='").append(tableName).append("'");
        try (Statement stmt = conn.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(sql.toString());
            if (resultSet.next() && resultSet.getInt(1) > 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void createTable(Connection conn, String tableName, Collection<EntityColumn> columns) throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append("CREATE TABLE ").append(tableName).append(" (");
        for (EntityColumn column : columns) {
            sql.append(column.getColumn()).append(" ").append(getSQLType(column.getJdbcType())).append(",");
        }
        sql.append("PRIMARY KEY (id)) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_general_ci;");
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql.toString());
        }
    }

    @Override
    public void dropTable(Connection conn, String tableName) throws SQLException {
        StringBuilder sql = new StringBuilder("DROP TABLE ");
        sql.append(tableName);
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql.toString());
        }
    }

    @Override
    public String addColumns(String tableName, Collection<EntityColumn> columns) {
        StringBuilder sql = new StringBuilder();
        sql.append("ALTER TABLE ").append(tableName).append(" ADD ");
        sql.append("(");
        boolean bFind = false;
        for (EntityColumn column : columns) {
            if (bFind) {
                sql.append(",");
            }
            sql.append(column.getColumn()).append(" ").append(getSQLType(column.getJdbcType()));
            bFind = true;
        }
        sql.append(")");
        return sql.toString();
    }

    @Override
    public String changeColumns(String tableName, Collection<EntityColumn> columns) {
        StringBuilder sql = new StringBuilder();
        for (EntityColumn column : columns) {
            sql.append("ALTER TABLE ").append(tableName).append(" MODIFY ");
            sql.append(column.getColumn()).append(" ").append(getSQLType(column.getJdbcType()));
            sql.append(";");
        }
        return sql.toString();
    }

    @Override
    public String dropColumns(String tableName, Map<String, EntityColumn> columns) {
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
