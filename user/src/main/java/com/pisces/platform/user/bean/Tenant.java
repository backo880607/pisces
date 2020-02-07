package com.pisces.platform.user.bean;

import com.pisces.platform.core.annotation.Relation;
import com.pisces.platform.core.entity.EntityCoding;
import com.pisces.platform.core.relation.Sign;
import com.pisces.platform.core.relation.Type;
import com.pisces.platform.core.utils.DateUtils;

import javax.persistence.Table;
import java.util.Collection;
import java.util.Date;

@Table(name = "USER_TENANT")
public class Tenant extends EntityCoding {
    private Date endDate;
    private Integer maxLoginQty;
    private Integer maxAccountQty;

    @Relation(clazz = "Department", sign = "tenant", type = Type.OneToMulti, owner = true)
    public static final Sign departments = sign();
    public static final Sign parent = sign();
    @Relation(clazz = "Tenant", sign = "parent", type = Type.OneToMulti, owner = true)
    public static final Sign children = sign();

    @Override
    public void init() {
        super.init();
        endDate = DateUtils.MAX;
        maxLoginQty = 5;
        maxAccountQty = 5;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getMaxLoginQty() {
        return maxLoginQty;
    }

    public void setMaxLoginQty(Integer maxLoginQty) {
        this.maxLoginQty = maxLoginQty;
    }

    public Integer getMaxAccountQty() {
        return maxAccountQty;
    }

    public void setMaxAccountQty(Integer maxAccountQty) {
        this.maxAccountQty = maxAccountQty;
    }

    public Collection<Department> getDepartments() {
        return getList(departments);
    }

    public Tenant getParent() {
        return get(parent);
    }

    public void setParent(Tenant tenant) {
        set(parent, tenant);
    }

    public Collection<Tenant> getChildren() {
        return getList(children);
    }
}
