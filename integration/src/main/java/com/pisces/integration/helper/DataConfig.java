package com.pisces.integration.helper;

public class DataConfig {
	private String sepField;
	private String sepEntity;
	private String replaceField = "▲";
	private String replaceEntity = "◆";
	
	public String getSepField() {
		return sepField;
	}
	
	public void setSepField(String sepField) {
		this.sepField = sepField;
	}
	
	public String getSepEntity() {
		return sepEntity;
	}
	
	public void setSepEntity(String sepEntity) {
		this.sepEntity = sepEntity;
	}
	
	public String getReplaceField() {
		return replaceField;
	}
	
	public void setReplaceField(String replaceField) {
		this.replaceField = replaceField;
	}
	
	public String getReplaceEntity() {
		return replaceEntity;
	}
	
	public void setReplaceEntity(String replaceEntity) {
		this.replaceEntity = replaceEntity;
	}
}
