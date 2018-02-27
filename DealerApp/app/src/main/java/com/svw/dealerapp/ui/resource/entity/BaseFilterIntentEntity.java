package com.svw.dealerapp.ui.resource.entity;

import java.util.Date;
import java.util.List;

/**
 * Created by qinshi on 12/19/2017.
 */

public class BaseFilterIntentEntity {
    protected List<ResourceFilterItemEntity> sourceSelectList;
    protected boolean isShowSelf = false;
    protected String carCode;
    protected String carName;
    protected String userName;
    protected String userId;
    protected String seriesCode;
    protected String seriesName;
    protected Date createEndTimeDate;
    protected String createStartTime;
    protected boolean isCreateSelectAllTime = true;

    public List<ResourceFilterItemEntity> getSourceSelectList() {
        return sourceSelectList;
    }

    public void setSourceSelectList(List<ResourceFilterItemEntity> sourceSelectList) {
        this.sourceSelectList = sourceSelectList;
    }

    public boolean isShowSelf() {
        return isShowSelf;
    }

    public void setShowSelf(boolean showSelf) {
        this.isShowSelf = showSelf;
    }

    public String getCarCode() {
        return carCode;
    }

    public void setCarCode(String carCode) {
        this.carCode = carCode;
    }

    public String getCarName() {
        return carName;
    }

    public Date getCreateEndTimeDate() {
        return createEndTimeDate;
    }

    public void setCreateEndTimeDate(Date createEndTimeDate) {
        this.createEndTimeDate = createEndTimeDate;
    }

    public String getCreateStartTime() {
        return createStartTime;
    }

    public void setCreateStartTime(String createStartTime) {
        this.createStartTime = createStartTime;
    }

    public boolean isCreateSelectAllTime() {
        return isCreateSelectAllTime;
    }

    public void setCreateSelectAllTime(boolean createSelectAllTime) {
        isCreateSelectAllTime = createSelectAllTime;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSeriesCode() {
        return seriesCode;
    }

    public void setSeriesCode(String seriesCode) {
        this.seriesCode = seriesCode;
    }

    public String getSeriesName() {
        return seriesName;
    }

    public void setSeriesName(String seriesName) {
        this.seriesName = seriesName;
    }
}
