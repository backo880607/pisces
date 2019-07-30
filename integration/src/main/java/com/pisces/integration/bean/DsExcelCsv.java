package com.pisces.integration.bean;

import javax.persistence.Table;

@Table(name = "integration_ds_excel_csv")
public class DsExcelCsv extends DataSource {
	private String path;
	
	@Override
	public void init() {
		super.init();
		path = "";
	}

	public final String getPath() {
		return path;
	}

	public final void setPath(String path) {
		this.path = path;
	}
}
