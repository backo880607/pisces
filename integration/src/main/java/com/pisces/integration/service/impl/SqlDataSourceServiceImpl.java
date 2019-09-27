package com.pisces.integration.service.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.pisces.core.dao.BaseDao;
import com.pisces.core.entity.EntityObject;
import com.pisces.core.entity.Property;
import com.pisces.core.service.EntityServiceImpl;
import com.pisces.core.service.PropertyService;
import com.pisces.core.utils.Primary;
import com.pisces.integration.bean.DataSource;
import com.pisces.integration.bean.FieldInfo;
import com.pisces.integration.bean.Scheme;
import com.pisces.integration.bean.SqlDataSource;
import com.pisces.integration.helper.DataConfig;
import com.pisces.integration.service.SqlDataSourceService;
import com.pisces.rds.provider.base.SQLProvider;

import tk.mybatis.mapper.entity.EntityColumn;

abstract class SqlDataSourceServiceImpl<T extends SqlDataSource, D extends BaseDao<T>> extends EntityServiceImpl<T, D> implements SqlDataSourceService<T> {
	private SQLProvider provider;
	private Connection conn;
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet resultSet;
	private SqlDataSource dataSource;
	
	@Autowired
	private PropertyService propertyService;
	
	public SqlDataSourceServiceImpl(SQLProvider provider) {
		this.provider = provider;
	}
	
	@Override
	public DataConfig getDataConfig() {
		return null;
	}
	
	@Override
	public boolean validConnection(DataSource dataSource, String tableName) throws Exception {
		if (!(dataSource instanceof SqlDataSource)) {
			return false;
		}
		Class.forName(this.provider.getDriverName());
		this.dataSource = (SqlDataSource)dataSource;
		this.conn = DriverManager.getConnection(this.provider.getConnection(this.dataSource.getHost(), this.dataSource.getPort(), this.dataSource.getDataBase(), this.dataSource.getCharset()), 
				this.dataSource.getUsername(), this.dataSource.getPassword());
		return this.provider.existedTable(this.conn, this.dataSource.getDataBase(), tableName);
	}

	@Override
	public boolean open(DataSource dataSource, String tableName) throws Exception {
		if (!(dataSource instanceof SqlDataSource)) {
			return false;
		}
		this.dataSource = (SqlDataSource)dataSource;
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
	public void beforeWriteTable(Scheme scheme, Collection<FieldInfo> fields) throws Exception {
		if (this.provider.existedTable(this.conn, this.conn.getCatalog(), scheme.getOutName())) {
			this.provider.dropTable(this.conn, scheme.getOutName());
		}
		Class<? extends EntityObject> entityClass = Primary.get().getEntityClass(scheme.getInName());
		List<EntityColumn> columns = new ArrayList<EntityColumn>();
		for (FieldInfo field : fields) {
			EntityColumn column = new EntityColumn();
			column.setColumn(field.getExternName());
			
			Property property = this.propertyService.get(entityClass, field.getName());
			if (property.getLarge()) {
				column.setJdbcType(this.provider.getJdbcType(property.getType(), property.getLarge()));
			}
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
	}

	@Override
	public void beforeWriteEntity(EntityObject entity) throws Exception {
		
	}
	
	@Override
	public void write(int index, String data) throws Exception {
		this.pstmt.setString(index, data);
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
	
	@Override
	public String obtainValue(EntityObject entity, Property property) {
		return null;
	}
}
