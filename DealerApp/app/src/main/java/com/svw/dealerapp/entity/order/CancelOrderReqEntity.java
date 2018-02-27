package com.svw.dealerapp.entity.order;

import com.svw.dealerapp.entity.ReqEntity;

import java.util.List;

/**
 * Created by lijinkui on 2017/9/28.
 */

public class CancelOrderReqEntity extends ReqEntity {
    private String orderId;
    private String cancelDesc;//选填
    private String oppId;
    private String modeId;//联络方式
    private String followupRemark;//选填
    private String nextScheduleDesc;//跟进计划
    private String nextModeId;//跟进方式
    private String scheduleDateStr;//跟进时间
    private String competitor;//竞品
    private String isTestdrive;//试乘试驾
    private List<CancelOrderFollowupResult> followupResults;//跟进结果

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCancelDesc() {
        return cancelDesc;
    }

    public void setCancelDesc(String cancelDesc) {
        this.cancelDesc = cancelDesc;
    }

    public String getOppId() {
        return oppId;
    }

    public void setOppId(String oppId) {
        this.oppId = oppId;
    }

    public String getModeId() {
        return modeId;
    }

    public void setModeId(String modeId) {
        this.modeId = modeId;
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

    public String getNextModeId() {
        return nextModeId;
    }

    public void setNextModeId(String nextModeId) {
        this.nextModeId = nextModeId;
    }

    public String getScheduleDateStr() {
        return scheduleDateStr;
    }

    public void setScheduleDateStr(String scheduleDateStr) {
        this.scheduleDateStr = scheduleDateStr;
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

    public List<CancelOrderFollowupResult> getFollowupResults() {
        return followupResults;
    }

    public void setFollowupResults(List<CancelOrderFollowupResult> followupResults) {
        this.followupResults = followupResults;
    }

    public static class CancelOrderFollowupResult {
        private String dictId;
        private String dictName;//选填
        private String resultDesc;//选择战败时填写
        private String suspendDate;//选填
        private String orderId;//选填
        private String orderStatus;//选填

        public CancelOrderFollowupResult() {

        }

        public CancelOrderFollowupResult(String dictId, String resultDesc) {
            this.dictId = dictId;
            this.resultDesc = resultDesc;
        }

        public CancelOrderFollowupResult(String dictId, String resultDesc, String suspendDate) {
            this.dictId = dictId;
            this.resultDesc = resultDesc;
            this.suspendDate = suspendDate;
        }

        public String getDictId() {
            return dictId;
        }

        public void setDictId(String dictId) {
            this.dictId = dictId;
        }

        public String getDictName() {
            return dictName;
        }

        public void setDictName(String dictName) {
            this.dictName = dictName;
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
}
