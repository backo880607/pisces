package com.pisces.platform.core.utils;

public class PageParam {
    private int pageNum;
    private int pageSize;
    private String orderBy;
    private String filter;

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int value) {
        this.pageNum = value;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int value) {
        this.pageSize = value;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String value) {
        this.orderBy = value;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String value) {
        this.filter = value;
    }
}
