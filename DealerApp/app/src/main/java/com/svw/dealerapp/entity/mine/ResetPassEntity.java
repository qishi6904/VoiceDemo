package com.svw.dealerapp.entity.mine;

/**
 * Created by lijinkui on 2017/6/16.
 */

@Deprecated
public class ResetPassEntity {

    /**
     * tokenInfo : {"accessToken":"5f65a302-0584-44ee-8577-0d0628813294","tokenType":"bearer","refreshToken":"d655a92a-e29c-4563-935d-8f60d343734e","expiresIn":43177,"scope":"read write"}
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
         * accessToken : 5f65a302-0584-44ee-8577-0d0628813294
         * tokenType : bearer
         * refreshToken : d655a92a-e29c-4563-935d-8f60d343734e
         * expiresIn : 43177
         * scope : read write
         */

        private String webTokent;
        private String accessToken;
        private String tokenType;
        private String refreshToken;
        private int expiresIn;
        private String scope;

        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }

        public String getTokenType() {
            return tokenType;
        }

        public void setTokenType(String tokenType) {
            this.tokenType = tokenType;
        }

        public String getRefreshToken() {
            return refreshToken;
        }

        public void setRefreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
        }

        public int getExpiresIn() {
            return expiresIn;
        }

        public void setExpiresIn(int expiresIn) {
            this.expiresIn = expiresIn;
        }

        public String getScope() {
            return scope;
        }

        public void setScope(String scope) {
            this.scope = scope;
        }

        public String getWebToken() {
            return webTokent;
        }

        public void setWebToken(String webToken) {
            this.webTokent = webToken;
        }
    }
}
