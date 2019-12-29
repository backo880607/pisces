package com.pisces.platform.integration.bean;

import com.pisces.platform.integration.enums.STREAM_TYPE;

import javax.persistence.Table;

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
