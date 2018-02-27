package com.svw.dealerapp.ui.resource.entity;

/**
 * Created by qinshi on 12/19/2017.
 */

public class BaseFilterEntity {

    protected String showSelf;
    protected String srcTypeId;
    protected String seriesId;
    protected String carModelId;
    protected String userId;
    protected String createTimeFrom;
    protected String createTimeTo;

    public String getShowSelf() {
        return showSelf;
    }

    public void setShowSelf(String showSelf) {
        this.showSelf = showSelf;
    }

    public String getSrcTypeId() {
        return srcTypeId;
    }

    public void setSrcTypeId(String srcTypeId) {
        this.srcTypeId = srcTypeId;
    }

    public String getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(String seriesId) {
        this.seriesId = seriesId;
    }

    public String getCarModelId() {
        return carModelId;
    }

    public void setCarModelId(String carModelId) {
        this.carModelId = carModelId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCreateTimeFrom() {
        return createTimeFrom;
    }

    public void setCreateTimeFrom(String createTimeFrom) {
        this.createTimeFrom = createTimeFrom;
    }

    public String getCreateTimeTo() {
        return createTimeTo;
    }

    public void setCreateTimeTo(String createTimeTo) {
        this.createTimeTo = createTimeTo;
    }
}
