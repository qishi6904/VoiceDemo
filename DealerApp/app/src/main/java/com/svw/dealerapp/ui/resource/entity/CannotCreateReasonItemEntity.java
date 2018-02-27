package com.svw.dealerapp.ui.resource.entity;

/**
 * Created by qinshi on 5/15/2017.
 */

public class CannotCreateReasonItemEntity {

    private String info;
    private String detail;

    public CannotCreateReasonItemEntity(){

    }

    public CannotCreateReasonItemEntity(String info, String detail) {
        this.info = info;
        this.detail = detail;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
