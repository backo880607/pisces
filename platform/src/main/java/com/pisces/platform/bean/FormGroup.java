package com.pisces.platform.bean;

import java.util.Collection;

import javax.persistence.Table;

import com.pisces.core.annotation.Relation;
import com.pisces.core.entity.EntityObject;
import com.pisces.core.relation.Sign;
import com.pisces.core.relation.Type;

@Table(name = "platform_form_group")
public class FormGroup extends EntityObject {
	@Relation(clazz = "Form", sign = "formGroup", type = Type.OneToMulti, owner = true)
	public static final Sign forms = sign();
	
	public Collection<Form> getForms() {
		return getEntities(forms);
	}
}
