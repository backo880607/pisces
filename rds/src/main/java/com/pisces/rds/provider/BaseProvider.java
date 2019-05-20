package com.pisces.rds.provider;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Date;

import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.type.JdbcType;

import com.pisces.core.entity.MultiEnum;
import com.pisces.rds.provider.base.AdaptiveProvider;
import com.pisces.rds.provider.base.DB2Provider;
import com.pisces.rds.provider.base.MySQLProvider;
import com.pisces.rds.provider.base.OracleProvider;
import com.pisces.rds.provider.base.SQLProvider;
import com.pisces.rds.provider.base.SQLServerProvider;
import com.pisces.rds.provider.base.SQLiteProvider;

import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.MapperTemplate;

public abstract class BaseProvider extends MapperTemplate {
	private SQLProvider provider;
	
	public BaseProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
		super(mapperClass, mapperHelper);
	}
	
	protected SQLProvider getProvider(MappedStatement ms) {
		if (this.provider == null) {
			synchronized (BaseProvider.class) {
				if (this.provider == null) {
					Connection conn = null;
					try {
						conn = ms.getConfiguration().getEnvironment().getDataSource().getConnection();
						DatabaseMetaData dbmd = conn.getMetaData();
						if (dbmd == null) {
							return null;
						}
						String dbName = dbmd.getDatabaseProductName();
						if (dbName == null) {
							return null;
						}
						
						if (dbName.startsWith("DB2/")) {
							this.provider = new DB2Provider();
						} else if (dbName.startsWith("Oracle")) {
							this.provider = new OracleProvider();
						} else if (dbName.startsWith("Microsoft SQL Server")) {
							this.provider = new SQLServerProvider();
						} else if (dbName.equals("Adaptive Server")) {
							this.provider = new AdaptiveProvider();
						} else if (dbName.equals("MySQL")) {
							this.provider = new MySQLProvider();
						} else if (dbName.equals("sqlite")) {
							this.provider = new SQLiteProvider();
						}
						this.provider.setDatabase(conn.getCatalog());
						this.provider.setDbMetaData(dbmd);
					} catch (SQLException e) {
						e.printStackTrace();
					} finally {
					}
				}
			}
		}
		
		return this.provider;
	}
	
	protected JdbcType getJdbcType(Class<?> javaType) throws SQLException {
		if (javaType == null) {
			throw new SQLException("javaType must be not null");
		}
		if (javaType == Boolean.class) {
			return JdbcType.BIT;
		} else if (javaType == Byte.class) {
			return JdbcType.TINYINT;
		} else if (javaType == Short.class) {
			return JdbcType.SMALLINT;
		} else if (javaType == Integer.class) {
			return JdbcType.INTEGER;
		} else if (javaType == Long.class) {
			return JdbcType.BIGINT;
		} else if (javaType == Float.class) {
			return JdbcType.REAL;
		} else if (javaType == Double.class) {
			return JdbcType.DOUBLE;
		} else if (javaType == BigDecimal.class) {
			return JdbcType.DECIMAL;
		} else if (javaType == String.class) {
			return JdbcType.VARCHAR;
		} else if (javaType == Date.class) {
			return JdbcType.DATE;
		} else if (javaType == Clob.class) {
			return JdbcType.CLOB;
		} else if (javaType == Blob.class) {
			return JdbcType.BLOB;
		} else if (javaType == URL.class) {
			return JdbcType.DATALINK;
		} else if (javaType.isEnum()) {
			return JdbcType.VARCHAR;
		} else if (MultiEnum.class.isAssignableFrom(javaType)) {
			return JdbcType.INTEGER;
		}
		
		throw new SQLException("don`t match jdbc type: " + javaType.getName());
	}
}
