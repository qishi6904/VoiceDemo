package com.svw.dealerapp.entity.mine;

import java.util.List;

/**
 * Created by qinshi on 6/1/2017.
 */

public class NotificationEntity {


    private List<NotificationInfoEntity> noticeList;

    public List<NotificationInfoEntity> getNoticeList() {
        return noticeList;
    }

    public void setNoticeList(List<NotificationInfoEntity> noticeList) {
        this.noticeList = noticeList;
    }

    public static class NotificationInfoEntity {
        /**
         * notifID : C16FA0B4-0728-4E99-9182-0B73B8392E50
         * messageId : 500032
         * deviceID : ea185595-4b35-4bf1-b8da-2f6036b12667
         * deviceType : 102
         * appName : xxx
         * channelType : 100
         * notifUser : loginID
         * notifDate : 2016-09-02 02:11:23
         * notifDesc : 您一条新的客流/线索信息需要跟进。
         * notifLink : 1001
         * notifStatus : 0
         * processDate : null
         * createUser : Admin
         * updateTime : null
         * remark : null
         * noticeSender : 牛哄哄
         * noticeTitle : 您一条新的客流/线索信息需要跟进。
         */

        private String notifID;
        private String messageId;
        private String deviceID;
        private String deviceType;
        private String appName;
        private String channelType;
        private String notifUser;
        private String notifDate;
        private String notifDesc;
        private String notifLink;
        private String notifStatus;
        private Object processDate;
        private String createUser;
        private Object updateTime;
        private Object remark;
        private String noticeSender;
        private String noticeTitle;
        private String activity;
        private String oppId;


        //for UI setting
        private boolean isSelect;
        private boolean isShowSingleLine = false;

        public String getNotifID() {
            return notifID;
        }

        public void setNotifID(String notifID) {
            this.notifID = notifID;
        }

        public String getMessageId() {
            return messageId;
        }

        public void setMessageId(String messageId) {
            this.messageId = messageId;
        }

        public String getDeviceID() {
            return deviceID;
        }

        public void setDeviceID(String deviceID) {
            this.deviceID = deviceID;
        }

        public String getDeviceType() {
            return deviceType;
        }

        public void setDeviceType(String deviceType) {
            this.deviceType = deviceType;
        }

        public String getAppName() {
            return appName;
        }

        public void setAppName(String appName) {
            this.appName = appName;
        }

        public String getChannelType() {
            return channelType;
        }

        public void setChannelType(String channelType) {
            this.channelType = channelType;
        }

        public String getNotifUser() {
            return notifUser;
        }

        public void setNotifUser(String notifUser) {
            this.notifUser = notifUser;
        }

        public String getNotifDate() {
            return notifDate;
        }

        public void setNotifDate(String notifDate) {
            this.notifDate = notifDate;
        }

        public String getNotifDesc() {
            return notifDesc;
        }

        public void setNotifDesc(String notifDesc) {
            this.notifDesc = notifDesc;
        }

        public String getNotifLink() {
            return notifLink;
        }

        public void setNotifLink(String notifLink) {
            this.notifLink = notifLink;
        }

        public String getNotifStatus() {
            return notifStatus;
        }

        public void setNotifStatus(String notifStatus) {
            this.notifStatus = notifStatus;
        }

        public Object getProcessDate() {
            return processDate;
        }

        public void setProcessDate(Object processDate) {
            this.processDate = processDate;
        }

        public String getCreateUser() {
            return createUser;
        }

        public void setCreateUser(String createUser) {
            this.createUser = createUser;
        }

        public Object getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(Object updateTime) {
            this.updateTime = updateTime;
        }

        public Object getRemark() {
            return remark;
        }

        public void setRemark(Object remark) {
            this.remark = remark;
        }

        public String getNoticeSender() {
            return noticeSender;
        }

        public void setNoticeSender(String noticeSender) {
            this.noticeSender = noticeSender;
        }

        public String getNoticeTitle() {
            return noticeTitle;
        }

        public void setNoticeTitle(String noticeTitle) {
            this.noticeTitle = noticeTitle;
        }

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        public boolean isShowSingleLine() {
            return isShowSingleLine;
        }

        public void setShowSingleLine(boolean showSingleLine) {
            isShowSingleLine = showSingleLine;
        }

        public String getActivity() {
            return activity;
        }

        public void setActivity(String activity) {
            this.activity = activity;
        }

        public String getOppId() {
            return oppId;
        }

        public void setOppId(String oppId) {
            this.oppId = oppId;
        }
    }
}
