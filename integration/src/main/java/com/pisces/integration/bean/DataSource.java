package com.pisces.integration.bean;

import com.pisces.core.entity.EntityCoding;

public class DataSource extends EntityCoding {
	private String host;
	private String charset;
	
	@Override
	public void init() {
		super.init();
		host = "";
		charset = "utf-8";
	}
	
	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}
	
	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}
}
