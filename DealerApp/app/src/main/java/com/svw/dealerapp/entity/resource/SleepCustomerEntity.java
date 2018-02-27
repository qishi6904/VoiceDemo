package com.svw.dealerapp.entity.resource;

import java.util.List;

/**
 * Created by qinshi on 9/25/2017.
 */

public class SleepCustomerEntity {

    private PageBean page;

    private List<SleepCustomerInfoEntity> data;

    public PageBean getPage() {
        return page;
    }

    public void setPage(PageBean page) {
        this.page = page;
    }

    public List<SleepCustomerInfoEntity> getData() {
        return data;
    }

    public void setData(List<SleepCustomerInfoEntity> data) {
        this.data = data;
    }

    public class SleepCustomerInfoEntity {

        /**
         * appmStatusId : 17520
         * appmStatusName : 已完成
         * appmTypeId : 17010
         * appmTypeName : 邀约到店
         * budgetMax : 350000
         * budgetMin : 250000
         * carModelDescCn : 朗逸 1.6L 自动品悠
         * carModelId : 181CA1
         * cityId : 150100
         * createTime : 2017-09-25T14:52:25.000+0800
         * createUser : 10080
         * currentMileage : 0
         * custAge : 10010
         * custAgeName : 25以下
         * custGender : 0
         * custMobile : 13212345678
         * custName : 顾客309
         * deliveryDate :
         * dormancyDate : 2017-12-27
         * dormancyDesc : 审批已通过
         * failureDate : Wed Sep 27 10:50:54 CST 2017
         * insideColorId : RV
         * insideColorNameCn : 米色内饰
         * leadsId : 19439cb2-beba-437e-babf-aa2691984047
         * oppId : 4abaae17-48dc-4415-b9bd-04cb49ecae63
         * oppLevel : 12010
         * oppLevelName : H
         * oppOwner : 10080
         * oppOwnerName : sales8
         * oppStatusId : 11540
         * oppStatusName : 休眠
         * optionPackage : 测试
         * orgId : 2010100
         * orgName : 内蒙古泰裕汽车服务有限公司
         * outsideColorId : B4B4
         * outsideColorNameCn : 糖果白
         * paymentMode : 12510
         * paymentModeName : 全款
         * propertyId : 13510
         * propertyName : 首次
         * provinceId : 150000
         * purchaseId : 13010
         * purchaseName : 私人
         * srcTypeId : 11010
         * srcTypeName : 到店
         * updateTime : 2017-09-27T10:51:11.000+0800
         */

        private String appmStatusId;
        private String appmStatusName;
        private String appmTypeId;
        private String appmTypeName;
        private int budgetMax;
        private int budgetMin;
        private String carModelDescCn;
        private String carModelId;
        private String cityId;
        private String createTime;
        private String createUser;
        private int currentMileage;
        private String custAge;
        private String custAgeName;
        private String custGender;
        private String custMobile;
        private String custName;
        private String deliveryDate;
        private String dormancyDate;
        private String dormancyDesc;
        private String failureDate;
        private String insideColorId;
        private String insideColorNameCn;
        private String leadsId;
        private String oppId;
        private String oppLevel;
        private String oppLevelName;
        private String oppOwner;
        private String oppOwnerName;
        private String oppStatusId;
        private String oppStatusName;
        private String optionPackage;
        private String orgId;
        private String orgName;
        private String outsideColorId;
        private String outsideColorNameCn;
        private String paymentMode;
        private String paymentModeName;
        private String propertyId;
        private String propertyName;
        private String provinceId;
        private String purchaseId;
        private String purchaseName;
        private String srcTypeId;
        private String srcTypeName;
        private String updateTime;
        private String imageUrl;
        private String custTelephone;
        private String isKeyuser;
        private String seriesName;

        public String getAppmStatusId() {
            return appmStatusId;
        }

        public void setAppmStatusId(String appmStatusId) {
            this.appmStatusId = appmStatusId;
        }

        public String getAppmStatusName() {
            return appmStatusName;
        }

        public void setAppmStatusName(String appmStatusName) {
            this.appmStatusName = appmStatusName;
        }

        public String getAppmTypeId() {
            return appmTypeId;
        }

        public void setAppmTypeId(String appmTypeId) {
            this.appmTypeId = appmTypeId;
        }

        public String getAppmTypeName() {
            return appmTypeName;
        }

        public void setAppmTypeName(String appmTypeName) {
            this.appmTypeName = appmTypeName;
        }

        public int getBudgetMax() {
            return budgetMax;
        }

        public void setBudgetMax(int budgetMax) {
            this.budgetMax = budgetMax;
        }

        public int getBudgetMin() {
            return budgetMin;
        }

        public void setBudgetMin(int budgetMin) {
            this.budgetMin = budgetMin;
        }

        public String getCarModelDescCn() {
            return carModelDescCn;
        }

        public void setCarModelDescCn(String carModelDescCn) {
            this.carModelDescCn = carModelDescCn;
        }

        public String getCarModelId() {
            return carModelId;
        }

        public void setCarModelId(String carModelId) {
            this.carModelId = carModelId;
        }

        public String getCityId() {
            return cityId;
        }

