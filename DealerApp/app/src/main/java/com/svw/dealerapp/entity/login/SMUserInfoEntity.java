package com.svw.dealerapp.entity.login;

/**
 * Created by lijinkui on 2018/1/25.
 */

public class SMUserInfoEntity {
    /**
     * accountId : accountId
     * account : admin
     * displayName : admin
     * name : admin
     * status : status
     * email : email
     * mobile : 15968104133
     * avatarUrl : http://cma-it.oss-cn-hangzhou.aliyuncs.com/eg/3b694bfb-2897-40cc-8df5-41a7d7c4cd4c?Expires=17285241600&OSSAccessKeyId=LTAIJ8FK1pxYKRdp&Signature=meo0w/JIs8ByhWXjangSfsKWVsQ=
     * currentCategory : 09
     * currentRootCategory : 09
     * activate : false
     * districtId : districtId
     * orgId : orgId
     * orgName : orgName
     * depId : depId
     * depName : depName
     * shopId : shopId
     */

    private String accountId;
    private String account;
    private String displayName;
    private String name;
    private String status;
    private String email;
    private String mobile;
    private String avatarUrl;
    private String currentCategory;
    private String currentRootCategory;
    private boolean activate;
    private String districtId;
    private String orgId;
    private String orgName;
    private String depId;
    private String depName;
    private String shopId;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getCurrentCategory() {
        return currentCategory;
    }

    public void setCurrentCategory(String currentCategory) {
        this.currentCategory = currentCategory;
    }

    public String getCurrentRootCategory() {
        return currentRootCategory;
    }

    public void setCurrentRootCategory(String currentRootCategory) {
        this.currentRootCategory = currentRootCategory;
    }

    public boolean getActivate() {
        return activate;
    }

    public void setActivate(boolean activate) {
        this.activate = activate;
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
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

    public String getDepId() {
        return depId;
    }

    public void setDepId(String depId) {
        this.depId = depId;
    }

    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    @Override
    public String toString() {
        return "SMUserInfoEntity{" +
                "accountId='" + accountId + '\'' +
                ", account='" + account + '\'' +
                ", displayName='" + displayName + '\'' +
                ", name='" + name + '\'' +
                ", status='" + status + '\'' +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", currentCategory='" + currentCategory + '\'' +
                ", currentRootCategory='" + currentRootCategory + '\'' +
                ", activate=" + activate +
                ", districtId='" + districtId + '\'' +
                ", orgId='" + orgId + '\'' +
                ", orgName='" + orgName + '\'' +
                ", depId='" + depId + '\'' +
                ", depName='" + depName + '\'' +
                ", shopId='" + shopId + '\'' +
                '}';
    }
}
