package com.pisces.platform.integration.helper;

import com.pisces.platform.core.entity.EntityMapper;
import com.pisces.platform.core.utils.EntityUtils;

public class DataConfig {
    private String sepField;
    private String sepEntity;
    private String replaceField = "▲";
    private String replaceEntity = "◆";
    private EntityMapper mapper = EntityUtils.createEntityMapper();

    public String getSepField() {
        return sepField;
    }

    public void setSepField(String sepField) {
        this.sepField = sepField;
    }

    public String getSepEntity() {
        return sepEntity;
    }

    public void setSepEntity(String sepEntity) {
        this.sepEntity = sepEntity;
    }

    public String getReplaceField() {
        return replaceField;
    }

    public void setReplaceField(String replaceField) {
        this.replaceField = replaceField;
    }

    public String getReplaceEntity() {
        return replaceEntity;
    }

    public void setReplaceEntity(String replaceEntity) {
        this.replaceEntity = replaceEntity;
    }

    public EntityMapper getMapper() {
        return mapper;
    }

    public void setMapper(EntityMapper mapper) {
        this.mapper = mapper;
    }
}
