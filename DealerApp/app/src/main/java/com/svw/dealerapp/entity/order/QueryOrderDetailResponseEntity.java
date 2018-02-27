package com.svw.dealerapp.entity.order;


import com.svw.dealerapp.entity.optionalpackage.OptionalPackageEntity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by xupan on 30/08/2017.
 */

public class QueryOrderDetailResponseEntity implements Serializable {

    /**
     * orderId : 643ab875-7b15-4533-ab60-274ab0945c0e
     * custName : Syringa
     * custGender : 1
     * custBirthday : null
     * custType : null
     * custMobile : 15825730797
     * custTelephone : null
     * custId : null
     * oppId : 3cc5cdc6-fe8f-4722-8c25-e4694d313ca0
     * salesConsultant : null
     * orderDate : null
     * channelId :
     * srcTypeId : 11010
     * orderType : null
     * orderStatus : 0
     * orgId : 2010100
     * provinceId : 150000
     * cityId : 150100
     * countyId : null
     * custEmail :
     * custDuty : null
     * custIndustry : null
     * corpNature : null
     * corpName : null
     * certType : 11010
     * certNum :
     * custAddress :
     * custPostcode : null
     * seriesId : 3E92
     * modelId : 3e92fz
     * outsideColorId :
     * insideColorId :
     * modelPrice : null
     * modelDiscount : null
     * currentMoney : null
     * itemMoney : null
     * totalMoney : null
     * paymentChannel : null
     * paymentRate : null
     * paymentMoney : null
     * remainderMoney : null
     * paymentCycle : null
     * deliveryDate : null
     * carOwnerName : null
     * carOwnerMobile : null
     * carOwnerVin : null
     * carOwnerId : null
     * carOwnerCard : null
     * createUser : 10150
     * createTime : null
     * updateTime : null
     * remark : null
     * isFirst : null
     * cancelDesc : null
     * carOwnerTel : null
     * recomName : null
     * recomMobile : null
     * recomVin : null
     * recomCard : null
     * carServiceCode : null
     * isDelivery : null
     * isHq : null
     * ecCarOptions : null
     */

    private String custName;
    private String custGender;
    private String custBirthday;
    private String custType;
    private String custMobile;
    private String custTelephone;
    private String custId;
    private String oppId;
    private String salesConsultant;
    private String orderDate;
    private String channelId;
    private String srcTypeId;
    private String orderType;
    private String orgId;
    private String provinceId;
    private String cityId;
    private String countyId;
    private String custEmail;
    private String custDuty;
    private String custIndustry;
    private String corpNature;
    private String corpName;
    private String certType;
    private String certNum;
    private String custAddress;
    private String custPostcode;
    private String seriesId;
    private String modelId;
    private String outsideColorId;
    private String insideColorId;
    private String modelPrice;
    private String modelDiscount;
    private String currentMoney;
    private String itemMoney;
    private String totalMoney;
    private String paymentChannel;
    private String paymentRate;
    private String paymentMoney;
    private String remainderMoney;
    private String paymentCycle;
    private String deliveryDate;
    private String carOwnerName;
    private String carOwnerMobile;
    private String carOwnerVin;
    private String carOwnerId;
    private String carOwnerCard;
    private String createUser;
    private String createTime;
    private String updateTime;
    private String remark;
    private String isFirst;
    private String cancelDesc;
    private String carOwnerTel;
    private String recomName;
    private String recomMobile;
    private String recomVin;
    private String recomCard;
    private String carServiceCode;
    private String isDelivery;
    private String isHq;
    private String isEcOtdOrder;
    private boolean buyerInfoEditable;
    private String orderStatus;//当前订单状态
    private String isOtdOrder;//当前订单是否为otd 0：是 1：否
    private String orderId;//当前orderId

    private ArrayList<OptionalPackageEntity.OptionListBean> ecCarOptions;//选装包

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustGender() {
        return custGender;
    }

    public void setCustGender(String custGender) {
        this.custGender = custGender;
    }

