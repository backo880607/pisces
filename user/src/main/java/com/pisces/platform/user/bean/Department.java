package com.pisces.platform.user.bean;

import com.pisces.platform.core.annotation.PropertyMeta;
import com.pisces.platform.core.annotation.Relation;
import com.pisces.platform.core.entity.EntityCoding;
import com.pisces.platform.core.relation.RelationKind;
import com.pisces.platform.core.relation.Sign;
import com.pisces.platform.core.relation.Type;

import javax.persistence.Table;
import java.util.Collection;

@Table(name = "USER_DEPARTMENT")
public class Department extends EntityCoding {
    public static final Sign tenant = sign();
    @Relation(clazz = "Role", type = Type.OneToMulti, owner = true)
    @PropertyMeta(kind = RelationKind.Set)
    public static final Sign roles = sign();
    @Relation(clazz = "Account", sign = "department", type = Type.OneToMulti)
    public static final Sign accounts = sign();

    public Tenant getTenant() {
        return get(tenant);
    }

    public void setTenant(Tenant tenant) {
        set(Department.tenant, tenant);
    }

    public Collection<Role> getRoles() {
        return getList(roles);
    }

    public Collection<Account> getAccounts() {
        return getList(accounts);
    }
}
