package com.pisces.integration.service.impl;

import java.util.Collection;

import com.pisces.core.dao.BaseDao;
import com.pisces.core.entity.EntityObject;
import com.pisces.core.entity.Property;
import com.pisces.core.service.EntityServiceImpl;
import com.pisces.integration.bean.DataSource;
import com.pisces.integration.bean.FieldInfo;
import com.pisces.integration.bean.Scheme;
import com.pisces.integration.helper.DataConfig;
import com.pisces.integration.service.JsonDataSourceService;

public class JsonDataSourceServiceImpl<T extends DataSource, D extends BaseDao<T>> extends EntityServiceImpl<T, D> implements JsonDataSourceService<T> {

	@Override
	public DataConfig getDataConfig() {
		return null;
	}

	@Override
	public boolean validConnection(DataSource dataSource, String tableName) throws Exception {
		return false;
	}

	@Override
	public boolean open(DataSource dataSource, String tableName) throws Exception {
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
	public void beforeWriteTable(Scheme scheme, Collection<FieldInfo> fields) throws Exception {
	}

	@Override
	public void beforeWriteEntity(EntityObject entity) throws Exception {
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

	@Override
	public String obtainValue(EntityObject entity, Property property) {
		return null;
	}
}
