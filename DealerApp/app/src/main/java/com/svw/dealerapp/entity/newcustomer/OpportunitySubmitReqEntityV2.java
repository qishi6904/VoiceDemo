package com.svw.dealerapp.entity.newcustomer;

import com.svw.dealerapp.entity.ReqEntity;
import com.svw.dealerapp.entity.optionalpackage.OptionalPackageEntity;

import java.util.List;

/**
 * Created by xupan on 28/09/2017.
 */

public class OpportunitySubmitReqEntityV2 extends ReqEntity {

    /**
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
     * srcTypeId : 1
     * oppOwner : 李四
     * oppLevel : H
     * isKeyuser : 0
     * purchaseId : 1
     * propertyId : 2
     * currentModel : 3E92FZ
     * modelYear :
     * currentMileage :
     * budgetMin : 20000
     * paymentMode :
     * carModelId : 3E92FZ
     * outsideColorId : 2
     * insideColorId : 3
     * remark :
     * createUser : 1234
     * leadsId : 32343243543654654765765765765734543
     * provinceId : 212212121222
     * cityId : 323432423432
     * orgId : 3232342432423432
     * scheduleDateStr : 2017-05-12T10: 01: 06.006+0800
     * followupDesc : 23232323232323232323
     * opportunityRelations : [{"relaId":"10012","relaDesc":"lflsfldsfjdsl","relaFlag":"0","remark":"sdfdsfsdfdsfsdfdsf"},{"relaId":"10013","relaDesc":"ssssssss","relaFlag":"1","remark":"wwwwwwwwwwwwwww"}]
     * modeId : 15010
     * nextModeId : 15010
     * followupRemark : aaaa
     * nextScheduleDesc : bbbb
     * results : [{"dictId":"15530","resultDesc":"ccccc"},{"dictId":"15540"}]
     * appointment : {"appmDateStr":"2017-05-12T10: 01: 06.006+0800","appmTypeId":"17010"}
     */

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
    private String recomName;
    private String recomMobile;
    private String purchaseId;
    private String propertyId;
    private String currentModel;
    private String modelYear;
    private String currentMileage;
    private String budgetMin;
    private String budgetMax;
    private String paymentMode;
    private String seriesId;
    private String activityId;
    private String optionPackage;
    private String carModelId;
    private String outsideColorId;
    private String insideColorId;
    private String remark;
    private String createUser;
    private String leadsId;
    private String provinceId;
    private String cityId;
    private String orgId;
    private String scheduleDateStr;
    private String scheduleDesc;
    private String followupDesc;
    private String modeId;
    private String nextModeId;
    private String followupRemark;
    private String nextScheduleDesc;
    private String appmDateStr;
    private String appmTypeId;
    private String appraiserId;
    private String competitor;
    private String isTestdrive;
    private Appointment appointment;

    private List<OpportunityRelationsBean> opportunityRelations;
    private List<FollowupCreateReqEntity.ResultsBean> results;

    private List<OptionalPackageEntity.OptionListBean> ecCarOptions;//选装包

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
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

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
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

    public String getFollowupDesc() {
        return followupDesc;
    }

    public void setFollowupDesc(String followupDesc) {
        this.followupDesc = followupDesc;
    }

    public String getModeId() {
        return modeId;
    }

    public void setModeId(String modeId) {
        this.modeId = modeId;
    }

    public String getNextModeId() {
        return nextModeId;
    }

    public void setNextModeId(String nextModeId) {
        this.nextModeId = nextModeId;
    }

    public String getFollowupRemark() {
        return followupRemark;
    }

    public void setFollowupRemark(String followupRemark) {
        this.followupRemark = followupRemark;
    }

    public String getNextScheduleDesc() {
        return nextScheduleDesc;
    }

    public void setNextScheduleDesc(String nextScheduleDesc) {
        this.nextScheduleDesc = nextScheduleDesc;
    }

    public String getAppmDateStr() {
        return appmDateStr;
    }

    public void setAppmDateStr(String appmDateStr) {
        this.appmDateStr = appmDateStr;
    }

    public String getAppmTypeId() {
        return appmTypeId;
    }

    public void setAppmTypeId(String appmTypeId) {
        this.appmTypeId = appmTypeId;
    }

    public String getAppraiserId() {
        return appraiserId;
    }

    public void setAppraiserId(String appraiserId) {
        this.appraiserId = appraiserId;
    }

    public String getCompetitor() {
        return competitor;
    }

    public void setCompetitor(String competitor) {
        this.competitor = competitor;
    }

    public String getIsTestdrive() {
        return isTestdrive;
    }

    public void setIsTestdrive(String isTestdrive) {
        this.isTestdrive = isTestdrive;
    }

    public List<OpportunityRelationsBean> getOpportunityRelations() {
        return opportunityRelations;
    }

    public void setOpportunityRelations(List<OpportunityRelationsBean> opportunityRelations) {
        this.opportunityRelations = opportunityRelations;
    }

    public List<FollowupCreateReqEntity.ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<FollowupCreateReqEntity.ResultsBean> results) {
        this.results = results;
    }

    public List<OptionalPackageEntity.OptionListBean> getEcCarOptions() {
        return ecCarOptions;
    }

    public void setEcCarOptions(List<OptionalPackageEntity.OptionListBean> ecCarOptions) {
        this.ecCarOptions = ecCarOptions;
    }

    public static class Appointment {

        private String appmDateStr;
        private String appmTypeId;

        public String getAppmDateStr() {
            return appmDateStr;
        }

        public void setAppmDateStr(String appmDateStr) {
            this.appmDateStr = appmDateStr;
        }

        public String getAppmTypeId() {
            return appmTypeId;
        }

        public void setAppmTypeId(String appmTypeId) {
            this.appmTypeId = appmTypeId;
        }
    }

//    public static class OpportunityRelationsBean {
//        /**
//         * relaId : 10012
//         * relaDesc : lflsfldsfjdsl
//         * relaFlag : 0
//         * remark : sdfdsfsdfdsfsdfdsf
//         */
//
//        private String relaId;
//        private String relaDesc;
//        private String relaFlag;
//        private String remark;
//
//        public String getRelaId() {
//            return relaId;
//        }
//
//        public void setRelaId(String relaId) {
//            this.relaId = relaId;
//        }
//
//        public String getRelaDesc() {
//            return relaDesc;
//        }
//
//        public void setRelaDesc(String relaDesc) {
//            this.relaDesc = relaDesc;
//        }
//
//        public String getRelaFlag() {
//            return relaFlag;
//        }
//
//        public void setRelaFlag(String relaFlag) {
//            this.relaFlag = relaFlag;
//        }
//
//        public String getRemark() {
//            return remark;
//        }
//
//        public void setRemark(String remark) {
//            this.remark = remark;
//        }
//    }

//    public static class ResultsBean {
//        /**
//         * dictId : 15530
//         * resultDesc : ccccc
//         */
//
//        private String dictId;
//        private String resultDesc;
//
//        public String getDictId() {
//            return dictId;
//        }
//
//        public void setDictId(String dictId) {
//            this.dictId = dictId;
//        }
//
//        public String getResultDesc() {
//            return resultDesc;
//        }
//
//        public void setResultDesc(String resultDesc) {
//            this.resultDesc = resultDesc;
//        }
//    }
}
