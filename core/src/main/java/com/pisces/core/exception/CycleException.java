package com.pisces.core.exception;

import com.pisces.core.entity.EntityObject;

public class CycleException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6468776684821094915L;
	
	private EntityObject entity;

	public CycleException() {
		super();
	}
	
	public CycleException(String message) {
		super(message);
	}
	
	public CycleException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public CycleException(Throwable cause) {
		super(cause);
	}

	public EntityObject getEntity() {
		return entity;
	}

	public void setEntity(EntityObject entity) {
		this.entity = entity;
	}
}
