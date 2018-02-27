package com.svw.dealerapp.entity.newcustomer;

import java.util.List;

/**
 * Created by qinshi on 7/14/2017.
 */

public class RemarkEntity {

    private List<RemarkEntityInfo> list;

    public List<RemarkEntityInfo> getList() {
        return list;
    }

    public void setList(List<RemarkEntityInfo> list) {
        this.list = list;
    }

    public class RemarkEntityInfo{
        private String id;
        private String oppId;
        private String oppComment;
        private String createUser;
        private String createUserName;
        private String createTime;
        private String updateTime;
        private String remark;
        private String createUserImage;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOppId() {
            return oppId;
        }

        public void setOppId(String oppId) {
            this.oppId = oppId;
        }

        public String getOppComment() {
            return oppComment;
        }

        public void setOppComment(String oppComment) {
            this.oppComment = oppComment;
        }

        public String getCreateUser() {
            return createUser;
        }

        public void setCreateUser(String createUser) {
            this.createUser = createUser;
        }

        public String getCreateUserName() {
            return createUserName;
        }

        public void setCreateUserName(String createUserName) {
            this.createUserName = createUserName;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getCreateUserImage() {
            return createUserImage;
        }

        public void setCreateUserImage(String createUserImage) {
            this.createUserImage = createUserImage;
        }
    }
}
