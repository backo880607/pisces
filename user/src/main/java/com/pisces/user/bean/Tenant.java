package com.pisces.user.bean;

import java.util.Collection;

import com.pisces.core.entity.EntityCoding;
import com.pisces.core.relation.Sign;

public class Tenant extends EntityCoding {
	public static final Sign user = sign();
	
	public Collection<Account> getUser() {
		return getEntities(user);
	}
}
