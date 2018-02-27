package com.svw.dealerapp.entity.mine;

import java.util.List;

/**
 * Created by qinshi on 6/2/2017.
 */

public class ApproveWaitEntity {

    private List<ApproveWaitInfoEntity> approvals;

    public List<ApproveWaitInfoEntity> getApprovals() {
        return approvals;
    }

    public void setApprovals(List<ApproveWaitInfoEntity> approvals) {
        this.approvals = approvals;
    }

    public static class ApproveWaitInfoEntity {
        /**
         * approvalDesc :
         * strUpdateTime :
         * applicationOwner : 121212
         * businessId : 111111
         * remark :
         * applicationDesc :
         * approvalStatusId : 1
         * businessFlag :
         * approvalTypeId : 2
         * strApprovalTime :
         * approvalId : 00421f6c-a586-48df-9045-c86ebd9691da
         * createUser :
         * strCreateTime :
         * approvalUserId : 111
         * strApplicationTime :
         * approvalFlag : 1
         */

        private String approvalDesc;
        private String strUpdateTime;
        private String applicationOwner;
        private String businessId;
        private String remark;
        private String applicationDesc;
        private String approvalStatusId;
        private String businessFlag;
        private String approvalTypeId;
        private String strApprovalTime;
        private String approvalId;
        private String createUser;
        private String strCreateTime;
        private String approvalUserId;
        private String strApplicationTime;
        private String approvalFlag;
        private String custName;
        private String applicationOwnerName;
        private String oppId;

        public String getApprovalDesc() {
            return approvalDesc;
        }

        public void setApprovalDesc(String approvalDesc) {
            this.approvalDesc = approvalDesc;
        }

        public String getStrUpdateTime() {
            return strUpdateTime;
        }

        public void setStrUpdateTime(String strUpdateTime) {
            this.strUpdateTime = strUpdateTime;
        }

        public String getApplicationOwner() {
            return applicationOwner;
        }

        public void setApplicationOwner(String applicationOwner) {
            this.applicationOwner = applicationOwner;
        }

        public String getBusinessId() {
            return businessId;
        }

        public void setBusinessId(String businessId) {
            this.businessId = businessId;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getApplicationDesc() {
            return applicationDesc;
        }

        public void setApplicationDesc(String applicationDesc) {
            this.applicationDesc = applicationDesc;
        }

        public String getApprovalStatusId() {
            return approvalStatusId;
        }

        public void setApprovalStatusId(String approvalStatusId) {
            this.approvalStatusId = approvalStatusId;
        }

        public String getBusinessFlag() {
            return businessFlag;
        }

        public void setBusinessFlag(String businessFlag) {
            this.businessFlag = businessFlag;
        }

        public String getApprovalTypeId() {
            return approvalTypeId;
        }

        public void setApprovalTypeId(String approvalTypeId) {
            this.approvalTypeId = approvalTypeId;
        }

        public String getStrApprovalTime() {
            return strApprovalTime;
        }

        public void setStrApprovalTime(String strApprovalTime) {
            this.strApprovalTime = strApprovalTime;
        }

        public String getApprovalId() {
            return approvalId;
        }

        public void setApprovalId(String approvalId) {
            this.approvalId = approvalId;
        }

        public String getCreateUser() {
            return createUser;
        }

        public void setCreateUser(String createUser) {
            this.createUser = createUser;
        }

        public String getStrCreateTime() {
            return strCreateTime;
        }

        public void setStrCreateTime(String strCreateTime) {
            this.strCreateTime = strCreateTime;
        }

        public String getApprovalUserId() {
            return approvalUserId;
        }

        public void setApprovalUserId(String approvalUserId) {
            this.approvalUserId = approvalUserId;
        }

        public String getStrApplicationTime() {
            return strApplicationTime;
        }

        public void setStrApplicationTime(String strApplicationTime) {
            this.strApplicationTime = strApplicationTime;
        }

        public String getApprovalFlag() {
            return approvalFlag;
        }

        public void setApprovalFlag(String approvalFlag) {
            this.approvalFlag = approvalFlag;
        }

        public String getCustName() {
            return custName;
        }

        public void setCustName(String custName) {
            this.custName = custName;
        }

        public String getApplicationOwnerName() {
            return applicationOwnerName;
        }

        public void setApplicationOwnerName(String applicationOwnerName) {
            this.applicationOwnerName = applicationOwnerName;
        }

        public String getOppId() {
            return oppId;
        }

        public void setOppId(String oppId) {
            this.oppId = oppId;
        }
    }
}
