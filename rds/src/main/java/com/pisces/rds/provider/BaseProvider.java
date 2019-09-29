package com.pisces.rds.provider;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import org.apache.ibatis.mapping.MappedStatement;

import com.pisces.rds.provider.base.AdaptiveProvider;
import com.pisces.rds.provider.base.DB2Provider;
import com.pisces.rds.provider.base.MySqlProvider;
import com.pisces.rds.provider.base.OracleProvider;
import com.pisces.rds.provider.base.SQLProvider;
import com.pisces.rds.provider.base.SQLiteProvider;
import com.pisces.rds.provider.base.SqlServerProvider;

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
							this.provider = new SqlServerProvider();
						} else if (dbName.equals("Adaptive Server")) {
							this.provider = new AdaptiveProvider();
						} else if (dbName.equals("MySQL")) {
							this.provider = new MySqlProvider();
						} else if (dbName.equals("SQLite")) {
							this.provider = new SQLiteProvider();
						}
					} catch (SQLException e) {
						e.printStackTrace();
					} finally {
					}
				}
			}
		}
		
		return this.provider;
	}
}
