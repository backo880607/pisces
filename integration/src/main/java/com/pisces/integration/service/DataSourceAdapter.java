package com.pisces.integration.service;

import java.io.Closeable;
import java.lang.reflect.Field;
import java.util.Collection;

import com.pisces.core.entity.EntityObject;
import com.pisces.integration.bean.DataSource;
import com.pisces.integration.bean.FieldInfo;

public interface DataSourceAdapter extends Closeable {
	boolean validConnection(DataSource dataSource, String tableName) throws Exception;
	boolean open(DataSource dataSource, String tableName) throws Exception;
	void close();
	boolean executeQuery(DataSource dataSource, String tableName, Collection<FieldInfo> fields) throws Exception;
	Collection<FieldInfo> getFields() throws Exception;
	
	boolean step() throws Exception;
	String getData(int index) throws Exception;
	String getData(Field field) throws Exception;
	
	void writeHeader(Collection<FieldInfo> fields);
	void beforeWriteEntity(EntityObject entity);
	void write(int index, String data) throws Exception;
	void afterWriteEntity(EntityObject entity);
}
