package com.pisces.integration.helper;

import java.io.Closeable;
import java.util.Collection;

import com.pisces.core.entity.EntityObject;
import com.pisces.integration.bean.DataSource;
import com.pisces.integration.bean.FieldInfo;
import com.pisces.integration.bean.Scheme;

public interface DataSourceAdapter extends Closeable {
	void modifyConfig(DataConfig config);
	
	boolean validConnection(DataSource dataSource, String tableName, boolean export) throws Exception;
	boolean open(DataSource dataSource, String tableName, boolean export) throws Exception;
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
