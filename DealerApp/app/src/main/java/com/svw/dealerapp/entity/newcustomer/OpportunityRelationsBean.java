package com.svw.dealerapp.entity.newcustomer;

import java.io.Serializable;

/**
 * Created by xupan on 18/07/2017.
 */

public class OpportunityRelationsBean implements Serializable {

    /**
     * relaId : 10012
     * relaDesc : lflsfldsfjdsl
     * relaFlag : 0
     * remark : sdfdsfsdfdsfsdfdsf
     */

    private String relaId;
    private String relaDesc;
    private String relaFlag;
    private String remark;

    public String getRelaId() {
        return relaId;
    }

    public void setRelaId(String relaId) {
        this.relaId = relaId;
    }

    public String getRelaDesc() {
        return relaDesc;
    }

    public void setRelaDesc(String relaDesc) {
        this.relaDesc = relaDesc;
    }

    public String getRelaFlag() {
        return relaFlag;
    }

    public void setRelaFlag(String relaFlag) {
        this.relaFlag = relaFlag;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "OpportunityRelationsBean{" +
                "relaId='" + relaId + '\'' +
                ", relaDesc='" + relaDesc + '\'' +
                ", relaFlag='" + relaFlag + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
