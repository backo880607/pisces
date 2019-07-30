package com.pisces.core.entity;

import java.lang.reflect.Method;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pisces.core.annotation.PropertyMeta;
import com.pisces.core.enums.EditType;
import com.pisces.core.enums.PropertyType;
import com.pisces.core.relation.Sign;

public class Property extends EntityCoding {
	private String belongName;	// 属性所属类
	private PropertyType type;	// 属性类型
	private EditType editType;	// 属性的编辑类型
	private Boolean inherent;	// 是否是内部固有属性
	private Boolean modifiable;	// 是否可以修改
	private Boolean visiable;	// 由用户控制是否显示
	private Boolean display;	// 是否能够在界面上显示
	private Short preci;		// 对于double类型控制显示精度
	private String tips;		// 属性的提示信息
	private String expression;	// 属性取值表达式
	private String displayName;	// 页面显示名称
	private Boolean primaryKey;	// 是否为主键字段，自定义字段不能作为主键
	
	@JsonIgnore
	@PropertyMeta(internal=true)
	public transient Class<? extends EntityObject> belongClazz;
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
		inherent = true;
		modifiable = true;
		visiable = true;
		display = true;
		preci = 7;
		tips = "";
		expression = "";
		displayName = "";
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

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public Boolean getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(Boolean primaryKey) {
		this.primaryKey = primaryKey;
	}
}
