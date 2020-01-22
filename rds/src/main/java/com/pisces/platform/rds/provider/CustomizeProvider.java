package com.pisces.platform.rds.provider;

import com.pisces.platform.rds.config.EntityWrapperFactory;
import com.pisces.platform.rds.config.MybatisEntityFactory;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.type.JdbcType;
import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.entity.EntityTable;
import tk.mybatis.mapper.mapperhelper.EntityHelper;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.SqlHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class CustomizeProvider extends BaseProvider {

    public CustomizeProvider(Class<?> mapperClazz, MapperHelper mapperHelper) {
        super(mapperClazz, mapperHelper);
    }

    private static boolean notChecked = true;

    public void checkTable(MappedStatement ms) throws SQLException {
        if (notChecked) {
            notChecked = false;
            ms.getConfiguration().setObjectWrapperFactory(new EntityWrapperFactory());
            ms.getConfiguration().setObjectFactory(new MybatisEntityFactory());
            Transmit.instance.start();
            return;
        }
        Class<?> entityClazz = getEntityClass(ms);
        try (Connection conn = ms.getConfiguration().getEnvironment().getDataSource().getConnection()) {
            if (!doExistedTable(conn, ms)) {
                doCreateTable(conn, ms);
                return;
            }

            Map<String, EntityColumn> existedColumns = getExistedColumns(conn, entityClazz);
            List<EntityColumn> addColumns = new ArrayList<>();
            List<EntityColumn> changeColumns = new ArrayList<>();
            for (EntityColumn column : EntityHelper.getColumns(entityClazz)) {
                EntityColumn existedColumn = existedColumns.get(column.getColumn());
                if (existedColumn != null) {
                    if (!(getProvider(ms).compatible(column.getJdbcType(), existedColumn.getJdbcType()))) {
                        changeColumns.add(column);
                    }
                    existedColumns.remove(column.getColumn());
                } else {
                    addColumns.add(column);
                }
            }

            if (!addColumns.isEmpty()) {
                autoAddColumns(conn, ms, addColumns);
            }

            if (!changeColumns.isEmpty()) {
                autoChangeColumns(conn, ms, changeColumns);
            }

            if (!existedColumns.isEmpty()) {
                autoDropColumns(conn, ms, existedColumns);
            }
        }
    }

    private boolean doExistedTable(Connection conn, MappedStatement ms) throws SQLException {
        Class<?> entityClazz = getEntityClass(ms);
        return getProvider(ms).existedTable(conn, conn.getCatalog(), tableName(entityClazz));
    }

    private void doCreateTable(Connection conn, MappedStatement ms) throws SQLException {
        Class<?> entityClazz = getEntityClass(ms);
        EntityTable table = EntityHelper.getEntityTable(entityClazz);
        this.getProvider(ms).createTable(conn, tableName(entityClazz), table.getEntityClassColumns());
    }

    private Map<String, EntityColumn> getExistedColumns(Connection conn, Class<?> entityClazz) throws SQLException {
        Map<String, EntityColumn> existedColumns = new HashMap<>();
        try (ResultSet resultSet = conn.getMetaData().getColumns(conn.getCatalog(), null, tableName(entityClazz), null)) {
            while (resultSet.next()) {
                EntityColumn column = new EntityColumn();
                column.setColumn(resultSet.getString(4));
                column.setJdbcType(JdbcType.forCode(resultSet.getInt("DATA_TYPE")));
                existedColumns.put(column.getColumn(), column);
            }
        }
        return existedColumns;
    }

    private void autoAddColumns(Connection conn, MappedStatement ms, List<EntityColumn> columns) throws SQLException {
        Class<?> entityClazz = getEntityClass(ms);
        final String addSQL = this.getProvider(ms).addColumns(tableName(entityClazz), columns);
        try (PreparedStatement stmt = conn.prepareStatement(addSQL)) {
            stmt.execute();
        }
    }

    private void autoChangeColumns(Connection conn, MappedStatement ms, List<EntityColumn> columns) throws SQLException {
        Class<?> entityClazz = getEntityClass(ms);
        final String changeSQL = this.getProvider(ms).changeColumns(tableName(entityClazz), columns);
        try (PreparedStatement stmt = conn.prepareStatement(changeSQL)) {
            stmt.execute();
        }
    }

    private void autoDropColumns(Connection conn, MappedStatement ms, Map<String, EntityColumn> columns) throws SQLException {
        Class<?> entityClazz = getEntityClass(ms);
        final String changeSQL = this.getProvider(ms).dropColumns(tableName(entityClazz), columns);
        try (PreparedStatement stmt = conn.prepareStatement(changeSQL)) {
            stmt.execute();
        }
    }

    public String selectByIds(MappedStatement ms) {
        final Class<?> entityClass = getEntityClass(ms);
        setResultType(ms, entityClass);
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.selectAllColumns(entityClass));
        sql.append(SqlHelper.fromTable(entityClass, tableName(entityClass)));
        sql.append(" WHERE id IN ");
        sql.append("<foreach item=\"item\" index=\"index\" collection=\"list\" open=\"(\" separator=\",\" close=\")\">");
        sql.append("#{item}");
        sql.append("</foreach>");
        return sql.toString();
    }

