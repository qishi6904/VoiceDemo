package com.svw.dealerapp.ui.resource.entity;

/**
 * Created by qinshi on 5/24/2017.
 */

public class YellowCardFilterEntity extends BaseFilterEntity {

    /**
     * carModelId : 3E92FZ
     * createTimeFrom : 2017-05-17T12:00:00.000+0800
     * createTimeTo : 2017-05-17T24:00:00.000+0800
     * oppStatus : 15510,15520,15530
     * isKeyuser : 1
     * oppLevel : A,B,C
     * org_id : XXXX
     * srcTypeId : 11010,11020,11030
     * userId : XXXX
     */

    private String oppStatus;
    private String isKeyuser;
    private String oppLevel;
    private String org_id;
    private String custMobile;
    private String needFollowup;

    public String getOppStatus() {
        return oppStatus;
    }

    public void setOppStatus(String oppStatus) {
        this.oppStatus = oppStatus;
    }

    public String getIsKeyuser() {
        return isKeyuser;
    }

    public void setIsKeyuser(String isKeyuser) {
        this.isKeyuser = isKeyuser;
    }

    public String getOppLevel() {
        return oppLevel;
    }

    public void setOppLevel(String oppLevel) {
        this.oppLevel = oppLevel;
    }

    public String getOrg_id() {
        return org_id;
    }

    public void setOrg_id(String org_id) {
        this.org_id = org_id;
    }


    public String getCustMobile() {
        return custMobile;
    }

    public void setCustMobile(String custMobile) {
        this.custMobile = custMobile;
    }

    public String getNeedFollowup() {
        return needFollowup;
    }

    public void setNeedFollowup(String needFollowup) {
        this.needFollowup = needFollowup;
    }

}
