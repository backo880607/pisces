package com.pisces.user.bean;

import java.util.Collection;

import javax.persistence.Table;

import com.pisces.core.annotation.Relation;
import com.pisces.core.entity.EntityCoding;
import com.pisces.core.relation.Sign;
import com.pisces.core.relation.Type;

@Table(name = "USER_DEPARTMENT")
public class Department extends EntityCoding {
	public static final Sign tenant = sign();
	@Relation(clazz = "Account", sign = "department", type = Type.OneToMulti)
	public static final Sign accounts = sign();
	
	public Collection<Account> getAccounts() {
		return getList(accounts);
	}
	
	public Tenant getTenant() {
		return get(tenant);
	}
	
	public void setTenant(Tenant tenant) {
		
	}
}
