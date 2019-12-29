package com.pisces.platform.report.bean;

import com.pisces.platform.core.entity.EntityObject;
import org.apache.ibatis.type.JdbcType;
import tk.mybatis.mapper.annotation.ColumnType;

public class PivotGroupSetting extends EntityObject {
	@ColumnType(jdbcType = JdbcType.LONGVARCHAR)
	private String code;
	@ColumnType(jdbcType = JdbcType.LONGVARCHAR)
	private String name;
	
	private String filter;
	
	private String sort;
	
	@Override
	public void init() {
		super.init();
		code = "";
		name = "";
		filter = "";
		sort = "";
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFilter() {
		return filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
}
