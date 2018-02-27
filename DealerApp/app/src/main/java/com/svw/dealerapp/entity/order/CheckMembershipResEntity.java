package com.svw.dealerapp.entity.order;

/**
 * Created by xupan on 19/09/2017.
 */

public class CheckMembershipResEntity {
    private String cardId;
    private String customerName;
    private String customerMobile;
    private String vin;
    private String cardNo;
    private String cardKind;

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
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

    public String getCardKind() {
        return cardKind;
    }

    public void setCardKind(String cardKind) {
        this.cardKind = cardKind;
    }
}
