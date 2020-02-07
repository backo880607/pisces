package com.pisces.platform.user.bean;

import com.pisces.platform.core.annotation.Relation;
import com.pisces.platform.core.entity.EntityCoding;
import com.pisces.platform.core.relation.Sign;
import com.pisces.platform.core.relation.Type;

import javax.persistence.Table;
import java.util.Collection;

@Table(name = "USER_ROLE")
public class Role extends EntityCoding {
    public static final Sign parent = sign();
    @Relation(clazz = "Role", sign = "parent", type = Type.OneToMulti)
    public static final Sign children = sign();

    public Role getParent() {
        return get(parent);
    }

    public void setParent(Role role) {
        set(parent, role);
    }

    public Collection<Role> getChildren() {
        return getList(children);
    }
}
