package com.pisces.platform.bean;

import com.pisces.core.annotation.Relation;
import com.pisces.core.entity.EntityObject;
import com.pisces.core.relation.Sign;
import com.pisces.core.relation.Type;

public class Catalog extends EntityObject {
	@Relation(clazz = "FormGroup", type = Type.OneToOne, owner = true)
	public static final Sign formGroup = sign();
	@Relation(clazz = "Catalog", sign = "childs", type = Type.OneToMulti, owner = true)
	public static final Sign parent = sign();
	public static final Sign childs = sign();
	
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
