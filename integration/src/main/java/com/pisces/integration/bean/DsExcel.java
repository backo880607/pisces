package com.pisces.integration.bean;

import javax.persistence.Table;

@Table(name = "INTEGRATION_DS_EXCEL")
public class DsExcel extends DataSource {
	private String extension;
	
	@Override
	public void init() {
		super.init();
		extension = "xlsx";
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}
}
