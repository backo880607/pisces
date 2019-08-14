package com.pisces.platform.bean;

import java.util.Collection;

import javax.persistence.Table;

import com.pisces.core.annotation.Relation;
import com.pisces.core.entity.EntityObject;
import com.pisces.core.relation.Ioc;
import com.pisces.core.relation.Sign;
import com.pisces.core.relation.Type;

@Table(name = "platform_catalog")
public class Catalog extends EntityObject {
	private String name;
	
	@Relation(clazz = "FormGroup", type = Type.OneToOne, owner = true)
	public static final Sign formGroup = sign();
	public static final Sign parent = sign();
	@Relation(clazz = "Catalog", sign = "parent", type = Type.OneToMulti, owner = true)
	public static final Sign childs = sign();
	
	@Override
	public void init() {
		super.init();
		name = "";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public FormGroup getFormGroup() {
		return getEntity(formGroup);
	}
	
	public void setFormGroup(FormGroup formGroup) {
		Ioc.set(this, Catalog.formGroup, formGroup);
	}
	
	public Catalog getParent() {
		return getEntity(parent);
	}
	
	public void setParent(Catalog catalog) {
		Ioc.set(this, parent, catalog);
	}
	
	public Collection<Catalog> getChilds() {
		return getEntities(childs);
	}
}
