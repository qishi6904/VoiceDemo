package com.svw.dealerapp.entity.login;

import java.util.List;

/**
 * Created by lijinkui on 2018/1/29.
 */

public class SMUserPrivilegeByAppIdEntity {

    private SMUserPrivilegeNodeEntity node;
    private List<SMUserPrivilegeByAppIdEntity> data;


    public SMUserPrivilegeNodeEntity getNode() {
        return node;
    }

    public void setNode(SMUserPrivilegeNodeEntity node) {
        this.node = node;
    }

    public List<SMUserPrivilegeByAppIdEntity> getData() {
        return data;
    }

    public void setData(List<SMUserPrivilegeByAppIdEntity> data) {
        this.data = data;
    }

    public static class SMUserPrivilegeNodeEntity {

        private String id;
        private String name;
        private String cnName;
        private String enName;
        private String category;
        private String type;
        private boolean isCheck;
        private String code;
        private String pageId;
        private String resourceId;
        private String uri;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCnName() {
            return cnName;
        }

        public void setCnName(String cnName) {
            this.cnName = cnName;
        }

        public String getEnName() {
            return enName;
        }

        public void setEnName(String enName) {
            this.enName = enName;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public boolean getIsCheck() {
            return isCheck;
        }

        public void setIsCheck(boolean isCheck) {
            this.isCheck = isCheck;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getPageId() {
            return pageId;
        }

        public void setPageId(String pageId) {
            this.pageId = pageId;
        }

        public String getResourceId() {
            return resourceId;
        }

        public void setResourceId(String resourceId) {
            this.resourceId = resourceId;
        }

        public String getUri() {
            return uri;
        }

        public void setUri(String uri) {
            this.uri = uri;
        }
    }
}
