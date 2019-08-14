package com.pisces.platform.bean;

import javax.persistence.Table;

import com.pisces.core.entity.EntityObject;
import com.pisces.core.relation.Ioc;
import com.pisces.core.relation.Sign;

@Table(name = "platform_form")
public class Form extends EntityObject {
	public static final Sign formGroup = sign();

	public FormGroup getFormGroup() {
		return getEntity(formGroup);
	}
	
	public void setFormGroup(FormGroup formGroup) {
		Ioc.set(this, Form.formGroup, formGroup);
	}
}
