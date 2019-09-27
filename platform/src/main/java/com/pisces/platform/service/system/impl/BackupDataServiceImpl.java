package com.pisces.platform.service.system.impl;

import java.util.Collection;

import org.springframework.stereotype.Service;

import com.pisces.core.entity.EntityObject;
import com.pisces.core.entity.Property;
import com.pisces.core.service.EntityServiceImpl;
import com.pisces.integration.bean.DataSource;
import com.pisces.integration.bean.FieldInfo;
import com.pisces.integration.bean.Scheme;
import com.pisces.integration.helper.DataConfig;
import com.pisces.platform.bean.system.BackupData;
import com.pisces.platform.dao.system.BackupDataDao;
import com.pisces.platform.service.system.BackupDataService;

@Service
public class BackupDataServiceImpl extends EntityServiceImpl<BackupData, BackupDataDao> implements BackupDataService {

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
