package com.svw.dealerapp.entity.login;

/**
 * Created by lijinkui on 2018/1/25.
 */

public class SMRefreshSubTokenEntity {

    private String accessToken;//sub accessToken
    private String expireAt;
    private String refreshToken;//sub refreshToken
    private String refreshExpireAt;
    private String openId;
    private String clientId;
    private String scope;
    private String source;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getExpireAt() {
        return expireAt;
    }

    public void setExpireAt(String expireAt) {
        this.expireAt = expireAt;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getRefreshExpireAt() {
        return refreshExpireAt;
    }

    public void setRefreshExpireAt(String refreshExpireAt) {
        this.refreshExpireAt = refreshExpireAt;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return "SMRefreshSubTokenEntity{" +
                "accessToken='" + accessToken + '\'' +
                ", expireAt='" + expireAt + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                ", refreshExpireAt='" + refreshExpireAt + '\'' +
                ", openId='" + openId + '\'' +
                ", clientId='" + clientId + '\'' +
                ", scope='" + scope + '\'' +
                ", source='" + source + '\'' +
                '}';
    }
}
