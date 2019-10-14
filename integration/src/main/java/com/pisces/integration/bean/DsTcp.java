package com.pisces.integration.bean;

import javax.persistence.Table;

import com.pisces.integration.enums.TCP_TYPE;

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
