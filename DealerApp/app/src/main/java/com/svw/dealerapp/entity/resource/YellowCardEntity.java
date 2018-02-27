package com.svw.dealerapp.entity.resource;

import java.util.List;

/**
 * Created by qinshi on 5/5/2017.
 */

public class YellowCardEntity {

    /**
     * data : [{"currentMileage":0,"custMobile":"12950788460","custWechat":"","infoId":"","carModelId":"3E92FZ","isKeyuser":"1","purchaseId":"13099","oppOwner":"","custDesc":"","remark":"","cityId":"","isWechat":"","budgetMax":0,"infoDesc":"","oppLevel":"C","orgId":"","insideColorId":"","leadsId":"","demandId":"","oppStatusId":"11510","custAge":"10030","propertyId":"13510","budgetMin":0,"channelId":"5c22cf44-6dee-4b18-bf00-f760f1c0c306","demandDesc":"","paymentMode":"","custGender":"1","updateTime":"","custName":"Y 曹","modelYear":"","provinceId":"","custTelephone":"","outsideColorId":"","createTime":"2017-05-17T14:22:01.000+0800","custEmail":"","srcTypeId":"11030","createUser":"15618910521","oppId":"8d8d52d9-63c3-4760-81cd-74cb3f73eec4","currentModel":""}]
     * page : {"totalCount":1,"pageIndex":1,"pageSize":10}
     */

    private PageBean page;
    private List<YellowCardInfoEntity> data;

    public PageBean getPage() {
        return page;
    }

    public void setPage(PageBean page) {
        this.page = page;
    }

    public List<YellowCardInfoEntity> getData() {
        return data;
    }

    public void setData(List<YellowCardInfoEntity> data) {
        this.data = data;
    }

    public static class YellowCardInfoEntity {

        private String carModelDescCn;
        private String carModelId;
        private String channelId;
        private String channelName;
        private String createTime;
        private String custGender;
        private String custMobile;
        private String custName;
        private String custTelephone;
        private String isKeyuser;
        private String leadsId;
        private String oppId;
        private String oppLevel;
        private String oppOwner;
        private String oppOwnerName;
        private String oppStatusId;
        private String oppStatusName;
        private String orgId;
        private String orgName;
        private String srcTypeId;
        private String srcTypeName;
        private String updateTime;
        private String appmTypeId;
        private String appmTypeName;
        private String appmStatusId;
        private String appmStatusName;
        private String outsideColorNameCn;
        private String insideColorNameCn;
        private String outsideColorId;
        private String insideColorId;
        private String seriesId;
        private String seriesName;
        private String followupId;

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

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
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

        public String getCustTelephone() {
            return custTelephone;
        }

        public void setCustTelephone(String custTelephone) {
            this.custTelephone = custTelephone;
        }

        public String getIsKeyuser() {
            return isKeyuser;
        }

        public void setIsKeyuser(String isKeyuser) {
            this.isKeyuser = isKeyuser;
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

        public String getAppmStatusName() {
            return appmStatusName;
        }

        public void setAppmStatusName(String appmStatusName) {
            this.appmStatusName = appmStatusName;
        }

        public String getOutsideColorNameCn() {
            return outsideColorNameCn;
        }

        public void setOutsideColorNameCn(String outsideColorNameCn) {
            this.outsideColorNameCn = outsideColorNameCn;
        }

        public String getInsideColorNameCn() {
            return insideColorNameCn;
        }

        public void setInsideColorNameCn(String insideColorNameCn) {
            this.insideColorNameCn = insideColorNameCn;
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

        public String getSeriesId() {
            return seriesId;
        }

        public void setSeriesId(String seriesId) {
            this.seriesId = seriesId;
        }

        public String getSeriesName() {
            return seriesName;
        }

        public void setSeriesName(String seriesName) {
            this.seriesName = seriesName;
        }

        public String getFollowupId() {
            return followupId;
        }

        public void setFollowupId(String followupId) {
            this.followupId = followupId;
        }
    }
}
