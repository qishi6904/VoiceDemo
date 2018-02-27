package com.svw.dealerapp.entity.login;

/**
 * Created by qinshi on 1/26/2018.
 */

public class SMUserPrivilegeDBEntity {

    private String accountId;           //用户ID
    private String uri;
    private String isCheckUri;
    private String resourceId;
    private String isCheckResource;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getIsCheckUri() {
        return isCheckUri;
    }

    public void setIsCheckUri(String isCheckUri) {
        this.isCheckUri = isCheckUri;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getIsCheckResource() {
        return isCheckResource;
    }

    public void setIsCheckResource(String isCheckResource) {
        this.isCheckResource = isCheckResource;
    }

    @Override
    public String toString() {
        return "SMUserPrivilegeDBEntity{" +
                "accountId='" + accountId + '\'' +
                ", uri='" + uri + '\'' +
                ", isCheckUri='" + isCheckUri + '\'' +
                ", resourceId='" + resourceId + '\'' +
                ", isCheckResource='" + isCheckResource + '\'' +
                '}';
    }
}
