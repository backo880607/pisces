package com.pisces.platform.integration.service.impl;

import com.pisces.platform.core.entity.EntityObject;
import com.pisces.platform.integration.bean.DataSource;
import com.pisces.platform.integration.bean.FieldInfo;
import com.pisces.platform.integration.bean.Scheme;
import com.pisces.platform.integration.helper.DataConfig;
import com.pisces.platform.integration.helper.DataSourceAdapter;

import java.util.Collection;

public abstract class JsonDataSourceService<T extends Enum<T>> implements DataSourceAdapter {

	@Override
	public void modifyConfig(DataConfig config) {
	}

	@Override
	public boolean validConnection(DataSource dataSource, String tableName, boolean export) throws Exception {
		return false;
	}

	@Override
	public boolean open(DataSource dataSource, String tableName, boolean export) throws Exception {
		return false;
	}

	@Override
	public void close() {
		
	}

	@Override
	public boolean executeQuery(DataSource dataSource, String tableName, Collection<FieldInfo> fields)
			throws Exception {
		return false;
	}

	@Override
	public Collection<FieldInfo> getFields() throws Exception {
		return null;
	}

	@Override
	public boolean step() throws Exception {
		return false;
	}

	@Override
	public String getData(int index) throws Exception {
		return null;
	}

	@Override
	public boolean beforeWriteTable(Scheme scheme, Collection<FieldInfo> fields) throws Exception {
		return true;
	}

	@Override
	public boolean beforeWriteEntity(EntityObject entity) throws Exception {
		return true;
	}

	@Override
	public void write(int index, String data) throws Exception {
	}

	@Override
	public void afterWriteEntity(EntityObject entity) throws Exception {
	}

	@Override
	public void afterWriteTable(Scheme scheme) throws Exception {
	}
}
