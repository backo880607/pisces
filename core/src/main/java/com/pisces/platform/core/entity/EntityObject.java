package com.pisces.platform.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pisces.platform.core.enums.CREATE_UPDATE_TYPE;
import com.pisces.platform.core.enums.ENTITY_STATUS;
import com.pisces.platform.core.relation.Ioc;
import com.pisces.platform.core.relation.RefBase;
import com.pisces.platform.core.relation.Sign;
import com.pisces.platform.core.utils.IDGenerator;
import com.pisces.platform.core.utils.Primary;

import javax.persistence.Id;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class EntityObject implements Comparable<EntityObject> {
    protected static Sign sign() {
        return null;
    }

    @Id
    private Long id;

    @JsonIgnore
    private transient boolean created = false;
    @JsonIgnore
    private transient boolean modified = false;
    @JsonIgnore
    private transient boolean initialized = false;

    @JsonIgnore
    private transient Map<Sign, RefBase> relations;
    private transient Map<String, Object> userFields = new HashMap<>();

    private String createBy;
    private String updateBy;
    private Date createDate;
    private Date updateDate;
    private CREATE_UPDATE_TYPE createType;
    private CREATE_UPDATE_TYPE updateType;
    private ENTITY_STATUS status;

    public EntityObject() {
        Primary.get().createRelation(this);
    }

    @Override
    public final boolean equals(Object obj) {
        if (this.getClass() == obj.getClass()) {
            return this.getId().equals(((EntityObject) obj).getId());
        }
        return false;
    }

    @Override
    public final int hashCode() {
        return Long.hashCode(this.id);
    }

    public void init() {
        id = IDGenerator.instance.getID();
        createBy = "";
        updateBy = "";
        createDate = new Date();
        updateDate = createDate;
        createType = CREATE_UPDATE_TYPE.SYSTEM;
        updateType = CREATE_UPDATE_TYPE.SYSTEM;
        status = ENTITY_STATUS.ENABLE;
        initialized = true;
    }

    public final Long getId() {
        return id;
    }

    public final void setId(Long value) {
        this.id = value;
    }

    public final boolean getCreated() {
        return created;
    }

    public final void setCreated(boolean value) {
        this.created = value;
    }

    public final boolean getModified() {
        return modified;
    }

    public final void setModified(boolean value) {
        this.modified = value;
    }

    public final boolean getInitialized() {
        return initialized;
    }

    public final void setInitialized(boolean value) {
        this.initialized = value;
    }

    public final Map<Sign, RefBase> getRelations() {
        return relations;
    }

    public final void setRelations(Map<Sign, RefBase> value) {
        this.relations = value;
    }

    @SuppressWarnings("unchecked")
    public <T extends EntityObject> T get(Sign sign) {
        return (T) this.relations.get(sign).get();
    }

    @SuppressWarnings("unchecked")
    public <T extends EntityObject> Collection<T> getList(Sign sign) {
        return (Collection<T>) this.relations.get(sign).collection();
    }

    public final void set(Sign sign, EntityObject relaEntity) {
        Ioc.set(this, sign, relaEntity);
    }

    public final Object getUserFields(String name) {
        return this.userFields.get(name);
    }

    public final void setUserFields(String name, Object value) {
        this.userFields.put(name, value);
    }

    public final String getCreateBy() {
        return createBy;
    }

    public final void setCreateBy(String value) {
        this.createBy = value;
    }

    public final String getUpdateBy() {
        return updateBy;
    }

    public final void setUpdateBy(String value) {
        this.updateBy = value;
    }

    public final Date getCreateDate() {
        return createDate;
    }

    public final void setCreateDate(Date value) {
        this.createDate = value;
    }

    public final Date getUpdateDate() {
        return updateDate;
    }

    public final void setUpdateDate(Date value) {
        this.updateDate = value;
    }

    public final CREATE_UPDATE_TYPE getCreateType() {
        return createType;
    }

    public final void setCreateType(CREATE_UPDATE_TYPE value) {
        this.createType = value;
    }

    public final CREATE_UPDATE_TYPE getUpdateType() {
        return updateType;
    }

    public final void setUpdateType(CREATE_UPDATE_TYPE value) {
        this.updateType = value;
    }

    public final ENTITY_STATUS getStatus() {
        return status;
    }

    public final void setStatus(ENTITY_STATUS value) {
        this.status = value;
    }

    @Override
    public final int compareTo(EntityObject o) {
        return o.getId().compareTo(this.getId());
    }
}
