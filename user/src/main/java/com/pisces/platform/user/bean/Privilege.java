package com.pisces.platform.user.bean;

import com.pisces.platform.core.annotation.Relation;
import com.pisces.platform.core.entity.EntityCoding;
import com.pisces.platform.core.relation.Sign;
import com.pisces.platform.core.relation.Type;

import javax.persistence.Table;
import java.util.Collection;

@Table(name = "USER_PRIVILEGE")
public class Privilege extends EntityCoding {
    @Relation(clazz = "ResourceAccess", type = Type.MultiToMulti)
    public static final Sign resources = sign();

    public Collection<ResourceAccess> getResources() {
        return getList(resources);
    }
}
