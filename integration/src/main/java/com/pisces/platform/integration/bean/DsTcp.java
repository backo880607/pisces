package com.pisces.platform.integration.bean;

import com.pisces.platform.integration.enums.TCP_TYPE;

import javax.persistence.Table;

@Table(name = "INTEGRATION_DS_TCP")
public class DsTcp extends DataSource {
	private TCP_TYPE type;
	
	@Override
	public void init() {
		super.init();
		type = TCP_TYPE.TABLE;
	}

	public TCP_TYPE getType() {
		return type;
	}

	public void setType(TCP_TYPE type) {
		this.type = type;
	}
}
