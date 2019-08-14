package com.pisces.core.entity;

import java.io.Serializable;
import java.lang.reflect.Method;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pisces.core.annotation.PrimaryKey;
import com.pisces.core.annotation.PropertyMeta;
import com.pisces.core.enums.EditType;
import com.pisces.core.enums.PropertyType;
import com.pisces.core.relation.Sign;
import com.pisces.core.validator.InsertGroup;

@PrimaryKey(fields={"belongName"}, groups = {InsertGroup.class})
public class Property extends EntityCoding implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7594283934306849106L;
	
	private String belongName;	// 属性所属类
	private PropertyType type;	// 属性类型
	private String typeName;	// 属性类型名称，包含包路径
	private EditType editType;	// 属性的编辑类型
	private Boolean inherent = false;	// 是否是内部固有属性
	private Boolean modifiable;	// 是否可以修改
	private Boolean visiable;	// 由用户控制是否显示
	private Boolean display;	// 是否能够在界面上显示
	private Short preci;		// 对于double类型控制显示精度
	private String tips;		// 属性的提示信息
	private String expression;	// 属性取值表达式
	private Boolean primaryKey;	// 是否为主键字段，自定义字段不能作为主键
	
	@JsonIgnore
	@PropertyMeta(internal=true)
	public transient Class<? extends EntityObject> belongClazz;
	@JsonIgnore
	@PropertyMeta(internal=true)
	public transient Class<?> clazz;
	@JsonIgnore
	@PropertyMeta(internal=true)
	public transient Sign sign;
	@JsonIgnore
	@PropertyMeta(internal=true)
	public transient Method getMethod;
	@JsonIgnore
	@PropertyMeta(internal=true)
	public transient Method setMethod;
	
	@Override
	public void init() {
		super.init();
		belongName = "";
		type = PropertyType.Integer;
		editType = EditType.TEXT;
		modifiable = true;
		visiable = true;
		display = true;
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
	
	public PropertyType getType() {
		return type;
	}
	
	public void setType(PropertyType type) {
		this.type = type;
	}
	
	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public EditType getEditType() {
		return editType;
	}
	
	public void setEditType(EditType editType) {
		this.editType = editType;
	}
	
	public Boolean getInherent() {
		return inherent;
	}

	public void setInherent(Boolean inherent) {
		this.inherent = inherent;
	}
	
	public Boolean getModifiable() {
		return modifiable;
	}
	
	public void setModifiable(Boolean modifiable) {
		this.modifiable = modifiable;
	}
	
	public Boolean getVisiable() {
		return visiable;
	}
	
	public void setVisiable(Boolean visiable) {
		this.visiable = visiable;
	}

	public Boolean getDisplay() {
		return display;
	}

	public void setDisplay(Boolean display) {
		this.display = display;
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
