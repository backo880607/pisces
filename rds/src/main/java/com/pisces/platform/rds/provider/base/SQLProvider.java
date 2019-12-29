package com.pisces.platform.rds.provider.base;

import com.pisces.platform.core.enums.PROPERTY_TYPE;
import org.apache.ibatis.type.JdbcType;
import tk.mybatis.mapper.entity.EntityColumn;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;

public abstract class SQLProvider {

    public abstract String getSQLType(JdbcType jdbcType);

    // 由数据库的类型获取内部支持的Jdbc数据类型。
    public boolean compatible(JdbcType lhs, JdbcType rhs) {
        return lhs == rhs;
    }

    public static JdbcType getJdbcType(PROPERTY_TYPE type, boolean large) {
        JdbcType jdbcType = JdbcType.OTHER;
        switch (type) {
            case BOOLEAN:
                jdbcType = JdbcType.BIT;
                break;
            case SHORT:
                jdbcType = JdbcType.SMALLINT;
                break;
            case INTEGER:
                jdbcType = JdbcType.INTEGER;
                break;
            case LONG:
                jdbcType = JdbcType.BIGINT;
                break;
            case DOUBLE:
                jdbcType = JdbcType.DOUBLE;
                break;
            case DATE:
                jdbcType = JdbcType.DATE;
                break;
            case TIME:
                jdbcType = JdbcType.TIME;
                break;
            case DATE_TIME:
                jdbcType = JdbcType.TIMESTAMP;
                break;
            case DURATION:
                jdbcType = JdbcType.VARCHAR;
                break;
            case ENUM:
                jdbcType = JdbcType.VARCHAR;
                break;
            case MULTI_ENUM:
                jdbcType = JdbcType.VARCHAR;
                break;
            case STRING:
                jdbcType = large ? JdbcType.LONGVARCHAR : JdbcType.VARCHAR;
                break;
            case ENTITY:
                jdbcType = JdbcType.LONGVARCHAR;
                break;
            case LIST:
                jdbcType = JdbcType.LONGVARCHAR;
                break;
            default:
                break;
        }
        if (jdbcType == JdbcType.OTHER) {
            throw new UnsupportedOperationException("not support property type: " + type.name());
        }
        return jdbcType;
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
