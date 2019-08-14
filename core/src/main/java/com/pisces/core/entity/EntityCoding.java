package com.pisces.core.entity;

import com.pisces.core.annotation.PrimaryKey;
import com.pisces.core.enums.EntityStatus;
import com.pisces.core.validator.InsertGroup;

@PrimaryKey(fields={"code"}, groups = {InsertGroup.class})
public class EntityCoding extends EntityObject {
	private String code;
	private String name;
	private EntityStatus status;
	private String remarks;
	
	@Override
	public void init() {
		super.init();
		code = "";
		name = "";
		status = EntityStatus.ENABLE;
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
	
	public EntityStatus getStatus() {
		return status;
	}
	
	public void setStatus(EntityStatus status) {
		this.status = status;
	}
	
	public String getRemarks() {
		return remarks;
	}
	
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
}