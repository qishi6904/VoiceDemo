package com.svw.dealerapp.entity.home;

/**
 * Created by qinshi on 11/6/2017.
 */

public class CheckRepeatEntity {


    /**
     * opp : {"oppId":"0ae123e0-ab2f-42d0-b76f-f29c2668c47b","custName":"Simon","custGender":"0","custAge":null,"custDesc":null,"oppStatusId":"11510","custMobile":"10000000000","custTelephone":null,"isWechat":null,"custWechat":null,"custEmail":null,"channelId":null,"srcTypeId":null,"oppOwner":"15000000017","oppLevel":"12010","isKeyuser":"0","purchaseId":"13010","propertyId":"13510","currentModel":null,"modelYear":null,"currentMileage":null,"budgetMin":null,"budgetMax":null,"paymentMode":null,"carModelId":"3E92FZ","outsideColorId":null,"insideColorId":null,"createUser":"15000000018","createTime":1498441562000,"updateTime":1498642662000,"remark":null,"provinceId":"32000+F190","cityId":"320500","orgId":"2211325","leadsId":"a16f0f59-8107-4c53-a9bf-a6984e4d45fb","imageUrl":null,"followupDesc":"12133","seriesId":"3E92","isComment":null,"recomName":null,"recomMobile":null}
     * daLeads : {"leadsId":"a16f0f59-8107-4c53-a9bf-a6984e4d45fb","custName":"Simon","custGender":"0","custAge":null,"custMobile":"10000000000","custTelephone":null,"custWechat":null,"custEmail":null,"walkinDate":null,"custDescription":"Asdfafsa","orgId":"2211325","salesConsultant":"15000000002","carModelId":null,"channelId":null,"srcTypeId":"11010","leadsStatusId":"10520","statusReason":null,"isFlow":"0","isDuplication":"0","createUser":"15000000018","createTime":1498441467000,"updateTime":1498441562000,"remark":null,"provinceId":null,"cityId":null,"leaveDate":null,"imageUrl":null,"isValid":null,"custRights":"上汽大众 Polo GTI GP 整车 新车 礼包 订金 大众 上海大众 汽车","seriesId":null,"srcCarDesc":null,"ccId":null}
     * id : null
     * oppId : null
     * leadsId : null
     * userId : null
     * createTime : null
     * isConversion : null
     */

    private OppBean opp;
    private DaLeadsBean daLeads;
    private String id;
    private String oppId;
    private String leadsId;
    private String userId;
    private String createTime;
    private String isConversion;

    public OppBean getOpp() {
        return opp;
    }

    public void setOpp(OppBean opp) {
        this.opp = opp;
    }

    public DaLeadsBean getDaLeads() {
        return daLeads;
    }

