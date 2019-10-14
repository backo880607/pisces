package com.pisces.integration.bean;

import javax.persistence.Table;

import com.pisces.integration.enums.HTTP_TYPE;

@Table(name = "INTEGRATION_DS_HTTP")
public class DsHttp extends DataSource {
	private HTTP_TYPE type;
	
	@Override
	public void init() {
		super.init();
		type = HTTP_TYPE.TABLE;
	}

	public HTTP_TYPE getType() {
		return type;
	}

	public void setType(HTTP_TYPE type) {
		this.type = type;
	}
}
