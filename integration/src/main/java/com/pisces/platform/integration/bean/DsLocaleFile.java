package com.pisces.platform.integration.bean;

import com.pisces.platform.integration.enums.LOCALE_FILE_TYPE;

import javax.persistence.Table;

@Table(name = "INTEGRATION_DS_LOCALE_FILE")
public class DsLocaleFile extends DataSource {
	private LOCALE_FILE_TYPE type;
	
	@Override
	public void init() {
		super.init();
		setType(LOCALE_FILE_TYPE.PISCES);
	}

	public LOCALE_FILE_TYPE getType() {
		return type;
	}

	public void setType(LOCALE_FILE_TYPE type) {
		this.type = type;
	}
}
