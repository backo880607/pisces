package com.pisces.platform.core.entity;

import com.pisces.platform.core.annotation.PrimaryKey;

@PrimaryKey(fields = {"code"})
public class EntityCoding extends EntityObject {
    private String code;
    private String name;
    private String remarks;

    @Override
    public void init() {
        super.init();
        code = "";
        name = "";
        remarks = "";
    }

    public String getCode() {
        return code;
    }

    public void setCode(String value) {
        this.code = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String value) {
        this.remarks = value;
    }

}