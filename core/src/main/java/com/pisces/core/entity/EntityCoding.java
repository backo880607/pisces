package com.pisces.core.entity;

import com.pisces.core.annotation.PrimaryKey;

@PrimaryKey(fields={"code"})
public class EntityCoding extends EntityObject {
	private String code;
	private String name;
	private String remarks;
	
	@Override
	public void init() {
		super.init();
		code = "";
		name = "";
		remarks = "";
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
	
	public String getRemarks() {
		return remarks;
	}
	
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
}