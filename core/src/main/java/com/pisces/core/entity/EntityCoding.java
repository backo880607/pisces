package com.pisces.core.entity;

import com.pisces.core.annotation.PrimaryKey;
import com.pisces.core.enums.ENTITY_STATUS;

@PrimaryKey(fields={"code"})
public class EntityCoding extends EntityObject {
	private String code;
	private String name;
	private ENTITY_STATUS status;
	private String remarks;
	
	@Override
	public void init() {
		super.init();
		code = "";
		name = "";
		status = ENTITY_STATUS.ENABLE;
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
	
	public ENTITY_STATUS getStatus() {
		return status;
	}
	
	public void setStatus(ENTITY_STATUS status) {
		this.status = status;
	}
	
	public String getRemarks() {
		return remarks;
	}
	
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
}