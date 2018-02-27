package com.svw.dealerapp.entity.order;

import com.svw.dealerapp.entity.ReqEntity;

/**
 * Created by xupan on 19/09/2017.
 */

public class CheckMembershipReqEntity extends ReqEntity {

    private String queryType;
    private String customerName;
    private String customerMobile;
    private String vin;
    private String cardNo;

    public String getQueryType() {
        return queryType;
    }

    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerMobile() {
        return customerMobile;
    }

    public void setCustomerMobile(String customerMobile) {
        this.customerMobile = customerMobile;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }
}
