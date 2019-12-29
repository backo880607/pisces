package com.pisces.platform.core.validator;

import java.util.HashMap;
import java.util.Map;

public class ErrorInfo {
    private String clazz;
    private String field;
    private String message;
    private String value;
    private Map<String, String> entity = new HashMap<>();

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String value) {
        this.clazz = value;
    }

    public String getField() {
        return field;
    }

    public void setField(String value) {
        this.field = value;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String value) {
        this.message = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Map<String, String> getEntity() {
        return entity;
    }

    public void setEntity(Map<String, String> value) {
        this.entity = value;
    }
}
