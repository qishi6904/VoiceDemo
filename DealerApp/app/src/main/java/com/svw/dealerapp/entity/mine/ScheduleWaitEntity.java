package com.svw.dealerapp.entity.mine;

import java.util.List;

/**
 * Created by qinshi on 6/2/2017.
 */

public class ScheduleWaitEntity {


    /**
     * followupCount : 0
     * passengerCount : 0
     * ecommernceCount : 0
     * appointmentList : [{"appmTypeId":"17020","appmId":"9eaed4ba-81a8-4b12-83a8-10a5177a9308","custMobile":"12430183251","custWechat":"03tyv8o0j20hbli","seqNo":"","carModelId":"3E92FZ","remark":"","reqTime":"","appmDateStr":"2017-06-09 16:19:21","appmMobile":"12430183251","isFirst":"1","appmCust":"Mr 臧","appType":"","appmDesc":"","appId":"","carModelDescCn":"","appmOwner":"15618910521","appmTypeName":"","appmStatusName":"","appmStatusId":"0","token":"","custTelephone":"02789777628","isReminder":"1","appmGender":"0","custEmail":"jz74kuhdc0@126.com","createUser":"","oppId":"c180d372-bbc9-45ca-963d-3cdb55dede84","reminderInterval":"2h"},{"appmTypeId":"17040","appmId":"21e36165-dc48-4ea7-a6c2-05eb5c6f09f0","custMobile":"12068304753","custWechat":"z537tpn8xeztuoy","seqNo":"","carModelId":"3E92FZ","remark":"","reqTime":"","appmDateStr":"2017-06-07 09:17:15","appmMobile":"12068304753","isFirst":"0","appmCust":"ZB 李","appType":"","appmDesc":"","appId":"","carModelDescCn":"","appmOwner":"15618910521","appmTypeName":"","appmStatusName":"","appmStatusId":"0","token":"","custTelephone":"01688504041","isReminder":"0","appmGender":"1","custEmail":"c9mnnvjkl0@126.com","createUser":"","oppId":"f52fb882-ffa1-4695-8573-642df2e6f434","reminderInterval":"5h"}]
     */

    private PageBean page;
    private List<ScheduleWaitInfoEntity> appointmentList;

    public List<ScheduleWaitInfoEntity> getAppointmentList() {
        return appointmentList;
    }

    public void setAppointmentList(List<ScheduleWaitInfoEntity> appointmentList) {
        this.appointmentList = appointmentList;
    }

    public PageBean getPage() {
        return page;
    }

    public void setPage(PageBean page) {
        this.page = page;
    }

    public static class ScheduleWaitInfoEntity {
        /**
         * appmTypeId : 17020
         * appmId : 9eaed4ba-81a8-4b12-83a8-10a5177a9308
         * custMobile : 12430183251
         * custWechat : 03tyv8o0j20hbli
         * seqNo :
         * carModelId : 3E92FZ
         * remark :
         * reqTime :
         * appmDateStr : 2017-06-09 16:19:21
         * appmMobile : 12430183251
         * isFirst : 1
         * appmCust : Mr 臧
         * appType :
         * appmDesc :
         * appId :
         * carModelDescCn :
         * appmOwner : 15618910521
         * appmTypeName :
         * appmStatusName :
         * appmStatusId : 0
         * token :
         * custTelephone : 02789777628
         * isReminder : 1
         * appmGender : 0
         * custEmail : jz74kuhdc0@126.com
         * createUser :
         * oppId : c180d372-bbc9-45ca-963d-3cdb55dede84
         * reminderInterval : 2h
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

        private boolean isShowEdit;

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
