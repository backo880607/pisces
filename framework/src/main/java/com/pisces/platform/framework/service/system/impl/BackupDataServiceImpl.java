package com.pisces.platform.framework.service.system.impl;

import com.pisces.platform.core.entity.EntityObject;
import com.pisces.platform.core.service.EntityServiceImpl;
import com.pisces.platform.framework.bean.system.BackupData;
import com.pisces.platform.framework.dao.system.BackupDataDao;
import com.pisces.platform.framework.service.system.BackupDataService;
import com.pisces.platform.integration.bean.DataSource;
import com.pisces.platform.integration.bean.FieldInfo;
import com.pisces.platform.integration.bean.Scheme;
import com.pisces.platform.integration.helper.DataConfig;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class BackupDataServiceImpl extends EntityServiceImpl<BackupData, BackupDataDao> implements BackupDataService {

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
