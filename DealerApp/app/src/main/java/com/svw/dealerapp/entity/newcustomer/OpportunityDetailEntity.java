package com.svw.dealerapp.entity.newcustomer;


import com.svw.dealerapp.entity.optionalpackage.OptionalPackageEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lijinkui on 2017/5/27.
 */

public class OpportunityDetailEntity implements Serializable {

    /**
     * oppId : 23rwr432few32fewer32423rerewrwer
     * custName : 张三
     * custGender : 0
     * custAge : 35
     * custDesc :
     * oppStatusId : 1
     * custMobile : 17601336803
     * custTelephone : 34343234
     * isWechat : 0
     * custWechat :
     * custEmail :
     * channelId : 2
     * channelName : werwerewr
     * srcTypeId : 1
     * srcTypeName : sdfsdfeff
     * oppOwner : 3323443
     * oppLevel : 32334
     * oppLevelName : H
     * isKeyuser : 0
     * purchaseId : 1
     * purchaseName : fwerwerwe
     * propertyId : 23233
     * currentModel : 3E92FZ
     * modelYear :
     * currentMileage :
     * budgetMin : 20000
     * budgetMax : 30000
     * paymentMode :
     * carModelId : 3E92FZ
     * outsideColorId : 2
     * insideColorId : 3
     * demandId : 1
     * demandDesc :
     * infoId : 1
     * infoDesc :
     * remark :
     * createUser : 1234
     * leadsId : 32343243543654654765765765765734543
     * provinceId : 212212121222
     * cityId : 323432423432
     * orgId : 3232342432423432
     * followupTags : [{"modeId":"15510"},{"modeId":"15511"}]
     * appointments : [{"appmId":"","oppId":"23rwr432few32fewer32423rerewrwer","appmCust":"张三","appmGender":"0","appmMobile":"17601336803","appmDateStr":"2017-05-20 09:30:00","appmDesc":"","appmOwner":"121212","appmTypeId":"12345","appmStatusId":"12345","isFirst":"0","isReminder":"1","reminderInterval":"","createUser":"","createTimeStr":"2017-05-19 09:30:00","updateTimeStr":"2017-05-20 09:30:00","remark":"23123123"},{"appmId":"","oppId":"23rwr432few32fewer32423rerewrwer","appmCust":"张三","appmGender":"0","appmMobile":"17601336803","appmDateStr":"2017-05-20 09:30:00","appmDesc":"","appmOwner":"121212","appmTypeId":"12345","appmStatusId":"12345","isFirst":"0","isReminder":"1","reminderInterval":"","createUser":"","createTimeStr":"2017-05-19 09:30:00","updateTimeStr":"2017-05-20 09:30:00","remark":"23123123"}]
     * followups : [{"followupId":"23rw2222few32fewer32423rerewrwer","oppId":"23rwr432few32fewer32423rerewrwer","followupOwner":"33343","scheduleDateStr":"2017-05-19 09:30:00","scheduleDesc":"eweeeeeeeeeeeeeeeee","followupDateStr":"2017-05-19 09:30:00","modeId":"32323","modeName":"weeeeeeweew","oppLevel":"22222","oppLevelName":"eeecffewfew","createUser":"","createTimeStr":"2017-05-19 09:30:00","updateTimeStr":"2017-05-20 09:30:00","remark":"23123123","resultDesc":"试车、驾车、其他"},{"followupId":"23rw2222few32fewer32423rerewrwer","oppId":"23rwr432few32fewer32423rerewrwer","followupOwner":"33343","scheduleDateStr":"2017-05-19 09:30:00","scheduleDesc":"eweeeeeeeeeeeeeeeee","followupDateStr":"2017-05-19 09:30:00","modeId":"32323","modeName":"weeeeeeweew","oppLevel":"22222","oppLevelName":"eeecffewfew","createUser":"","createTimeStr":"2017-05-19 09:30:00","updateTimeStr":"2017-05-20 09:30:00","remark":"23123123","resultDesc":"试车、驾车、其他"}]
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
    private String channelName;
    private String srcTypeId;
    private String srcTypeName;
    private String oppOwner;
    private String oppOwnerName;
    private String oppLevel;
    private String oppLevelName;
    private String isKeyuser;
    private String purchaseId;
    private String purchaseName;
    private String propertyId;
    private String currentModel;
    private String modelYear;
    private String currentMileage;
    private String budgetMin;
    private String budgetMax;
    private String paymentMode;
    private String carModelId;
    private String carModelName;
    private String outsideColorId;
    private String insideColorId;
    private String demandId;
    private String demandDesc;
    private String infoId;
    private String infoDesc;
    private String remark;
    private String createUser;
    private String leadsId;
    private String provinceId;
    private String cityId;
    private String orgId;
    private String outsideColorName;
    private String insideColorName;
    private String followupDesc;
    private String channelImageUrl;
    private String isComment;
    private String recomName;
    private String recomMobile;
    private String seriesId;
    private String optionPackage;
    private String activityId;
    private String activitySubject;
    private String appraiserId;
    private String appraiserName;
    private String customerCode;
    private String orderStatus;//当前订单状态
    private String isOtdOrder;//当前订单是否为otd 0：是 1：否
    private String orderId;//当前orderId
    private List<FollowupTagsBean> followupTags;
    private List<AppointmentsBean> appointments;
    private List<FollowupsBean> followups;
    private List<RelationsBean> opportunityRelations;

    private ArrayList<OptionalPackageEntity.OptionListBean> ecCarOptions;//选装包

    private boolean isYellowCardOwner = true; //当前用户是否是该黄卡的owner
    private boolean isSleepStatus = false;    //是否是休眠状态
    private boolean isFailedStatus = false;   //是否是战败状态

    private String leadId;//用于再进展厅客源黄卡关联

    public String getLeadId() {
        return leadId;
    }

    public void setLeadId(String leadId) {
        this.leadId = leadId;
    }

    public String getIsComment() {
        return isComment;
    }

    public void setIsComment(String isComment) {
        this.isComment = isComment;
    }

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

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
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

    public String getPurchaseName() {
        return purchaseName;
    }

    public void setPurchaseName(String purchaseName) {
        this.purchaseName = purchaseName;
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

    public String getCarModelName() {
        return carModelName;
    }

    public void setCarModelName(String carModelName) {
        this.carModelName = carModelName;
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

    public String getFollowupDesc() {
        return followupDesc;
    }

    public void setFollowupDesc(String followupDesc) {
        this.followupDesc = followupDesc;
    }

    public String getDemandId() {
        return demandId;
    }

    public void setDemandId(String demandId) {
        this.demandId = demandId;
    }

    public String getDemandDesc() {
        return demandDesc;
    }

    public void setDemandDesc(String demandDesc) {
        this.demandDesc = demandDesc;
    }

    public String getInfoId() {
        return infoId;
    }

    public void setInfoId(String infoId) {
        this.infoId = infoId;
    }

    public String getInfoDesc() {
        return infoDesc;
    }

    public void setInfoDesc(String infoDesc) {
        this.infoDesc = infoDesc;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getLeadsId() {
        return leadsId;
    }

    public void setLeadsId(String leadsId) {
        this.leadsId = leadsId;
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

    public String getOutsideColorName() {
        return outsideColorName;
    }

    public void setOutsideColorName(String outsideColorName) {
        this.outsideColorName = outsideColorName;
    }

    public String getInsideColorName() {
        return insideColorName;
    }

    public void setInsideColorName(String insideColorName) {
        this.insideColorName = insideColorName;
    }

    public String getChannelImageUrl() {
        return channelImageUrl;
    }

    public void setChannelImageUrl(String channelImageUrl) {
        this.channelImageUrl = channelImageUrl;
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

    public String getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(String seriesId) {
        this.seriesId = seriesId;
    }

    public String getOptionPackage() {
        return optionPackage;
    }

    public void setOptionPackage(String optionPackage) {
        this.optionPackage = optionPackage;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getActivitySubject() {
        return activitySubject;
    }

    public void setActivitySubject(String activitySubject) {
        this.activitySubject = activitySubject;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public List<FollowupTagsBean> getFollowupTags() {
        return followupTags;
    }

    public void setFollowupTags(List<FollowupTagsBean> followupTags) {
        this.followupTags = followupTags;
    }

    public List<AppointmentsBean> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<AppointmentsBean> appointments) {
        this.appointments = appointments;
    }

    public List<FollowupsBean> getFollowups() {
        return followups;
    }

    public void setFollowups(List<FollowupsBean> followups) {
        this.followups = followups;
    }

    public List<RelationsBean> getOpportunityRelations() {
        return opportunityRelations;
    }

    public void setOpportunityRelations(List<RelationsBean> opportunityRelations) {
        this.opportunityRelations = opportunityRelations;
    }

    public boolean isYellowCardOwner() {
        return isYellowCardOwner;
    }

    public void setYellowCardOwner(boolean yellowCardOwner) {
        isYellowCardOwner = yellowCardOwner;
    }

    public boolean isSleepStatus() {
        return isSleepStatus;
    }

    public void setSleepStatus(boolean sleepStatus) {
        isSleepStatus = sleepStatus;
    }

    public boolean isFailedStatus() {
        return isFailedStatus;
    }

    public void setFailedStatus(boolean failedStatus) {
        isFailedStatus = failedStatus;
    }

    public String getAppraiserId() {
        return appraiserId;
    }

    public void setAppraiserId(String appraiserId) {
        this.appraiserId = appraiserId;
    }

    public String getAppraiserName() {
        return appraiserName;
    }

    public void setAppraiserName(String appraiserName) {
        this.appraiserName = appraiserName;
    }

    public ArrayList<OptionalPackageEntity.OptionListBean> getEcCarOptions() {
        return ecCarOptions;
    }

    public void setEcCarOptions(ArrayList<OptionalPackageEntity.OptionListBean> ecCarOptions) {
        this.ecCarOptions = ecCarOptions;
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

    public static class FollowupTagsBean implements Serializable {
        /**
         * modeId : 15510
         */

