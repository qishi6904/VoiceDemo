package com.svw.dealerapp.entity.home;

import java.util.List;

/**
 * Created by qinshi on 6/3/2017.
 */

public class HomeEntity {


    /**
     * followupCount : 0
     * passengerCount : 0
     * ecommernceCount : 0
     * appointmentList : [{"appmTypeId":"17010","appmId":"300e6f13-5040-44b2-acef-f1042e45b0d7","custMobile":"12912960359","custWechat":"ln9398eyomchyvb","seqNo":"","carModelId":"3E92FZ","remark":"","reqTime":"","appmDateStr":"2017-07-06 20:00:00","appmMobile":"12090941183","isFirst":"","appmCust":"E 仲","appType":"","appmDesc":"","appId":"","carModelDescCn":"","appmOwner":"13186017669","appmTypeName":"","appmStatusName":"","appmStatusId":"17510","token":"","custTelephone":"05248302111","isReminder":"","appmGender":"1","custEmail":"73y2qp0tfq@126.com","createUser":"","oppId":"0a7b8d25-4380-40ec-ad63-e1f11025a3f9","reminderInterval":""}]
     */

    private String followupCount;
    private String passengerCount;
    private String ecommernceCount;
    private String followupDelayedCount;
    private String passengerDelayedCount;
    private String ecommernceDelayedCount;
    private String unapprovedCount;
    private PageBean page;
    private List<HomeInfoEntity> appointmentList;

    public PageBean getPage() {
        return page;
    }

    public void setPage(PageBean page) {
        this.page = page;
    }

    public List<HomeInfoEntity> getAppointmentList() {
        return appointmentList;
    }

    public void setAppointmentList(List<HomeInfoEntity> appointmentList) {
        this.appointmentList = appointmentList;
    }

    public String getFollowupCount() {
        return followupCount;
    }

    public void setFollowupCount(String followupCount) {
        this.followupCount = followupCount;
    }

    public String getPassengerCount() {
        return passengerCount;
    }

    public void setPassengerCount(String passengerCount) {
        this.passengerCount = passengerCount;
    }

    public String getEcommernceCount() {
        return ecommernceCount;
    }

    public void setEcommernceCount(String ecommernceCount) {
        this.ecommernceCount = ecommernceCount;
    }

    public String getFollowupDelayedCount() {
        return followupDelayedCount;
    }

    public void setFollowupDelayedCount(String followupDelayedCount) {
        this.followupDelayedCount = followupDelayedCount;
    }

    public String getPassengerDelayedCount() {
        return passengerDelayedCount;
    }

    public void setPassengerDelayedCount(String passengerDelayedCount) {
        this.passengerDelayedCount = passengerDelayedCount;
    }

    public String getEcommernceDelayedCount() {
        return ecommernceDelayedCount;
    }

    public void setEcommernceDelayedCount(String ecommernceDelayedCount) {
        this.ecommernceDelayedCount = ecommernceDelayedCount;
    }

    public String getUnapprovedCount() {
        return unapprovedCount;
    }

    public void setUnapprovedCount(String unapprovedCount) {
        this.unapprovedCount = unapprovedCount;
    }

    public static class HomeInfoEntity {
        /**
         * appmTypeId : 17010
         * appmId : 300e6f13-5040-44b2-acef-f1042e45b0d7
         * custMobile : 12912960359
         * custWechat : ln9398eyomchyvb
         * seqNo :
         * carModelId : 3E92FZ
         * remark :
         * reqTime :
         * appmDateStr : 2017-07-06 20:00:00
         * appmMobile : 12090941183
         * isFirst :
         * appmCust : E 仲
         * appType :
         * appmDesc :
         * appId :
         * carModelDescCn :
         * appmOwner : 13186017669
         * appmTypeName :
         * appmStatusName :
         * appmStatusId : 17510
         * token :
         * custTelephone : 05248302111
         * isReminder :
         * appmGender : 1
         * custEmail : 73y2qp0tfq@126.com
         * createUser :
         * oppId : 0a7b8d25-4380-40ec-ad63-e1f11025a3f9
         * reminderInterval :
         */

