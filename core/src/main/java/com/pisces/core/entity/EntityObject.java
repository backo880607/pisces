package com.pisces.core.entity;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pisces.core.enums.CreateUpdateType;
import com.pisces.core.exception.ExistedException;
import com.pisces.core.relation.RefBase;
import com.pisces.core.relation.Sign;
import com.pisces.core.utils.DateUtils;
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
	private transient Map<Sign, RefBase> relations;
	private transient Map<String, Object> userFields = new HashMap<>();
	
	private String createBy;
	private String updateBy;
	private Date createDate;
	private Date updateDate;
	private CreateUpdateType createType;
	private CreateUpdateType updateType;
	
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
		createDate = DateUtils.INVALID_DATE;
		updateDate = DateUtils.INVALID_DATE;
		createType = CreateUpdateType.SYSTEM;
		updateType = CreateUpdateType.SYSTEM;
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

	public boolean isModified() {
		return modified;
	}

	public void setModified(boolean modified) {
		this.modified = modified;
	}
	
	public Map<Sign, RefBase> getRelations() {
		return relations;
	}
	
	public void setRelations(Map<Sign, RefBase> relations) {
		this.relations = relations;
	}
	
	@SuppressWarnings("unchecked")
	public <T extends EntityObject> T getEntity(Sign sign) {
		return (T)this.relations.get(sign).get();
	}
	
	@SuppressWarnings("unchecked")
	public <T extends EntityObject> Collection<T> getEntities(Sign sign) {
		return (Collection<T>)this.relations.get(sign).collection();
	}
	
	public Object getUserFields(String name) {
		if (!this.userFields.containsKey(name)) {
			throw new ExistedException("user field`s name: " + name + " is not existed");
		}
		return this.userFields.get(name);
	}
	
	public void setUserFields(String name, Object value) {
		if (!this.userFields.containsKey(name)) {
			throw new ExistedException("user field`s name: " + name + " is not existed");
		}
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
	
	public CreateUpdateType getCreateType() {
		return createType;
	}
	
	public void setCreateType(CreateUpdateType createType) {
		this.createType = createType;
	}
	
	public CreateUpdateType getUpdateType() {
		return updateType;
	}
	
	public void setUpdateType(CreateUpdateType updateType) {
		this.updateType = updateType;
	}

	@Override
	public int compareTo(EntityObject o) {
		if (this.getId() == o.getId()) {
			return 0;
		}
		return this.getId() > o.getId() ? -1 : 1;
	}
}
