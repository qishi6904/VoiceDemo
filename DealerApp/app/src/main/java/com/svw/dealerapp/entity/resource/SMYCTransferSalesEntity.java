package com.svw.dealerapp.entity.resource;

/**
 * Created by qinshi on 1/30/2018.
 */

public class SMYCTransferSalesEntity {
    /**
     * accountId : 5008964956513895592
     * createdAt : 1516947839000
     * displayName : 顾玲玲
     * hiredAt : 1516947839000
     * id : 1000599
     * name : 顾玲玲
     * orgId : 397277642322059552
     * phone : 15190153430
     * status : 0
     * updatedAt : 1516947839000
     * userId : 397278938086507688
     */

    private String accountId;
    private String createdAt;
    private String displayName;
    private String hiredAt;
    private String id;
    private String name;
    private String orgId;
    private String phone;
    private String status;
    private String updatedAt;
    private String userId;

    private boolean isSelect;
    private boolean isAll;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getHiredAt() {
        return hiredAt;
    }

    public void setHiredAt(String hiredAt) {
        this.hiredAt = hiredAt;
    }

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

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isAll() {
        return isAll;
    }

    public void setAll(boolean all) {
        isAll = all;
    }
}
