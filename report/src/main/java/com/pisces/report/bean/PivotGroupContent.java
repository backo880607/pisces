package com.pisces.report.bean;

import org.apache.ibatis.type.JdbcType;

import com.pisces.core.entity.EntityObject;

import tk.mybatis.mapper.annotation.ColumnType;

public class PivotGroupContent extends EntityObject {
	@ColumnType(jdbcType = JdbcType.LONGVARCHAR)
	private String code;
	@ColumnType(jdbcType = JdbcType.LONGVARCHAR)
	private String name;
	
	@ColumnType(jdbcType = JdbcType.LONGVARCHAR)
	private String textColor;
	
	@ColumnType(jdbcType = JdbcType.LONGVARCHAR)
	private String backgroundColor;
	
	private Boolean limitZero;

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

	public String getTextColor() {
		return textColor;
	}

	public void setTextColor(String textColor) {
		this.textColor = textColor;
	}

	public String getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public Boolean getLimitZero() {
		return limitZero;
	}

	public void setLimitZero(Boolean limitZero) {
		this.limitZero = limitZero;
	}
}
