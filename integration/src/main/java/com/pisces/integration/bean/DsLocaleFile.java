package com.pisces.integration.bean;

import javax.persistence.Table;

import com.pisces.integration.enums.LOCALE_FILE_TYPE;

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