    public void setDaLeads(DaLeadsBean daLeads) {
        this.daLeads = daLeads;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOppId() {
        return oppId;
    }

    public void setOppId(String oppId) {
        this.oppId = oppId;
    }

    public String getLeadsId() {
        return leadsId;
    }

    public void setLeadsId(String leadsId) {
        this.leadsId = leadsId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getIsConversion() {
        return isConversion;
    }

    public void setIsConversion(String isConversion) {
        this.isConversion = isConversion;
    }

    public static class OppBean {
        /**
         * oppId : 0ae123e0-ab2f-42d0-b76f-f29c2668c47b
         * custName : Simon
         * custGender : 0
         * custAge : null
         * custDesc : null
         * oppStatusId : 11510
         * custMobile : 10000000000
         * custTelephone : null
         * isWechat : null
         * custWechat : null
         * custEmail : null
         * channelId : null
         * srcTypeId : null
         * oppOwner : 15000000017
         * oppLevel : 12010
         * isKeyuser : 0
         * purchaseId : 13010
         * propertyId : 13510
         * currentModel : null
         * modelYear : null
         * currentMileage : null
         * budgetMin : null
         * budgetMax : null
         * paymentMode : null
         * carModelId : 3E92FZ
         * outsideColorId : null
         * insideColorId : null
         * createUser : 15000000018
         * createTime : 1498441562000
         * updateTime : 1498642662000
         * remark : null
         * provinceId : 32000+F190
         * cityId : 320500
         * orgId : 2211325
         * leadsId : a16f0f59-8107-4c53-a9bf-a6984e4d45fb
         * imageUrl : null
         * followupDesc : 12133
         * seriesId : 3E92
         * isComment : null
         * recomName : null
         * recomMobile : null
         */

        private String oppId;
        private String custName;
        private String custGender;
        private String custAge;
        private String custDesc;
        private String oppStatusId;
        private String custMobile;
        private String custTelephone;
        private String isWechat;
        private String custWechat;
        private String custEmail;
        private String channelId;
        private String srcTypeId;
        private String oppOwner;
        private String oppLevel;
        private String isKeyuser;
        private String purchaseId;
        private String propertyId;
        private String currentModel;
        private String modelYear;
        private String currentMileage;
        private String budgetMin;
        private String budgetMax;
        private String paymentMode;
        private String carModelId;
        private String outsideColorId;
        private String insideColorId;
        private String createUser;
        private String createTime;
        private String updateTime;
        private String remark;
        private String provinceId;
        private String cityId;
        private String orgId;
        private String leadsId;
        private String imageUrl;
        private String followupDesc;
        private String seriesId;
        private String isComment;
        private String recomName;
        private String recomMobile;

        public String getOppId() {
            return oppId;
        }

        public void setOppId(String oppId) {
            this.oppId = oppId;
        }

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

        public String getCustAge() {
            return custAge;
        }

        public void setCustAge(String custAge) {
            this.custAge = custAge;
        }

        public String getCustDesc() {
            return custDesc;
        }

        public void setCustDesc(String custDesc) {
            this.custDesc = custDesc;
        }

        public String getOppStatusId() {
            return oppStatusId;
        }

        public void setOppStatusId(String oppStatusId) {
            this.oppStatusId = oppStatusId;
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

        public String getIsWechat() {
            return isWechat;
        }

        public void setIsWechat(String isWechat) {
            this.isWechat = isWechat;
        }

        public String getCustWechat() {
            return custWechat;
        }

        public void setCustWechat(String custWechat) {
            this.custWechat = custWechat;
        }

        public String getCustEmail() {
            return custEmail;
        }

        public void setCustEmail(String custEmail) {
            this.custEmail = custEmail;
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

        public String getOppOwner() {
            return oppOwner;
        }

        public void setOppOwner(String oppOwner) {
            this.oppOwner = oppOwner;
        }

        public String getOppLevel() {
            return oppLevel;
        }

        public void setOppLevel(String oppLevel) {
            this.oppLevel = oppLevel;
        }

        public String getIsKeyuser() {
            return isKeyuser;
        }

        public void setIsKeyuser(String isKeyuser) {
            this.isKeyuser = isKeyuser;
        }

        public String getPurchaseId() {
            return purchaseId;
        }

        public void setPurchaseId(String purchaseId) {
            this.purchaseId = purchaseId;
        }

        public String getPropertyId() {
            return propertyId;
        }

        public void setPropertyId(String propertyId) {
            this.propertyId = propertyId;
        }

        public String getCurrentModel() {
            return currentModel;
        }

        public void setCurrentModel(String currentModel) {
            this.currentModel = currentModel;
        }

        public String getModelYear() {
            return modelYear;
        }

        public void setModelYear(String modelYear) {
            this.modelYear = modelYear;
        }

        public String getCurrentMileage() {
            return currentMileage;
        }

        public void setCurrentMileage(String currentMileage) {
            this.currentMileage = currentMileage;
        }

        public String getBudgetMin() {
            return budgetMin;
        }

        public void setBudgetMin(String budgetMin) {
            this.budgetMin = budgetMin;
        }

        public String getBudgetMax() {
            return budgetMax;
        }

        public void setBudgetMax(String budgetMax) {
            this.budgetMax = budgetMax;
        }

        public String getPaymentMode() {
            return paymentMode;
        }

        public void setPaymentMode(String paymentMode) {
            this.paymentMode = paymentMode;
        }

        public String getCarModelId() {
            return carModelId;
        }

        public void setCarModelId(String carModelId) {
            this.carModelId = carModelId;
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

        public String getOrgId() {
            return orgId;
        }

        public void setOrgId(String orgId) {
            this.orgId = orgId;
        }

        public String getLeadsId() {
            return leadsId;
        }

        public void setLeadsId(String leadsId) {
            this.leadsId = leadsId;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getFollowupDesc() {
            return followupDesc;
        }

        public void setFollowupDesc(String followupDesc) {
            this.followupDesc = followupDesc;
        }

        public String getSeriesId() {
            return seriesId;
        }

        public void setSeriesId(String seriesId) {
            this.seriesId = seriesId;
        }

        public String getIsComment() {
            return isComment;
        }

        public void setIsComment(String isComment) {
            this.isComment = isComment;
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
    }

    public static class DaLeadsBean {
        /**
         * leadsId : a16f0f59-8107-4c53-a9bf-a6984e4d45fb
         * custName : Simon
         * custGender : 0
         * custAge : null
         * custMobile : 10000000000
         * custTelephone : null
         * custWechat : null
         * custEmail : null
         * walkinDate : null
         * custDescription : Asdfafsa
         * orgId : 2211325
         * salesConsultant : 15000000002
         * carModelId : null
         * channelId : null
         * srcTypeId : 11010
         * leadsStatusId : 10520
         * statusReason : null
         * isFlow : 0
         * isDuplication : 0
         * createUser : 15000000018
         * createTime : 1498441467000
         * updateTime : 1498441562000
         * remark : null
         * provinceId : null
         * cityId : null
         * leaveDate : null
         * imageUrl : null
         * isValid : null
         * custRights : 上汽大众 Polo GTI GP 整车 新车 礼包 订金 大众 上海大众 汽车
         * seriesId : null
         * srcCarDesc : null
         * ccId : null
         */

        private String leadsId;
        private String custName;
        private String custGender;
        private String custAge;
        private String custMobile;
        private String custTelephone;
        private String custWechat;
        private String custEmail;
        private String walkinDate;
        private String custDescription;
        private String orgId;
        private String salesConsultant;
        private String carModelId;
        private String channelId;
        private String srcTypeId;
        private String leadsStatusId;
        private String statusReason;
        private String isFlow;
        private String isDuplication;
        private String createUser;
        private String createTime;
        private String updateTime;
        private String remark;
        private String provinceId;
        private String cityId;
        private String leaveDate;
        private String imageUrl;
        private String isValid;
        private String custRights;
        private String seriesId;
        private String srcCarDesc;
        private String ccId;

        public String getLeadsId() {
            return leadsId;
        }

        public void setLeadsId(String leadsId) {
            this.leadsId = leadsId;
        }

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

        public String getCustAge() {
            return custAge;
        }

        public void setCustAge(String custAge) {
            this.custAge = custAge;
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

        public String getCustWechat() {
            return custWechat;
        }

        public void setCustWechat(String custWechat) {
            this.custWechat = custWechat;
        }

        public String getCustEmail() {
            return custEmail;
        }

        public void setCustEmail(String custEmail) {
            this.custEmail = custEmail;
        }

        public String getWalkinDate() {
            return walkinDate;
        }

        public void setWalkinDate(String walkinDate) {
            this.walkinDate = walkinDate;
        }

        public String getCustDescription() {
            return custDescription;
        }

        public void setCustDescription(String custDescription) {
            this.custDescription = custDescription;
        }

        public String getOrgId() {
            return orgId;
        }

        public void setOrgId(String orgId) {
            this.orgId = orgId;
        }

        public String getSalesConsultant() {
            return salesConsultant;
        }

        public void setSalesConsultant(String salesConsultant) {
            this.salesConsultant = salesConsultant;
        }

        public String getCarModelId() {
            return carModelId;
        }

        public void setCarModelId(String carModelId) {
            this.carModelId = carModelId;
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

        public String getLeadsStatusId() {
            return leadsStatusId;
        }

        public void setLeadsStatusId(String leadsStatusId) {
            this.leadsStatusId = leadsStatusId;
        }

        public String getStatusReason() {
            return statusReason;
        }

        public void setStatusReason(String statusReason) {
            this.statusReason = statusReason;
        }

        public String getIsFlow() {
            return isFlow;
        }

        public void setIsFlow(String isFlow) {
            this.isFlow = isFlow;
        }

        public String getIsDuplication() {
            return isDuplication;
        }

        public void setIsDuplication(String isDuplication) {
            this.isDuplication = isDuplication;
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

        public String getLeaveDate() {
            return leaveDate;
        }

        public void setLeaveDate(String leaveDate) {
            this.leaveDate = leaveDate;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getIsValid() {
            return isValid;
        }

        public void setIsValid(String isValid) {
            this.isValid = isValid;
        }

        public String getCustRights() {
            return custRights;
        }

        public void setCustRights(String custRights) {
            this.custRights = custRights;
        }

        public String getSeriesId() {
            return seriesId;
        }

        public void setSeriesId(String seriesId) {
            this.seriesId = seriesId;
        }

        public String getSrcCarDesc() {
            return srcCarDesc;
        }

        public void setSrcCarDesc(String srcCarDesc) {
            this.srcCarDesc = srcCarDesc;
        }

        public String getCcId() {
            return ccId;
        }

        public void setCcId(String ccId) {
            this.ccId = ccId;
        }
    }
}