    public String getCustBirthday() {
        return custBirthday;
    }

    public void setCustBirthday(String custBirthday) {
        this.custBirthday = custBirthday;
    }

    public String getCustType() {
        return custType;
    }

    public void setCustType(String custType) {
        this.custType = custType;
    }

    public String getCustMobile() {
        return custMobile;
    }

    public void setCustMobile(String custMobile) {
        this.custMobile = custMobile;
    }

    public String getCustTelephone() {
        return custTelephone;
    }

    public void setCustTelephone(String custTelephone) {
        this.custTelephone = custTelephone;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getOppId() {
        return oppId;
    }

    public void setOppId(String oppId) {
        this.oppId = oppId;
    }

    public String getSalesConsultant() {
        return salesConsultant;
    }

    public void setSalesConsultant(String salesConsultant) {
        this.salesConsultant = salesConsultant;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getSrcTypeId() {
        return srcTypeId;
    }

    public void setSrcTypeId(String srcTypeId) {
        this.srcTypeId = srcTypeId;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getIsOtdOrder() {
        return isOtdOrder;
    }

    public void setIsOtdOrder(String isOtdOrder) {
        this.isOtdOrder = isOtdOrder;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCountyId() {
        return countyId;
    }

    public void setCountyId(String countyId) {
        this.countyId = countyId;
    }

    public String getCustEmail() {
        return custEmail;
    }

    public void setCustEmail(String custEmail) {
        this.custEmail = custEmail;
    }

    public String getCustDuty() {
        return custDuty;
    }

    public void setCustDuty(String custDuty) {
        this.custDuty = custDuty;
    }

    public String getCustIndustry() {
        return custIndustry;
    }

    public void setCustIndustry(String custIndustry) {
        this.custIndustry = custIndustry;
    }

    public String getCorpNature() {
        return corpNature;
    }

    public void setCorpNature(String corpNature) {
        this.corpNature = corpNature;
    }

    public String getCorpName() {
        return corpName;
    }

    public void setCorpName(String corpName) {
        this.corpName = corpName;
    }

    public String getCertType() {
        return certType;
    }

    public void setCertType(String certType) {
        this.certType = certType;
    }

    public String getCertNum() {
        return certNum;
    }

    public void setCertNum(String certNum) {
        this.certNum = certNum;
    }

    public String getCustAddress() {
        return custAddress;
    }

    public void setCustAddress(String custAddress) {
        this.custAddress = custAddress;
    }

    public String getCustPostcode() {
        return custPostcode;
    }

    public void setCustPostcode(String custPostcode) {
        this.custPostcode = custPostcode;
    }

    public String getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(String seriesId) {
        this.seriesId = seriesId;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getOutsideColorId() {
        return outsideColorId;
    }

    public void setOutsideColorId(String outsideColorId) {
        this.outsideColorId = outsideColorId;
    }

    public String getInsideColorId() {
        return insideColorId;
    }

    public void setInsideColorId(String insideColorId) {
        this.insideColorId = insideColorId;
    }

    public String getModelPrice() {
        return modelPrice;
    }

    public void setModelPrice(String modelPrice) {
        this.modelPrice = modelPrice;
    }

    public String getModelDiscount() {
        return modelDiscount;
    }

    public void setModelDiscount(String modelDiscount) {
        this.modelDiscount = modelDiscount;
    }

    public String getCurrentMoney() {
        return currentMoney;
    }

    public void setCurrentMoney(String currentMoney) {
        this.currentMoney = currentMoney;
    }

    public String getItemMoney() {
        return itemMoney;
    }

    public void setItemMoney(String itemMoney) {
        this.itemMoney = itemMoney;
    }

    public String getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(String totalMoney) {
        this.totalMoney = totalMoney;
    }

    public String getPaymentChannel() {
        return paymentChannel;
    }

    public void setPaymentChannel(String paymentChannel) {
        this.paymentChannel = paymentChannel;
    }

    public String getPaymentRate() {
        return paymentRate;
    }

    public void setPaymentRate(String paymentRate) {
        this.paymentRate = paymentRate;
    }

    public String getPaymentMoney() {
        return paymentMoney;
    }

    public void setPaymentMoney(String paymentMoney) {
        this.paymentMoney = paymentMoney;
    }

    public String getRemainderMoney() {
        return remainderMoney;
    }

    public void setRemainderMoney(String remainderMoney) {
        this.remainderMoney = remainderMoney;
    }

    public String getPaymentCycle() {
        return paymentCycle;
    }

    public void setPaymentCycle(String paymentCycle) {
        this.paymentCycle = paymentCycle;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getCarOwnerName() {
        return carOwnerName;
    }

    public void setCarOwnerName(String carOwnerName) {
        this.carOwnerName = carOwnerName;
    }

    public String getCarOwnerMobile() {
        return carOwnerMobile;
    }

    public void setCarOwnerMobile(String carOwnerMobile) {
        this.carOwnerMobile = carOwnerMobile;
    }

    public String getCarOwnerVin() {
        return carOwnerVin;
    }

    public void setCarOwnerVin(String carOwnerVin) {
        this.carOwnerVin = carOwnerVin;
    }

    public String getCarOwnerId() {
        return carOwnerId;
    }

    public void setCarOwnerId(String carOwnerId) {
        this.carOwnerId = carOwnerId;
    }

    public String getCarOwnerCard() {
        return carOwnerCard;
    }

    public void setCarOwnerCard(String carOwnerCard) {
        this.carOwnerCard = carOwnerCard;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getIsFirst() {
        return isFirst;
    }

    public void setIsFirst(String isFirst) {
        this.isFirst = isFirst;
    }

    public String getCancelDesc() {
        return cancelDesc;
    }

    public void setCancelDesc(String cancelDesc) {
        this.cancelDesc = cancelDesc;
    }

    public String getCarOwnerTel() {
        return carOwnerTel;
    }

    public void setCarOwnerTel(String carOwnerTel) {
        this.carOwnerTel = carOwnerTel;
    }

    public String getRecomName() {
        return recomName;
    }

    public void setRecomName(String recomName) {
        this.recomName = recomName;
    }

    public String getRecomMobile() {
        return recomMobile;
    }

    public void setRecomMobile(String recomMobile) {
        this.recomMobile = recomMobile;
    }

    public String getRecomVin() {
        return recomVin;
    }

    public void setRecomVin(String recomVin) {
        this.recomVin = recomVin;
    }

    public String getRecomCard() {
        return recomCard;
    }

    public void setRecomCard(String recomCard) {
        this.recomCard = recomCard;
    }

    public String getCarServiceCode() {
        return carServiceCode;
    }

    public void setCarServiceCode(String carServiceCode) {
        this.carServiceCode = carServiceCode;
    }

    public String getIsDelivery() {
        return isDelivery;
    }

    public void setIsDelivery(String isDelivery) {
        this.isDelivery = isDelivery;
    }

    public String getIsHq() {
        return isHq;
    }

    public void setIsHq(String isHq) {
        this.isHq = isHq;
    }

    public String getIsEcOtdOrder() {
        return isEcOtdOrder;
    }

    public void setIsEcOtdOrder(String isEcOtdOrder) {
        this.isEcOtdOrder = isEcOtdOrder;
    }

    public boolean isBuyerInfoEditable() {
        return buyerInfoEditable;
    }

    public void setBuyerInfoEditable(boolean buyerInfoEditable) {
        this.buyerInfoEditable = buyerInfoEditable;
    }

    public ArrayList<OptionalPackageEntity.OptionListBean> getEcCarOptions() {
        return ecCarOptions;
    }

    public void setEcCarOptions(ArrayList<OptionalPackageEntity.OptionListBean> ecCarOptions) {
        this.ecCarOptions = ecCarOptions;
    }
}
