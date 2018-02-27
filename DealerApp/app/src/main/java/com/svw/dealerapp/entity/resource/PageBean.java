package com.svw.dealerapp.entity.resource;

/**
 * Created by qinshi on 5/22/2017.
 */

public class PageBean {

    /**
     * totalCount : 99
     * pageIndex : 1
     * pageSize : 5
     */

    private int totalCount;
    private int pageIndex;
    private int pageSize;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
