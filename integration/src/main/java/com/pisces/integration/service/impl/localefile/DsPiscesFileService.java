package com.pisces.integration.service.impl.localefile;

import java.util.Collection;

import com.pisces.core.entity.EntityObject;
import com.pisces.integration.bean.DataSource;
import com.pisces.integration.bean.FieldInfo;
import com.pisces.integration.bean.Scheme;
import com.pisces.integration.enums.LOCALE_FILE_TYPE;
import com.pisces.integration.helper.AdapterRegister;
import com.pisces.integration.helper.DataConfig;

public class DsPiscesFileService extends AdapterRegister<LOCALE_FILE_TYPE> {

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
	public LOCALE_FILE_TYPE getType() {
		return LOCALE_FILE_TYPE.PISCES;
	}

}
