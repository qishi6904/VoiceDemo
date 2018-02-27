package com.svw.dealerapp.entity.login;

/**
 * Created by lijinkui on 2018/1/25.
 */

public class SMRefreshTokenEntity {

    private String access_token;
    private String refresh_token;
    private String scope;
    private String expire_in;
    private String openid;
    private String source;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getExpire_in() {
        return expire_in;
    }

    public void setExpire_in(String expire_in) {
        this.expire_in = expire_in;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return "SMRefreshTokenEntity{" +
                "access_token='" + access_token + '\'' +
                ", refresh_token='" + refresh_token + '\'' +
                ", scope='" + scope + '\'' +
                ", expire_in='" + expire_in + '\'' +
                ", openid='" + openid + '\'' +
                ", source='" + source + '\'' +
                '}';
    }
}
