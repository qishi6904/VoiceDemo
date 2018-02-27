package com.svw.dealerapp.entity;

/**
 * Created by lijinkui on 2017/5/9.
 */

public class ResEntity<T> {

    /**
     * retCode : 001001
     * retMessage : ID不存在
     * retTime : 2016-09-02 10:11:23.201
     */

    private String retCode;
    private String retMessage;
    private String retTime;
    private T retData;

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public String getRetMessage() {
        return retMessage;
    }

    public void setRetMessage(String retMessage) {
        this.retMessage = retMessage;
    }

    public String getRetTime() {
        return retTime;
    }

    public void setRetTime(String retTime) {
        this.retTime = retTime;
    }

    public T getRetData() {
        return retData;
    }

    public void setRetData(T retData) {
        this.retData = retData;
    }

    @Override
    public String toString() {
        return "ResEntity{" +
                "retCode='" + retCode + '\'' +
                ", retMessage='" + retMessage + '\'' +
                ", retTime='" + retTime + '\'' +
                ", retData=" + retData +
                '}';
    }
}
