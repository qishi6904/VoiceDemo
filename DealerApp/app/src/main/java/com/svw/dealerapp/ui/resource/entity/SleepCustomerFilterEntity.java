package com.svw.dealerapp.ui.resource.entity;

/**
 * Created by qinshi on 5/24/2017.
 */

public class SleepCustomerFilterEntity extends BaseFilterEntity{

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

    private String dormancyTimeFrom;
    private String dormancyTimeTo;
    private String suspendTimeFrom;
    private String suspendTimeTo;
    private String org_id;
//    private String followupResult;

//    public DealCustomerFilterEntity(String followupResult){
//        this.followupResult = followupResult;
//    }

    public String getDormancyTimeFrom() {
        return dormancyTimeFrom;
    }

    public void setDormancyTimeFrom(String dormancyTimeFrom) {
        this.dormancyTimeFrom = dormancyTimeFrom;
    }

    public String getSuspendTimeFrom() {
        return suspendTimeFrom;
    }

    public void setSuspendTimeFrom(String suspendTimeFrom) {
        this.suspendTimeFrom = suspendTimeFrom;
    }

    public String getSuspendTimeTo() {
        return suspendTimeTo;
    }

    public void setSuspendTimeTo(String suspendTimeTo) {
        this.suspendTimeTo = suspendTimeTo;
    }

    public String getDormancyTimeTo() {
        return dormancyTimeTo;
    }

    public void setDormancyTimeTo(String dormancyTimeTo) {
        this.dormancyTimeTo = dormancyTimeTo;
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