//    public String insertList(MappedStatement ms) {
//        final Class<?> entityClazz = getEntityClass(ms);
//        StringBuilder sql = new StringBuilder();
//        sql.append(SqlHelper.insertIntoTable(entityClazz, tableName(entityClazz)));
//        sql.append(SqlHelper.insertColumns(entityClazz, false, false, false));
//        sql.append(" VALUES ");
//        sql.append("<foreach collection=\"list\" item=\"record\" separator=\",\" >");
//        sql.append("<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">");
//        //获取全部列
//        Set<EntityColumn> columnList = EntityHelper.getColumns(entityClazz);
//        //当某个列有主键策略时，不需要考虑他的属性是否为空，因为如果为空，一定会根据主键策略给他生成一个值
//        for (EntityColumn column : columnList) {
//            if (column.isInsertable()) {
//                sql.append(column.getColumnHolder("record") + ",");
//            }
//        }
//        sql.append("</trim>");
//        sql.append("</foreach>");
//        return sql.toString();
//    }

    public String update(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.updateTable(entityClass, tableName(entityClass)));
        sql.append(SqlHelper.updateSetColumns(entityClass, null, false, false));
        sql.append(SqlHelper.wherePKColumns(entityClass, true));
        return sql.toString();
    }

    public String updateList(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        sql.append("<foreach item=\"item\" index=\"index\" collection=\"list\" open=\"(\" separator=\",\" close=\")\">");
        sql.append(SqlHelper.updateTable(entityClass, tableName(entityClass)));
        sql.append(SqlHelper.updateSetColumns(entityClass, null, true, isNotEmpty()));
        sql.append(SqlHelper.wherePKColumns(entityClass, true));
        sql.append("</foreach>");
        return sql.toString();
    }

    public String deleteList(MappedStatement ms) {
        final Class<?> entityClazz = getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.deleteFromTable(entityClazz, tableName(entityClazz)));
        sql.append(" WHERE id IN ");
        sql.append("<foreach item=\"item\" index=\"index\" collection=\"list\" open=\"(\" separator=\",\" close=\")\">");
        sql.append("#{item.id}");
        sql.append("</foreach>");
        return sql.toString();
    }

    public String deleteByPrimaryKeys(MappedStatement ms) {
        final Class<?> entityClazz = getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.deleteFromTable(entityClazz, tableName(entityClazz)));
        sql.append(" WHERE id IN ");
        sql.append("<foreach item=\"item\" index=\"index\" collection=\"list\" open=\"(\" separator=\",\" close=\")\">");
        sql.append("#{item}");
        sql.append("</foreach>");
        return sql.toString();
    }
}
