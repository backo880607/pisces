package com.pisces.web.controller;

import java.io.Serializable;

public class ResponseData implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5405084202383222826L;
	
	private boolean success;
	private int status;
	private String name;
	private String message;
	private String exception;
	private Object data;
	
	public boolean getSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	public int getStatus() {
		return status;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}
	
	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
