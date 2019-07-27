package com.pisces.integration.service;

import java.io.Closeable;
import java.lang.reflect.Field;
import java.util.Collection;

import com.pisces.core.service.EntityService;
import com.pisces.integration.bean.DataSource;
import com.pisces.integration.bean.FieldInfo;

public interface DataSourceService<T extends DataSource> extends EntityService<T>, Closeable {
	boolean validConnection(DataSource dataSource, String tableName) throws Exception;
	boolean open(DataSource dataSource, String tableName) throws Exception;
	void close();
	boolean checkTableStructure(DataSource dataSource, String tableName, Collection<FieldInfo> fields) throws Exception;
	boolean executeQuery(DataSource dataSource, String tableName, Collection<FieldInfo> fields) throws Exception;
	
	boolean step() throws Exception;
	String getData(int index) throws Exception;
	String getData(Field field) throws Exception;
	
	void write(Field field, String data) throws Exception;
}
