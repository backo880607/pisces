package com.pisces.core.entity;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pisces.core.enums.CREATE_UPDATE_TYPE;
import com.pisces.core.enums.ENTITY_STATUS;
import com.pisces.core.relation.Ioc;
import com.pisces.core.relation.RefBase;
import com.pisces.core.relation.Sign;
import com.pisces.core.utils.IDGenerator;
import com.pisces.core.utils.Primary;

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
	public boolean equals(Object obj) {
		if (this.getClass() == obj.getClass()) {
			return this.getId() == ((EntityObject)obj).getId();
		}
		return false;
	}
	
	@Override
	public int hashCode() {
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
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public boolean getCreated() {
		return created;
	}

	public void setCreated(boolean created) {
		this.created = created;
	}

	public boolean getModified() {
		return modified;
	}

	public void setModified(boolean modified) {
		this.modified = modified;
	}
	
	public boolean getInitialized() {
		return initialized;
	}
	
	public void setInitialized(boolean initialized) {
		this.initialized = initialized;
	}
	
	public Map<Sign, RefBase> getRelations() {
		return relations;
	}
	
	public void setRelations(Map<Sign, RefBase> relations) {
		this.relations = relations;
	}
	
	@SuppressWarnings("unchecked")
	public <T extends EntityObject> T get(Sign sign) {
		return (T)this.relations.get(sign).get();
	}
	
	@SuppressWarnings("unchecked")
	public <T extends EntityObject> Collection<T> getList(Sign sign) {
		return (Collection<T>)this.relations.get(sign).collection();
	}
	
	public void set(Sign sign, EntityObject relaEntity) {
		Ioc.set(this, sign, relaEntity);
	}
	
	public Object getUserFields(String name) {
		return this.userFields.get(name);
	}
	
	public void setUserFields(String name, Object value) {
		this.userFields.put(name, value);
	}
	
	public String getCreateBy() {
		return createBy;
	}
	
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	
	public String getUpdateBy() {
		return updateBy;
	}
	
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	
	public Date getCreateDate() {
		return createDate;
	}
	
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	public Date getUpdateDate() {
		return updateDate;
	}
	
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	
	public CREATE_UPDATE_TYPE getCreateType() {
		return createType;
	}
	
	public void setCreateType(CREATE_UPDATE_TYPE createType) {
		this.createType = createType;
	}
	
	public CREATE_UPDATE_TYPE getUpdateType() {
		return updateType;
	}
	
	public void setUpdateType(CREATE_UPDATE_TYPE updateType) {
		this.updateType = updateType;
	}
	
	public ENTITY_STATUS getStatus() {
		return status;
	}
	
	public void setStatus(ENTITY_STATUS status) {
		this.status = status;
	}

	@Override
	public int compareTo(EntityObject o) {
		if (this.getId() == o.getId()) {
			return 0;
		}
		return this.getId() > o.getId() ? -1 : 1;
	}
}
