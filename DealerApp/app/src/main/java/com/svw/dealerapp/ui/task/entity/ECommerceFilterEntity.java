package com.svw.dealerapp.ui.task.entity;

/**
 * Created by qinshi on 6/6/2017.
 */

public class ECommerceFilterEntity {

    /**
     * userId : XXXX
     * taskTypeId : 16020
     * srcTypeId : 11020
     */

    private String userId;
    private String taskTypeId;
    private String srcTypeId;
    private String isHomePage;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTaskTypeId() {
        return taskTypeId;
    }

    public void setTaskTypeId(String taskTypeId) {
        this.taskTypeId = taskTypeId;
    }

    public String getSrcTypeId() {
        return srcTypeId;
    }

    public void setSrcTypeId(String srcTypeId) {
        this.srcTypeId = srcTypeId;
    }

    public String getIsHomePage() {
        return isHomePage;
    }

    public void setIsHomePage(String isHomePage) {
        this.isHomePage = isHomePage;
    }
}
