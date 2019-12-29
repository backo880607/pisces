package com.pisces.platform.integration.service.impl.sql;

import com.pisces.platform.core.entity.EntityObject;
import com.pisces.platform.core.entity.Property;
import com.pisces.platform.core.service.PropertyService;
import com.pisces.platform.core.utils.Primary;
import com.pisces.platform.integration.bean.DataSource;
import com.pisces.platform.integration.bean.DsSql;
import com.pisces.platform.integration.bean.FieldInfo;
import com.pisces.platform.integration.bean.Scheme;
import com.pisces.platform.integration.helper.DataConfig;
import com.pisces.platform.integration.helper.DataSourceAdapter;
import com.pisces.platform.rds.provider.base.SQLProvider;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.EntityColumn;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

abstract class SqlDataSourceService<T extends Enum<T>> implements DataSourceAdapter {
    private SQLProvider provider;
    private Connection conn;
    private Statement stmt;
    private PreparedStatement pstmt;
    private ResultSet resultSet;
    private DsSql dataSource;

    @Autowired
    private PropertyService propertyService;

    public SqlDataSourceService(SQLProvider provider) {
        this.provider = provider;
    }

    @Override
    public void modifyConfig(DataConfig config) {
    }

    @Override
    public boolean validConnection(DataSource dataSource, String tableName, boolean export) throws Exception {
        if (!(dataSource instanceof DsSql)) {
            return false;
        }
        Class.forName(this.provider.getDriverName());
        this.dataSource = (DsSql) dataSource;
        this.conn = DriverManager.getConnection(this.provider.getConnection(this.dataSource.getHost(), this.dataSource.getPort(), this.dataSource.getDataBase(), this.dataSource.getCharset()),
                this.dataSource.getUsername(), this.dataSource.getPassword());
        return export ? true : this.provider.existedTable(this.conn, this.dataSource.getDataBase(), tableName);
    }

    @Override
    public boolean open(DataSource dataSource, String tableName, boolean export) throws Exception {
        if (!(dataSource instanceof DsSql)) {
            return false;
        }
        this.dataSource = (DsSql) dataSource;
        Class.forName(this.provider.getDriverName());
        this.conn = DriverManager.getConnection(this.provider.getConnection(this.dataSource.getHost(), this.dataSource.getPort(), this.dataSource.getDataBase(), this.dataSource.getCharset()),
                this.dataSource.getUsername(), this.dataSource.getPassword());
        return !this.conn.isClosed();
    }

    @Override
    public void close() {
        try {
            if (this.resultSet != null) {
                this.resultSet.close();
                this.resultSet = null;
            }
            if (this.stmt != null) {
                this.stmt.close();
                this.stmt = null;
            }
            if (this.pstmt != null) {
                this.pstmt.close();
                this.pstmt = null;
            }
            if (this.conn != null) {
                this.conn.close();
                this.conn = null;
            }
        } catch (SQLException e) {
        }
    }

    @Override
    public boolean executeQuery(DataSource dataSource, String tableName, Collection<FieldInfo> fields) throws Exception {
        if (this.conn == null || fields.isEmpty()) {
            return false;
        }
        StringBuilder builder = new StringBuilder("SELECT ");
        for (FieldInfo field : fields) {
            builder.append(field.getExternName()).append(",");
        }
        builder.deleteCharAt(builder.length() - 1);
        builder.append(" FROM ").append(tableName);
        this.stmt = this.conn.createStatement();
        this.resultSet = this.stmt.executeQuery(builder.toString());
        return !this.resultSet.isClosed();
    }

    @Override
    public Collection<FieldInfo> getFields() throws Exception {
        Collection<FieldInfo> result = new ArrayList<FieldInfo>();
        ResultSetMetaData metaData = this.resultSet.getMetaData();
        for (int i = 1; i <= metaData.getColumnCount(); ++i) {
            FieldInfo field = new FieldInfo();
            field.setName(metaData.getColumnName(i));
            field.setExternName(field.getName());
        }
        return result;
    }

    @Override
    public boolean step() throws Exception {
        return this.resultSet.next();
    }

    @Override
    public String getData(int index) throws Exception {
        return this.resultSet.getString(index);
    }

    @Override
    public boolean beforeWriteTable(Scheme scheme, Collection<FieldInfo> fields) throws Exception {
        if (this.provider.existedTable(this.conn, this.conn.getCatalog(), scheme.getOutName())) {
            this.provider.dropTable(this.conn, scheme.getOutName());
        }
        Class<? extends EntityObject> entityClass = Primary.get().getEntityClass(scheme.getInName());
        List<EntityColumn> columns = new ArrayList<EntityColumn>();
        for (FieldInfo field : fields) {
            EntityColumn column = new EntityColumn();
            column.setColumn(field.getExternName());

            Property property = this.propertyService.get(entityClass, field.getName());
            column.setJavaType(property.clazz);
            column.setJdbcType(SQLProvider.getJdbcType(property.getType(), property.getLarge()));
            columns.add(column);
        }
        this.provider.createTable(conn, scheme.getOutName(), columns);

        StringBuilder builder = new StringBuilder("INSERT INTO ");
        builder.append(scheme.getOutName()).append(" VALUES(");
        for (int i = 0; i < fields.size(); ++i) {
            builder.append("?,");
        }
        builder.deleteCharAt(builder.length() - 1);
        builder.append(")");
        this.pstmt = this.conn.prepareStatement(builder.toString());
        this.conn.setAutoCommit(false);

        return true;
    }

    @Override
    public boolean beforeWriteEntity(EntityObject entity) throws Exception {
        return true;
    }

    @Override
    public void write(int index, String data) throws Exception {
        this.pstmt.setString(index + 1, data);
    }

    @Override
    public void afterWriteEntity(EntityObject entity) throws Exception {
        this.pstmt.executeUpdate();
    }

    @Override
    public void afterWriteTable(Scheme scheme) throws Exception {
        this.conn.commit();
        this.conn.setAutoCommit(true);
    }
}
