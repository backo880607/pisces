package com.pisces.platform.bean;

import com.pisces.core.annotation.Relation;
import com.pisces.core.entity.EntityObject;
import com.pisces.core.relation.Sign;
import com.pisces.core.relation.Type;

public class FormGroup extends EntityObject {
	@Relation(clazz = "Form", sign = "formGroup", type = Type.OneToMulti, owner = true)
	public static final Sign forms = sign();
}
