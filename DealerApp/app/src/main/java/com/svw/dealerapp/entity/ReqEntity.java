package com.svw.dealerapp.entity;

import com.svw.dealerapp.DealerApp;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lijinkui on 2017/6/1.
 */

public class ReqEntity {

    /**
     * appId : ea185595-4b35-4bf1-b8da-2f6036b12667
     * appType : 101
     * seqNo : ea185595-4b35-4bf1-b8da-2f6036b12667
     * reqTime : 2016-09-02 10:11:22.231
     */

    private String appId;
    private String appType;
    private String seqNo;
    private String reqTime;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public String getReqTime() {
        return reqTime;
    }

    public void setReqTime(String reqTime) {
        this.reqTime = reqTime;
    }

    public ReqEntity(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ");
        String currentDate = simpleDateFormat.format(new Date());

        this.setAppId("com.svw.dealerapp");
        this.setAppType("101");
        this.setSeqNo("ea185595-4b35-4bf1-b8da-2f6036b12667");
        this.setReqTime(currentDate);
    }
}
