package com.pisces.integration.bean;

import javax.persistence.Table;

import com.pisces.integration.enums.SQL_TYPE;

@Table(name = "INTEGRATION_DS_SQL")
public class DsSql extends DataSource {
	private Integer port;
	private String username;
	private String password;
	private String dataBase;
	private SQL_TYPE type;
	
	@Override
	public void init() {
		super.init();
		port = 0;
		username = "";
		password = "";
		dataBase = "";
		type = SQL_TYPE.MYSQL;
	}
	
	public final Integer getPort() {
		return port;
	}
	
	public final void setPort(Integer port) {
		this.port = port;
	}
	
	public final String getUsername() {
		return username;
	}
	
	public final void setUsername(String username) {
		this.username = username;
	}
	
	public final String getPassword() {
		return password;
	}
	
	public final void setPassword(String password) {
		this.password = password;
	}

	public final String getDataBase() {
		return dataBase;
	}

	public final void setDataBase(String dataBase) {
		this.dataBase = dataBase;
	}

	public SQL_TYPE getType() {
		return type;
	}

	public void setType(SQL_TYPE type) {
		this.type = type;
	}

}
