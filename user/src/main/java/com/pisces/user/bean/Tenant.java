package com.pisces.user.bean;

import java.util.Collection;

import javax.persistence.Table;

import com.pisces.core.entity.EntityCoding;
import com.pisces.core.relation.Sign;

@Table(name = "user_tenant")
public class Tenant extends EntityCoding {
	public static final Sign user = sign();
	
	public Collection<Account> getUser() {
		return getEntities(user);
	}
}