        private String dictId;

        public String getDictId() {
            return dictId;
        }

        public void setDictId(String dictId) {
            this.dictId = dictId;
        }
    }

    public static class AppointmentsBean implements Serializable {
        /**
         * appmId :
         * oppId : 23rwr432few32fewer32423rerewrwer
         * appmCust : 张三
         * appmGender : 0
         * appmMobile : 17601336803
         * appmDateStr : 2017-05-20 09:30:00
         * appmDesc :
         * appmOwner : 121212
         * appmTypeId : 12345
         * appmStatusId : 12345
         * isFirst : 0
         * isReminder : 1
         * reminderInterval :
         * createUser :
         * createTimeStr : 2017-05-19 09:30:00
         * updateTimeStr : 2017-05-20 09:30:00
         * remark : 23123123
         */

        private String appmId;
        private String oppId;
        private String appmCust;
        private String appmGender;
        private String appmMobile;
        private String appmDateStr;
        private String appmDesc;
        private String appmOwner;
        private String appmTypeId;
        private String appmTypeName;
        private String appmStatusId;
        private String isFirst;
        private String isReminder;
        private String reminderInterval;
        private String createUser;
        private String createTimeStr;
        private String updateTimeStr;
        private String remark;

        private boolean isShowEdit;

        public String getAppmId() {
            return appmId;
        }

        public void setAppmId(String appmId) {
            this.appmId = appmId;
        }

        public String getOppId() {
            return oppId;
        }

        public void setOppId(String oppId) {
            this.oppId = oppId;
        }

        public String getAppmCust() {
            return appmCust;
        }

        public void setAppmCust(String appmCust) {
            this.appmCust = appmCust;
        }

        public String getAppmGender() {
            return appmGender;
        }

        public void setAppmGender(String appmGender) {
            this.appmGender = appmGender;
        }

        public String getAppmMobile() {
            return appmMobile;
        }

        public void setAppmMobile(String appmMobile) {
            this.appmMobile = appmMobile;
        }

        public String getAppmDateStr() {
            return appmDateStr;
        }

        public void setAppmDateStr(String appmDateStr) {
            this.appmDateStr = appmDateStr;
        }

        public String getAppmDesc() {
            return appmDesc;
        }

        public void setAppmDesc(String appmDesc) {
            this.appmDesc = appmDesc;
        }

        public String getAppmOwner() {
            return appmOwner;
        }

        public void setAppmOwner(String appmOwner) {
            this.appmOwner = appmOwner;
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

        public String getAppmStatusId() {
            return appmStatusId;
        }

        public void setAppmStatusId(String appmStatusId) {
            this.appmStatusId = appmStatusId;
        }

        public String getIsFirst() {
            return isFirst;
        }

        public void setIsFirst(String isFirst) {
            this.isFirst = isFirst;
        }

        public String getIsReminder() {
            return isReminder;
        }

        public void setIsReminder(String isReminder) {
            this.isReminder = isReminder;
        }

        public String getReminderInterval() {
            return reminderInterval;
        }

        public void setReminderInterval(String reminderInterval) {
            this.reminderInterval = reminderInterval;
        }

        public String getCreateUser() {
            return createUser;
        }

        public void setCreateUser(String createUser) {
            this.createUser = createUser;
        }

        public String getCreateTimeStr() {
            return createTimeStr;
        }

        public void setCreateTimeStr(String createTimeStr) {
            this.createTimeStr = createTimeStr;
        }

        public String getUpdateTimeStr() {
            return updateTimeStr;
        }

        public void setUpdateTimeStr(String updateTimeStr) {
            this.updateTimeStr = updateTimeStr;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public boolean isShowEdit() {
            return isShowEdit;
        }

        public void setShowEdit(boolean showEdit) {
            isShowEdit = showEdit;
        }
    }

    public static class FollowupsBean implements Serializable {
        /**
         * followupId : 23rw2222few32fewer32423rerewrwer
         * oppId : 23rwr432few32fewer32423rerewrwer
         * followupOwner : 33343
         * scheduleDateStr : 2017-05-19 09:30:00
         * scheduleDesc : eweeeeeeeeeeeeeeeee
         * followupDateStr : 2017-05-19 09:30:00
         * modeId : 32323
         * modeName : weeeeeeweew
         * oppLevel : 22222
         * oppLevelName : eeecffewfew
         * createUser :
         * createTimeStr : 2017-05-19 09:30:00
         * updateTimeStr : 2017-05-20 09:30:00
         * remark : 23123123
         * resultDesc : 试车、驾车、其他
         */

        private String followupId;
        private String oppId;
        private String followupOwner;
        private String scheduleDateStr;
        private String scheduleDesc;
        private String followupDateStr;
        private String modeId;
        private String modeName;
        private String oppLevel;
        private String oppLevelName;
        private String createUser;
        private String createTimeStr;
        private String updateTimeStr;
        private String remark;
        private String resultDesc;
        private String followupStatus;
        private String approvalStatusId;
        private String applicationDesc;
        private String isTestdrive;
        private List<ResultsBean> results;

        public String getIsTestdrive() {
            return isTestdrive;
        }

        public void setIsTestdrive(String isTestdrive) {
            this.isTestdrive = isTestdrive;
        }

        public String getFollowupId() {
            return followupId;
        }

        public void setFollowupId(String followupId) {
            this.followupId = followupId;
        }

        public String getOppId() {
            return oppId;
        }

        public void setOppId(String oppId) {
            this.oppId = oppId;
        }

        public String getFollowupOwner() {
            return followupOwner;
        }

        public void setFollowupOwner(String followupOwner) {
            this.followupOwner = followupOwner;
        }

        public String getScheduleDateStr() {
            return scheduleDateStr;
        }

        public void setScheduleDateStr(String scheduleDateStr) {
            this.scheduleDateStr = scheduleDateStr;
        }

        public String getScheduleDesc() {
            return scheduleDesc;
        }

        public void setScheduleDesc(String scheduleDesc) {
            this.scheduleDesc = scheduleDesc;
        }

        public String getFollowupDateStr() {
            return followupDateStr;
        }

        public void setFollowupDateStr(String followupDateStr) {
            this.followupDateStr = followupDateStr;
        }

        public String getModeId() {
            return modeId;
        }

        public void setModeId(String modeId) {
            this.modeId = modeId;
        }

        public String getModeName() {
            return modeName;
        }

        public void setModeName(String modeName) {
            this.modeName = modeName;
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

        public String getCreateUser() {
            return createUser;
        }

        public void setCreateUser(String createUser) {
            this.createUser = createUser;
        }

        public String getCreateTimeStr() {
            return createTimeStr;
        }

        public void setCreateTimeStr(String createTimeStr) {
            this.createTimeStr = createTimeStr;
        }

        public String getUpdateTimeStr() {
            return updateTimeStr;
        }

        public void setUpdateTimeStr(String updateTimeStr) {
            this.updateTimeStr = updateTimeStr;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getResultDesc() {
            return resultDesc;
        }

        public void setResultDesc(String resultDesc) {
            this.resultDesc = resultDesc;
        }

        public String getFollowupStatus() {
            return followupStatus;
        }

        public void setFollowupStatus(String followupStatus) {
            this.followupStatus = followupStatus;
        }

        public String getApprovalStatusId() {
            return approvalStatusId;
        }

        public void setApprovalStatusId(String approvalStatusId) {
            this.approvalStatusId = approvalStatusId;
        }

        public String getApplicationDesc() {
            return applicationDesc;
        }

        public void setApplicationDesc(String applicationDesc) {
            this.applicationDesc = applicationDesc;
        }

        public List<ResultsBean> getResults() {
            return results;
        }

        public void setResults(List<ResultsBean> results) {
            this.results = results;
        }

    }

    public static class RelationsBean implements Serializable {
        private String relaName;
        private String relaDesc;
        private String relaId;
        private String createUser;
        private String oppId;
        private String remark;
        private String id;
        private String relaFlag;

        public String getRelaName() {
            return relaName;
        }

        public void setRelaName(String relaName) {
            this.relaName = relaName;
        }

        public String getRelaDesc() {
            return relaDesc;
        }

        public void setRelaDesc(String relaDesc) {
            this.relaDesc = relaDesc;
        }

        public String getRelaId() {
            return relaId;
        }

        public void setRelaId(String relaId) {
            this.relaId = relaId;
        }

        public String getCreateUser() {
            return createUser;
        }

        public void setCreateUser(String createUser) {
            this.createUser = createUser;
        }

        public String getOppId() {
            return oppId;
        }

        public void setOppId(String oppId) {
            this.oppId = oppId;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getRelaFlag() {
            return relaFlag;
        }

        public void setRelaFlag(String relaFlag) {
            this.relaFlag = relaFlag;
        }
    }

    public static class ResultsBean implements Serializable {
        public String dictName;
        public String dictId;
        public String orderId;
        public String orderStatus;

        public String getDictName() {
            return dictName;
        }

        public void setDictName(String dictName) {
            this.dictName = dictName;
        }

        public String getDictId() {
            return dictId;
        }

        public void setDictId(String dictId) {
            this.dictId = dictId;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(String orderStatus) {
            this.orderStatus = orderStatus;
        }

    }

    @Override
    public String toString() {
        return "OpportunityDetailEntity{" +
                "oppId='" + oppId + '\'' +
                ", custName='" + custName + '\'' +
                ", custGender='" + custGender + '\'' +
                ", custAge='" + custAge + '\'' +
                ", custDesc='" + custDesc + '\'' +
                ", oppStatusId='" + oppStatusId + '\'' +
                ", custMobile='" + custMobile + '\'' +
                ", custTelephone='" + custTelephone + '\'' +
                ", isWechat='" + isWechat + '\'' +
                ", custWechat='" + custWechat + '\'' +
                ", custEmail='" + custEmail + '\'' +
                ", channelId='" + channelId + '\'' +
                ", channelName='" + channelName + '\'' +
                ", srcTypeId='" + srcTypeId + '\'' +
                ", srcTypeName='" + srcTypeName + '\'' +
                ", oppOwner='" + oppOwner + '\'' +
                ", oppOwnerName='" + oppOwnerName + '\'' +
                ", oppLevel='" + oppLevel + '\'' +
                ", oppLevelName='" + oppLevelName + '\'' +
                ", isKeyuser='" + isKeyuser + '\'' +
                ", purchaseId='" + purchaseId + '\'' +
                ", purchaseName='" + purchaseName + '\'' +
                ", propertyId='" + propertyId + '\'' +
                ", currentModel='" + currentModel + '\'' +
                ", modelYear='" + modelYear + '\'' +
                ", currentMileage='" + currentMileage + '\'' +
                ", budgetMin='" + budgetMin + '\'' +
                ", budgetMax='" + budgetMax + '\'' +
                ", paymentMode='" + paymentMode + '\'' +
                ", carModelId='" + carModelId + '\'' +
                ", carModelName='" + carModelName + '\'' +
                ", outsideColorId='" + outsideColorId + '\'' +
                ", insideColorId='" + insideColorId + '\'' +
                ", demandId='" + demandId + '\'' +
                ", demandDesc='" + demandDesc + '\'' +
                ", infoId='" + infoId + '\'' +
                ", infoDesc='" + infoDesc + '\'' +
                ", remark='" + remark + '\'' +
                ", createUser='" + createUser + '\'' +
                ", leadsId='" + leadsId + '\'' +
                ", provinceId='" + provinceId + '\'' +
                ", cityId='" + cityId + '\'' +
                ", orgId='" + orgId + '\'' +
                ", outsideColorName='" + outsideColorName + '\'' +
                ", insideColorName='" + insideColorName + '\'' +
                ", followupDesc='" + followupDesc + '\'' +
                ", channelImageUrl='" + channelImageUrl + '\'' +
                ", isComment='" + isComment + '\'' +
                ", recomName='" + recomName + '\'' +
                ", recomMobile='" + recomMobile + '\'' +
                ", seriesId='" + seriesId + '\'' +
                ", optionPackage='" + optionPackage + '\'' +
                ", activityId='" + activityId + '\'' +
                ", activitySubject='" + activitySubject + '\'' +
                ", appraiserId='" + appraiserId + '\'' +
                ", appraiserName='" + appraiserName + '\'' +
                ", customerCode='" + customerCode + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", isOtdOrder='" + isOtdOrder + '\'' +
                ", orderId='" + orderId + '\'' +
                ", followupTags=" + followupTags +
                ", appointments=" + appointments +
                ", followups=" + followups +
                ", opportunityRelations=" + opportunityRelations +
                ", ecCarOptions=" + ecCarOptions +
                ", isYellowCardOwner=" + isYellowCardOwner +
                ", isSleepStatus=" + isSleepStatus +
                ", isFailedStatus=" + isFailedStatus +
                ", leadId='" + leadId + '\'' +
                '}';
    }
}
