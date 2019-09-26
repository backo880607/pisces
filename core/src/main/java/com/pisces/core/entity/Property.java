package com.pisces.core.entity;

import java.io.Serializable;
import java.lang.reflect.Method;

import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pisces.core.annotation.PrimaryKey;
import com.pisces.core.annotation.PropertyMeta;
import com.pisces.core.enums.PROPERTY_TYPE;
import com.pisces.core.relation.Sign;
import com.pisces.core.validator.InsertGroup;

@Table(name = "PROPERTY")
@PrimaryKey(fields={"belongName"}, groups = {InsertGroup.class})
public class Property extends EntityCoding implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7594283934306849106L;
	
	private String belongName;	// 属性所属类
	private PROPERTY_TYPE type;	// 属性类型
	private String typeName;	// 属性类型名称，包含包路径
	private boolean large;		// 是否为长度超大的数据类型
	private Boolean inherent;	// 是否内部固有属性
	private Boolean isUnique;	// 是否唯一
	private Boolean modifiable;	// 是否可以修改
	private Short preci;		// 对于double类型控制显示精度
	private String tips;		// 属性的提示信息
	private String expression;	// 属性取值表达式
	private Boolean primaryKey;	// 是否为主键字段，自定义字段不能作为主键
	
	@JsonIgnore
	@PropertyMeta(visiable = false)
	public transient Class<? extends EntityObject> belongClazz;
	@JsonIgnore
	@PropertyMeta(visiable = false)
	public transient Class<?> clazz;
	@JsonIgnore
	@PropertyMeta(visiable = false)
	public transient Sign sign;
	@JsonIgnore
	@PropertyMeta(visiable = false)
	public transient Method getMethod;
	@JsonIgnore
	@PropertyMeta(visiable = false)
	public transient Method setMethod;
	
	@Override
	public void init() {
		super.init();
		belongName = "";
		type = PROPERTY_TYPE.LONG;
		typeName = "";
		large = false;
		inherent = false;
		isUnique = false;
		modifiable = true;
		preci = 7;
		tips = "";
		expression = "";
		primaryKey = false;
	}
	
	public String getBelongName() {
		return belongName;
	}

	public void setBelongName(String belongName) {
		this.belongName = belongName;
	}
	
	public PROPERTY_TYPE getType() {
		return type;
	}
	
	public void setType(PROPERTY_TYPE type) {
		this.type = type;
	}
	
	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	public boolean getLarge() {
		return large;
	}

	public void setLarge(boolean large) {
		this.large = large;
	}
	
	public Boolean getInherent() {
		return inherent;
	}

	public void setInherent(Boolean inherent) {
		this.inherent = inherent;
	}
	
	public Boolean getIsUnique() {
		return isUnique;
	}

	public void setIsUnique(Boolean isUnique) {
		this.isUnique = isUnique;
	}
	
	public Boolean getModifiable() {
		return modifiable;
	}
	
	public void setModifiable(Boolean modifiable) {
		this.modifiable = modifiable;
	}
	
	public Short getPreci() {
		return preci;
	}

	public void setPreci(Short preci) {
		this.preci = preci;
	}

	public String getTips() {
		return tips;
	}

	public void setTips(String tips) {
		this.tips = tips;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public Boolean getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(Boolean primaryKey) {
		this.primaryKey = primaryKey;
	}
}
