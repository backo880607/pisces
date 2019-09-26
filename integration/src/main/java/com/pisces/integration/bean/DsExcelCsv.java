package com.pisces.integration.bean;

import javax.persistence.Table;

@Table(name = "INTEGRATION_DS_EXCEL_CSV")
public class DsExcelCsv extends DataSource {
	
	@Override
	public void init() {
		super.init();
	}
}
