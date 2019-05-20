package com.pisces.integration.bean;

public class SqlDataSource extends DataSource {
	private String ip;
	private Integer port;
	private String username;
	private String password;
	private String dbName;
	
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

	public final String getDbName() {
		return dbName;
	}

	public final void setDbName(String dbName) {
		this.dbName = dbName;
	}

}
