package com.pisces.integration.service;

import java.io.Closeable;
import java.lang.reflect.Field;

import com.pisces.core.service.EntityService;
import com.pisces.integration.bean.DataSource;

public interface DataSourceService<T extends DataSource> extends EntityService<T>, Closeable {
	boolean validConnection(DataSource dataSource, String tableName) throws Exception;
	boolean open(DataSource dataSource, String tableName) throws Exception;
	void close();
	
	boolean step() throws Exception;
	String getData(int index) throws Exception;
	String getData(Field field) throws Exception;
	
	void write(Field field, String data) throws Exception;
}
