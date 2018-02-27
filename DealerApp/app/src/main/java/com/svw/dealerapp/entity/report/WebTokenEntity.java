package com.svw.dealerapp.entity.report;

/**
 * Created by lijinkui on 2017/8/1.
 */

@Deprecated
public class WebTokenEntity {
    /**
     * tokenInfo : {"accessToken":"6b3f0acd-e2ea-4188-ab64-602f1f5a791f","tokenType":null,"webTokent":"IUZ8uyto4tTCUb5XqTkq6kFvRtjJBHm6vOOGppUqygnMmSqOcfRhXQ0PYSDhn@XL@ED8BsLGO04@XL@zM=","refreshToken":null,"expiresIn":0,"scope":null}
     */

    private TokenInfoBean tokenInfo;

    public TokenInfoBean getTokenInfo() {
        return tokenInfo;
    }

    public void setTokenInfo(TokenInfoBean tokenInfo) {
        this.tokenInfo = tokenInfo;
    }

    public static class TokenInfoBean {
        /**
         * accessToken : 6b3f0acd-e2ea-4188-ab64-602f1f5a791f
         * tokenType : null
         * webTokent : IUZ8uyto4tTCUb5XqTkq6kFvRtjJBHm6vOOGppUqygnMmSqOcfRhXQ0PYSDhn@XL@ED8BsLGO04@XL@zM=
         * refreshToken : null
         * expiresIn : 0
         * scope : null
         */

        private String accessToken;
        private Object tokenType;
        private String webTokent;
        private Object refreshToken;
        private int expiresIn;
        private Object scope;

        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }

        public Object getTokenType() {
            return tokenType;
        }

        public void setTokenType(Object tokenType) {
            this.tokenType = tokenType;
        }

        public String getWebTokent() {
            return webTokent;
        }

        public void setWebTokent(String webTokent) {
            this.webTokent = webTokent;
        }

        public Object getRefreshToken() {
            return refreshToken;
        }

        public void setRefreshToken(Object refreshToken) {
            this.refreshToken = refreshToken;
        }

        public int getExpiresIn() {
            return expiresIn;
        }

        public void setExpiresIn(int expiresIn) {
            this.expiresIn = expiresIn;
        }

        public Object getScope() {
            return scope;
        }

        public void setScope(Object scope) {
            this.scope = scope;
        }
    }
}
