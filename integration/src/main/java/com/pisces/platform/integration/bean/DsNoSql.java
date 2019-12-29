package com.pisces.platform.integration.bean;

import com.pisces.platform.integration.enums.NOSQL_TYPE;

import javax.persistence.Table;

@Table(name = "INTEGRATION_DS_NO_SQL")
public class DsNoSql extends DataSource {
	private NOSQL_TYPE type;
	
	@Override
	public void init() {
		super.init();
		type = NOSQL_TYPE.REDIS;
	}

	public NOSQL_TYPE getType() {
		return type;
	}

	public void setType(NOSQL_TYPE type) {
		this.type = type;
	}
	
}
