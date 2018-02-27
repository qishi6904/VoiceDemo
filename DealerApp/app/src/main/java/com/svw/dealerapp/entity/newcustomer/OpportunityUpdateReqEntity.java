package com.svw.dealerapp.entity.newcustomer;

import com.svw.dealerapp.entity.ReqEntity;
import com.svw.dealerapp.entity.optionalpackage.OptionalPackageEntity;

import java.util.List;

/**
 * Created by lijinkui on 2017/6/2.
 */

public class OpportunityUpdateReqEntity extends ReqEntity {
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
     * srcTypeId : 1
     * oppOwner : 李四
     * oppLevel : H
     * isKeyuser : 0
     * purchaseId : 1
     * propertyId : 2
     * currentModel : 3E92FZ
     * modelYear :
     * currentMileage :
     * budgetMin : 10000
     * budgetMax : 20000
     * paymentMode :
     * carModelId : 3E92FZ
     * outsideColorId : 2
     * insideColorId : 3
     * remark :
     * leadsId : 3432432543543654645645
     * provinceId : 212212121222
     * cityId : 323432423432
     * orgId : 3232342432423432
     * opportunityRelations : [{"relaId":"10012","relaDesc":"lflsfldsfjdsl","relaFlag":"0","remark":"sdfdsfsdfdsfsdfdsf"},{"relaId":"10013","relaDesc":"ssssssss","relaFlag":"1","remark":"wwwwwwwwwwwwwww"}]
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
    private String seriesId;
    private String carModelId;
    private String outsideColorId;
    private String insideColorId;
    private String remark;
    private String leadsId;
    private String provinceId;
    private String cityId;
    private String orgId;
    private String followupId;
    private String scheduleDateStr;
    private String followupDesc;
    private String recomName;
    private String recomMobile;
    private String optionPackage;
    private String activityId;
    private String appraiserId;
    private List<OpportunityRelationsBean> opportunityRelations;
    private List<OptionalPackageEntity.OptionListBean> ecCarOptions;//选装包

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getFollowupId() {
        return followupId;
    }

    public void setFollowupId(String followupId) {
        this.followupId = followupId;
    }

    public String getScheduleDateStr() {
        return scheduleDateStr;
    }

    public void setScheduleDateStr(String scheduleDateStr) {
        this.scheduleDateStr = scheduleDateStr;
    }

    public String getFollowupDesc() {
        return followupDesc;
    }

    public void setFollowupDesc(String followupDesc) {
        this.followupDesc = followupDesc;
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

    public String getAppraiserId() {
        return appraiserId;
    }

    public void setAppraiserId(String appraiserId) {
        this.appraiserId = appraiserId;
    }

    public List<OpportunityRelationsBean> getOpportunityRelations() {
        return opportunityRelations;
    }

    public void setOpportunityRelations(List<OpportunityRelationsBean> opportunityRelations) {
        this.opportunityRelations = opportunityRelations;
    }

    public List<OptionalPackageEntity.OptionListBean> getEcCarOptions() {
        return ecCarOptions;
    }

    public void setEcCarOptions(List<OptionalPackageEntity.OptionListBean> ecCarOptions) {
        this.ecCarOptions = ecCarOptions;
    }

    @Override
    public String toString() {
        return "OpportunityUpdateReqEntity{" +
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
                ", srcTypeId='" + srcTypeId + '\'' +
                ", oppOwner='" + oppOwner + '\'' +
                ", oppLevel='" + oppLevel + '\'' +
                ", isKeyuser='" + isKeyuser + '\'' +
                ", purchaseId='" + purchaseId + '\'' +
                ", propertyId='" + propertyId + '\'' +
                ", currentModel='" + currentModel + '\'' +
                ", modelYear='" + modelYear + '\'' +
                ", currentMileage='" + currentMileage + '\'' +
                ", budgetMin='" + budgetMin + '\'' +
                ", budgetMax='" + budgetMax + '\'' +
                ", paymentMode='" + paymentMode + '\'' +
                ", seriesId='" + seriesId + '\'' +
                ", carModelId='" + carModelId + '\'' +
                ", outsideColorId='" + outsideColorId + '\'' +
                ", insideColorId='" + insideColorId + '\'' +
                ", remark='" + remark + '\'' +
                ", leadsId='" + leadsId + '\'' +
                ", provinceId='" + provinceId + '\'' +
                ", cityId='" + cityId + '\'' +
                ", orgId='" + orgId + '\'' +
                ", followupId='" + followupId + '\'' +
                ", scheduleDateStr='" + scheduleDateStr + '\'' +
                ", followupDesc='" + followupDesc + '\'' +
                ", recomName='" + recomName + '\'' +
                ", recomMobile='" + recomMobile + '\'' +
                ", optionPackage='" + optionPackage + '\'' +
                ", activityId='" + activityId + '\'' +
                ", appraiserId='" + appraiserId + '\'' +
                ", opportunityRelations=" + opportunityRelations +
                ", ecCarOptions=" + ecCarOptions +
                '}';
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
}
