package com.svw.dealerapp.entity.newcustomer;

import com.svw.dealerapp.entity.ReqEntity;

import java.util.List;

/**
 * Created by lijinkui on 2017/6/2.
 */

public class FollowupCreateReqEntity extends ReqEntity {

    /**
     * followupId : 2000
     * oppId : Lily
     * scheduleDateStr : 2017-05-12T10: 01: 06.006+0800
     * scheduleDesc : XXXXXXXXXXXXXXX
     * followupDate : 2017-05-12T10: 01: 06.006+0800
     * modeId : 8000
     * oppLevel : 0060
     * createUser : Alex
     * remark : CCCCCCCCC
     * results : [{"dictId":"00500","resultDesc":"XXXXXXXXX"},{"dictId":"00500","resultDesc":"XXXXXXXXX"}]
     */

    private String followupId;
    private String oppId;
    private String scheduleDateStr;
    private String scheduleDesc;
    private String modeId;
    private String nextModeId;
    private String oppLevel;
    private String remark;
    private String appmDateStr;
    private String appmTypeId;
    private String isFirst;
    private String isReminder;
    private String reminderInterval;
    private String leadsId;
    private String competitor;
    private String isNextFollowup;
    private String isTestdrive;
    private List<ResultsBean> results;

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

    public String getModeId() {
        return modeId;
    }

    public void setModeId(String modeId) {
        this.modeId = modeId;
    }

    public String getOppLevel() {
        return oppLevel;
    }

    public void setOppLevel(String oppLevel) {
        this.oppLevel = oppLevel;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getNextModeId() {
        return nextModeId;
    }

    public void setNextModeId(String nextModeId) {
        this.nextModeId = nextModeId;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public String getLeadsId() {
        return leadsId;
    }

    public void setLeadsId(String leadsId) {
        this.leadsId = leadsId;
    }

    public String getCompetitor() {
        return competitor;
    }

    public void setCompetitor(String competitor) {
        this.competitor = competitor;
    }

    public String getIsNextFollowup() {
        return isNextFollowup;
    }

    public void setIsNextFollowup(String isNextFollowup) {
        this.isNextFollowup = isNextFollowup;
    }

    public String getIsTestdrive() {
        return isTestdrive;
    }

    public void setIsTestdrive(String isTestdrive) {
        this.isTestdrive = isTestdrive;
    }

    public static class ResultsBean {
        /**
         * dictId : 00500
         * resultDesc : XXXXXXXXX
         */

        private String dictId;
        private String resultDesc;
        private String suspendDate;

        public String getDictId() {
            return dictId;
        }

        public void setDictId(String dictId) {
            this.dictId = dictId;
        }

        public String getResultDesc() {
            return resultDesc;
        }

        public void setResultDesc(String resultDesc) {
            this.resultDesc = resultDesc;
        }

        public String getSuspendDate() {
            return suspendDate;
        }

        public void setSuspendDate(String suspendDate) {
            this.suspendDate = suspendDate;
        }

        @Override
        public String toString() {
            return "ResultsBean{" +
                    "dictId='" + dictId + '\'' +
                    ", resultDesc='" + resultDesc + '\'' +
                    ", suspendDate='" + suspendDate + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "FollowupCreateReqEntity{" +
                "followupId='" + followupId + '\'' +
                ", oppId='" + oppId + '\'' +
                ", scheduleDateStr='" + scheduleDateStr + '\'' +
                ", scheduleDesc='" + scheduleDesc + '\'' +
                ", modeId='" + modeId + '\'' +
                ", nextModeId='" + nextModeId + '\'' +
                ", oppLevel='" + oppLevel + '\'' +
                ", remark='" + remark + '\'' +
                ", appmDateStr='" + appmDateStr + '\'' +
                ", appmTypeId='" + appmTypeId + '\'' +
                ", isFirst='" + isFirst + '\'' +
                ", isReminder='" + isReminder + '\'' +
                ", reminderInterval='" + reminderInterval + '\'' +
                ", leadsId='" + leadsId + '\'' +
                ", competitor='" + competitor + '\'' +
                ", isNextFollowup='" + isNextFollowup + '\'' +
                ", isTestdrive='" + isTestdrive + '\'' +
                ", results=" + results +
                '}';
    }
}
