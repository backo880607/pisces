package com.pisces.platform.integration.bean;

import com.pisces.platform.integration.enums.HTTP_TYPE;

import javax.persistence.Table;

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
