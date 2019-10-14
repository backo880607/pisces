package com.pisces.integration.bean;

import javax.persistence.Table;

import com.pisces.integration.enums.STREAM_TYPE;

@Table(name = "INTEGRATION_DS_STREAM")
public class DsStream extends DataSource {
	private STREAM_TYPE type;
	
	@Override
	public void init() {
		super.init();
		type = STREAM_TYPE.TABLE;
	}

	public STREAM_TYPE getType() {
		return type;
	}

	public void setType(STREAM_TYPE type) {
		this.type = type;
	}
	
}