        public void setCityId(String cityId) {
            this.cityId = cityId;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getCreateUser() {
            return createUser;
        }

        public void setCreateUser(String createUser) {
            this.createUser = createUser;
        }

        public int getCurrentMileage() {
            return currentMileage;
        }

        public void setCurrentMileage(int currentMileage) {
            this.currentMileage = currentMileage;
        }

        public String getCustAge() {
            return custAge;
        }

        public void setCustAge(String custAge) {
            this.custAge = custAge;
        }

        public String getCustAgeName() {
            return custAgeName;
        }

        public void setCustAgeName(String custAgeName) {
            this.custAgeName = custAgeName;
        }

        public String getCustGender() {
            return custGender;
        }

        public void setCustGender(String custGender) {
            this.custGender = custGender;
        }

        public String getCustMobile() {
            return custMobile;
        }

        public void setCustMobile(String custMobile) {
            this.custMobile = custMobile;
        }

        public String getCustName() {
            return custName;
        }

        public void setCustName(String custName) {
            this.custName = custName;
        }

        public String getDeliveryDate() {
            return deliveryDate;
        }

        public void setDeliveryDate(String deliveryDate) {
            this.deliveryDate = deliveryDate;
        }

        public String getDormancyDate() {
            return dormancyDate;
        }

        public void setDormancyDate(String dormancyDate) {
            this.dormancyDate = dormancyDate;
        }

        public String getDormancyDesc() {
            return dormancyDesc;
        }

        public void setDormancyDesc(String dormancyDesc) {
            this.dormancyDesc = dormancyDesc;
        }

        public String getFailureDate() {
            return failureDate;
        }

        public void setFailureDate(String failureDate) {
            this.failureDate = failureDate;
        }

        public String getInsideColorId() {
            return insideColorId;
        }

        public void setInsideColorId(String insideColorId) {
            this.insideColorId = insideColorId;
        }

        public String getInsideColorNameCn() {
            return insideColorNameCn;
        }

        public void setInsideColorNameCn(String insideColorNameCn) {
            this.insideColorNameCn = insideColorNameCn;
        }

        public String getLeadsId() {
            return leadsId;
        }

        public void setLeadsId(String leadsId) {
            this.leadsId = leadsId;
        }

        public String getOppId() {
            return oppId;
        }

        public void setOppId(String oppId) {
            this.oppId = oppId;
        }

        public String getOppLevel() {
            return oppLevel;
        }

        public void setOppLevel(String oppLevel) {
            this.oppLevel = oppLevel;
        }

        public String getOppLevelName() {
            return oppLevelName;
        }

        public void setOppLevelName(String oppLevelName) {
            this.oppLevelName = oppLevelName;
        }

        public String getOppOwner() {
            return oppOwner;
        }

        public void setOppOwner(String oppOwner) {
            this.oppOwner = oppOwner;
        }

        public String getOppOwnerName() {
            return oppOwnerName;
        }

        public void setOppOwnerName(String oppOwnerName) {
            this.oppOwnerName = oppOwnerName;
        }

        public String getOppStatusId() {
            return oppStatusId;
        }

        public void setOppStatusId(String oppStatusId) {
            this.oppStatusId = oppStatusId;
        }

        public String getOppStatusName() {
            return oppStatusName;
        }

        public void setOppStatusName(String oppStatusName) {
            this.oppStatusName = oppStatusName;
        }

        public String getOptionPackage() {
            return optionPackage;
        }

        public void setOptionPackage(String optionPackage) {
            this.optionPackage = optionPackage;
        }

        public String getOrgId() {
            return orgId;
        }

        public void setOrgId(String orgId) {
            this.orgId = orgId;
        }

        public String getOrgName() {
            return orgName;
        }

        public void setOrgName(String orgName) {
            this.orgName = orgName;
        }

        public String getOutsideColorId() {
            return outsideColorId;
        }

        public void setOutsideColorId(String outsideColorId) {
            this.outsideColorId = outsideColorId;
        }

        public String getOutsideColorNameCn() {
            return outsideColorNameCn;
        }

        public void setOutsideColorNameCn(String outsideColorNameCn) {
            this.outsideColorNameCn = outsideColorNameCn;
        }

        public String getPaymentMode() {
            return paymentMode;
        }

        public void setPaymentMode(String paymentMode) {
            this.paymentMode = paymentMode;
        }

        public String getPaymentModeName() {
            return paymentModeName;
        }

        public void setPaymentModeName(String paymentModeName) {
            this.paymentModeName = paymentModeName;
        }

        public String getIsKeyuser() {
            return isKeyuser;
        }

        public void setIsKeyuser(String isKeyuser) {
            this.isKeyuser = isKeyuser;
        }

        public String getPropertyId() {
            return propertyId;
        }

        public void setPropertyId(String propertyId) {
            this.propertyId = propertyId;
        }

        public String getPropertyName() {
            return propertyName;
        }

        public void setPropertyName(String propertyName) {
            this.propertyName = propertyName;
        }

        public String getProvinceId() {
            return provinceId;
        }

        public void setProvinceId(String provinceId) {
            this.provinceId = provinceId;
        }

        public String getPurchaseId() {
            return purchaseId;
        }

        public void setPurchaseId(String purchaseId) {
            this.purchaseId = purchaseId;
        }

        public String getPurchaseName() {
            return purchaseName;
        }

        public void setPurchaseName(String purchaseName) {
            this.purchaseName = purchaseName;
        }

        public String getSrcTypeId() {
            return srcTypeId;
        }

        public void setSrcTypeId(String srcTypeId) {
            this.srcTypeId = srcTypeId;
        }

        public String getSrcTypeName() {
            return srcTypeName;
        }

        public void setSrcTypeName(String srcTypeName) {
            this.srcTypeName = srcTypeName;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getCustTelephone() {
            return custTelephone;
        }

        public void setCustTelephone(String custTelephone) {
            this.custTelephone = custTelephone;
        }

        public String getSeriesName() {
            return seriesName;
        }

        public void setSeriesName(String seriesName) {
            this.seriesName = seriesName;
        }
    }
}
