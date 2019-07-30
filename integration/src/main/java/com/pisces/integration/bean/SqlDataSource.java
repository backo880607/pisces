package com.pisces.integration.bean;

public class SqlDataSource extends DataSource {
	private String ip;
	private Integer port;
	private String username;
	private String password;
	private String dataBase;
	
	@Override
	public void init() {
		super.init();
		ip = "";
		port = 0;
		username = "";
		password = "";
		dataBase = "";
	}
	
	public final String getIp() {
		return ip;
	}
	
	public final void setIp(String ip) {
		this.ip = ip;
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

}
