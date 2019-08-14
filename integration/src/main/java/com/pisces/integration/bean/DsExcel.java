package com.pisces.integration.bean;

import javax.persistence.Table;

@Table(name = "integration_ds_excel")
public class DsExcel extends DataSource {
	private String path;
	private String extension;
	
	@Override
	public void init() {
		super.init();
		path = "";
		extension = "xlsx";
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}
}
