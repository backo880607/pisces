package com.pisces.integration.bean;

import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.pisces.core.entity.EntityObject;
import com.pisces.core.relation.Ioc;
import com.pisces.core.relation.Sign;

@Table(name = "INTEGRATION_FIELD_INFO")
public class FieldInfo extends EntityObject {
	@NotBlank
	private String name;
	@NotBlank
	private String externName;
	
	public static final Sign scheme = sign();
	
	@Override
	public void init() {
		super.init();
		name = "";
		externName = "";
	}
	
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
	
	public void setScheme(Scheme scheme) {
		Ioc.set(this, FieldInfo.scheme, scheme);
	}
}
