package com.pisces.report.bean;

import com.pisces.core.entity.EntityObject;

public class PivotTable extends EntityObject {
	private String filter;
	
	@Override
	public void init() {
		super.init();
		filter = "";
	}

	public String getFilter() {
		return filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}
}
