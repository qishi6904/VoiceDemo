package com.svw.dealerapp.ui.resource.entity;

/**
 * Created by qinshi on 5/24/2017.
 */

public class FailedCustomerFilterEntity extends BaseFilterEntity{

    /**
     * carModelId : 3E92FZ
     * createTimeFrom : 2017-05-17T12:00:00.000+0800
     * createTimeTo : 2017-05-17T24:00:00.000+0800
     * followupResult : 15510,15520,15530
     * isKeyuser : 1
     * oppLevel : A,B,C
     * org_id : XXXX
     * srcTypeId : 11010,11020,11030
     * userId : XXXX
     */

    private String failureTimeFrom;
    private String failureTimeTo;
    private String org_id;
    private String failureDesc;
//    private String followupResult;

//    public DealCustomerFilterEntity(String followupResult){
//        this.followupResult = followupResult;
//    }

    public String getFailureTimeFrom() {
        return failureTimeFrom;
    }

    public void setFailureTimeFrom(String failureTimeFrom) {
        this.failureTimeFrom = failureTimeFrom;
    }

    public String getFailureTimeTo() {
        return failureTimeTo;
    }

    public void setFailureTimeTo(String failureTimeTo) {
        this.failureTimeTo = failureTimeTo;
    }

    public String getFailureDesc() {
        return failureDesc;
    }

    public void setFailureDesc(String failureDesc) {
        this.failureDesc = failureDesc;
    }

    public String getOrg_id() {
        return org_id;
    }

    public void setOrg_id(String org_id) {
        this.org_id = org_id;
    }

    //    public String getOppStatus() {
//        return followupResult;
//    }
//
//    public void setOppStatus(String followupResult) {
//        this.followupResult = followupResult;
//    }
}
