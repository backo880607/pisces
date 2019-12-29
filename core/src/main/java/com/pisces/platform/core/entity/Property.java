package com.pisces.platform.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pisces.platform.core.annotation.PrimaryKey;
import com.pisces.platform.core.annotation.PropertyMeta;
import com.pisces.platform.core.enums.PROPERTY_TYPE;
import com.pisces.platform.core.relation.Sign;

import javax.persistence.Table;
import java.io.Serializable;
import java.lang.reflect.Method;

@Table(name = "PROPERTY")
@PrimaryKey(fields = {"belongName"})
public class Property extends EntityCoding implements Serializable {
    private static final long serialVersionUID = -7594283934306849106L;

    private String belongName;    // 属性所属类
    private PROPERTY_TYPE type;    // 属性类型
    private String typeName;    // 属性类型名称，包含包路径
    private Boolean large;        // 是否为长度超大的数据类型
    private Boolean inherent;    // 是否内部固有属性
    private Boolean isUnique;    // 是否唯一
    private Boolean modifiable;    // 是否可以修改
    private Boolean display;    // 默认是否在界面显示
    private Short preci;        // 对于double类型控制显示精度
    private String tips;        // 属性的提示信息
    private String expression;    // 属性取值表达式
    private Boolean primaryKey;    // 是否为主键字段，自定义字段不能作为主键
    private transient Enum<?>[] enumItems;

    @JsonIgnore
    @PropertyMeta(visible = false)
    public transient Class<? extends EntityObject> belongClazz;
    @JsonIgnore
    @PropertyMeta(visible = false)
    public transient Class<?> clazz;
    @JsonIgnore
    @PropertyMeta(visible = false)
    public transient Sign sign;
    @JsonIgnore
    @PropertyMeta(visible = false)
    public transient Method getMethod;
    @JsonIgnore
    @PropertyMeta(visible = false)
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
        display = true;
        preci = 2;
        tips = "";
        expression = "";
        primaryKey = false;
    }

    public String getBelongName() {
        return belongName;
    }

    public void setBelongName(String value) {
        this.belongName = value;
    }

    public PROPERTY_TYPE getType() {
        return type;
    }

    public void setType(PROPERTY_TYPE value) {
        this.type = value;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String value) {
        this.typeName = value;
    }

    public Boolean getLarge() {
        return large;
    }

    public void setLarge(Boolean value) {
        this.large = value;
    }

    public Boolean getInherent() {
        return inherent;
    }

    public void setInherent(Boolean value) {
        this.inherent = value;
    }

    public Boolean getIsUnique() {
        return isUnique;
    }

    public void setIsUnique(Boolean value) {
        this.isUnique = value;
    }

    public Boolean getModifiable() {
        return modifiable;
    }

    public void setModifiable(Boolean value) {
        this.modifiable = value;
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

    public void setPreci(Short value) {
        this.preci = value;
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

    public void setExpression(String value) {
        this.expression = value;
    }

    public Boolean getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(Boolean value) {
        this.primaryKey = value;
    }

    public Enum<?>[] getEnumItems() {
        return enumItems;
    }

    public void setEnumItems(Enum<?>[] enumItems) {
        this.enumItems = enumItems;
    }
}
