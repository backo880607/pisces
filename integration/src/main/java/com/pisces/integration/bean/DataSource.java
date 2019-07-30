package com.pisces.integration.bean;

import com.pisces.core.entity.EntityCoding;

public class DataSource extends EntityCoding {
	private String charset;
	
	@Override
	public void init() {
		super.init();
		charset = "GBK";
	}

	public final String getCharset() {
		return charset;
	}

	public final void setCharset(String charset) {
		this.charset = charset;
	}
}
