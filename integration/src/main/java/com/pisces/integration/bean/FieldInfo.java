package com.pisces.integration.bean;

import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.pisces.core.entity.EntityCoding;
import com.pisces.core.relation.Sign;

@Table(name = "integration_field_info")
public class FieldInfo extends EntityCoding {
	@NotBlank
	private String name;
	@NotBlank
	private String externName;
	
	public static final Sign scheme = sign();
	
	public final String getName() {
		return name;
	}
	
	public final void setName(String name) {
		this.name = name;
	}
	
	public final String getExternName() {
		return externName;
	}
	
	public final void setExternName(String externName) {
		this.externName = externName;
	}
	
	public final Scheme getScheme() {
		return getEntity(scheme);
	}
}