        private String appmTypeId;
        private String appmId;
        private String custMobile;
        private String custWechat;
        private String seqNo;
        private String carModelId;
        private String remark;
        private String reqTime;
        private String appmDateStr;
        private String appmMobile;
        private String isFirst;
        private String appmCust;
        private String appType;
        private String appmDesc;
        private String appId;
        private String carModelDescCn;
        private String appmOwner;
        private String appmTypeName;
        private String appmStatusName;
        private String appmStatusId;
        private String token;
        private String custTelephone;
        private String isReminder;
        private String appmGender;
        private String custEmail;
        private String createUser;
        private String oppId;
        private String reminderInterval;
        private String isKeyuser;
        private String seriesId;
        private String seriesName;

        private boolean isShowEdit;     //是否显示编辑的两个按钮

        public String getAppmTypeId() {
            return appmTypeId;
        }

        public void setAppmTypeId(String appmTypeId) {
            this.appmTypeId = appmTypeId;
        }

        public String getAppmId() {
            return appmId;
        }

        public void setAppmId(String appmId) {
            this.appmId = appmId;
        }

        public String getCustMobile() {
            return custMobile;
        }

        public void setCustMobile(String custMobile) {
            this.custMobile = custMobile;
        }

        public String getCustWechat() {
            return custWechat;
        }

        public void setCustWechat(String custWechat) {
            this.custWechat = custWechat;
        }

        public String getSeqNo() {
            return seqNo;
        }

        public void setSeqNo(String seqNo) {
            this.seqNo = seqNo;
        }

        public String getCarModelId() {
            return carModelId;
        }

        public void setCarModelId(String carModelId) {
            this.carModelId = carModelId;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getReqTime() {
            return reqTime;
        }

        public void setReqTime(String reqTime) {
            this.reqTime = reqTime;
        }

        public String getAppmDateStr() {
            return appmDateStr;
        }

        public void setAppmDateStr(String appmDateStr) {
            this.appmDateStr = appmDateStr;
        }

        public String getAppmMobile() {
            return appmMobile;
        }

        public void setAppmMobile(String appmMobile) {
            this.appmMobile = appmMobile;
        }

        public String getIsFirst() {
            return isFirst;
        }

        public void setIsFirst(String isFirst) {
            this.isFirst = isFirst;
        }

        public String getAppmCust() {
            return appmCust;
        }

        public void setAppmCust(String appmCust) {
            this.appmCust = appmCust;
        }

        public String getAppType() {
            return appType;
        }

        public void setAppType(String appType) {
            this.appType = appType;
        }

        public String getAppmDesc() {
            return appmDesc;
        }

        public void setAppmDesc(String appmDesc) {
            this.appmDesc = appmDesc;
        }

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

        public String getCarModelDescCn() {
            return carModelDescCn;
        }

        public void setCarModelDescCn(String carModelDescCn) {
            this.carModelDescCn = carModelDescCn;
        }

        public String getAppmOwner() {
            return appmOwner;
        }

        public void setAppmOwner(String appmOwner) {
            this.appmOwner = appmOwner;
        }

        public String getAppmTypeName() {
            return appmTypeName;
        }

        public void setAppmTypeName(String appmTypeName) {
            this.appmTypeName = appmTypeName;
        }

        public String getAppmStatusName() {
            return appmStatusName;
        }

        public void setAppmStatusName(String appmStatusName) {
            this.appmStatusName = appmStatusName;
        }

        public String getAppmStatusId() {
            return appmStatusId;
        }

        public void setAppmStatusId(String appmStatusId) {
            this.appmStatusId = appmStatusId;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getCustTelephone() {
            return custTelephone;
        }

        public void setCustTelephone(String custTelephone) {
            this.custTelephone = custTelephone;
        }

        public String getIsReminder() {
            return isReminder;
        }

        public void setIsReminder(String isReminder) {
            this.isReminder = isReminder;
        }

        public String getAppmGender() {
            return appmGender;
        }

        public void setAppmGender(String appmGender) {
            this.appmGender = appmGender;
        }

        public String getCustEmail() {
            return custEmail;
        }

        public void setCustEmail(String custEmail) {
            this.custEmail = custEmail;
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

        public String getReminderInterval() {
            return reminderInterval;
        }

        public void setReminderInterval(String reminderInterval) {
            this.reminderInterval = reminderInterval;
        }

        public String getIsKeyuser() {
            return isKeyuser;
        }

        public void setIsKeyuser(String isKeyuser) {
            this.isKeyuser = isKeyuser;
        }

        public boolean isShowEdit() {
            return isShowEdit;
        }

        public void setShowEdit(boolean showEdit) {
            isShowEdit = showEdit;
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
    }

}
