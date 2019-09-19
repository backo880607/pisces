package com.pisces.core.exception;

import com.pisces.core.entity.EntityObject;
import com.pisces.core.entity.Property;

public class UpdateException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4534356790370600673L;
	
	private EntityObject entity;
	private Property property;
	private Object value;

	public UpdateException() {
		super();
	}
	
	public UpdateException(String message) {
		super(message);
	}
	
	public UpdateException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public UpdateException(Throwable cause) {
		super(cause);
	}

	public EntityObject getEntity() {
		return entity;
	}

	public void setEntity(EntityObject entity) {
		this.entity = entity;
	}

	public Property getProperty() {
		return property;
	}

	public void setProperty(Property property) {
		this.property = property;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}
}
