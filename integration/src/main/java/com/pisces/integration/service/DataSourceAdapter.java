package com.pisces.integration.service;

import java.io.Closeable;
import java.util.Collection;

import com.pisces.core.entity.EntityObject;
import com.pisces.integration.bean.DataSource;
import com.pisces.integration.bean.FieldInfo;
import com.pisces.integration.bean.Scheme;
import com.pisces.integration.helper.DataConfig;

public interface DataSourceAdapter extends Closeable {
	DataConfig getDataConfig();
	
	boolean validConnection(DataSource dataSource, String tableName) throws Exception;
	boolean open(DataSource dataSource, String tableName) throws Exception;
	void close();
	boolean executeQuery(DataSource dataSource, String tableName, Collection<FieldInfo> fields) throws Exception;
	Collection<FieldInfo> getFields() throws Exception;
	
	boolean step() throws Exception;
	String getData(int index) throws Exception;
	
	void beforeWriteTable(Scheme scheme, Collection<FieldInfo> fields) throws Exception;
	void beforeWriteEntity(EntityObject entity) throws Exception;
	void write(int index, String data) throws Exception;
	void afterWriteEntity(EntityObject entity) throws Exception;
	void afterWriteTable(Scheme scheme) throws Exception;
}
