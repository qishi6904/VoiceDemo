package com.svw.dealerapp.entity.login;

/**
 * Created by lijinkui on 2017/5/9.
 */

@Deprecated
public class LoginEntity {

    /**
     * userInfo : {"userId":"18899999999","orgId":"167dbe79-8f56-4333-ae68-c64f1ec87a84","pwd":"e10adc3949ba59abbe56e057f20f883e","name":"Android李用","gender":"1","image":"http://svm201704271433.oss-cn-shanghai.aliyuncs.com/82FC4CDFE7224A3C9A678BD9DD00845F.jpg","dutyId":"20100030","mobile":"18899999999","telephone":"","wechat":"","email":"","status":"1","createUser":"API","createTime":"2017-06-13T09:37:04.000+0000","updateTime":"2017-06-15T07:21:01.000+0000","remark":"","orgName":"安亭经销商","orgAddress":"安亭地铁站1号口","orgProvinceId":"1001","orgCityId":"1009"}
     * roleIds : 100
     * tokenInfo : {"accessToken":"5f65a302-0584-44ee-8577-0d0628813294","tokenType":"bearer","refreshToken":"d655a92a-e29c-4563-935d-8f60d343734e","expiresIn":43177,"scope":"read write"}
     */

    private UserInfoBean userInfo;
    private String roleIds;
    private TokenInfoBean tokenInfo;

    public UserInfoBean getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoBean userInfo) {
        this.userInfo = userInfo;
    }

    public String getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(String roleIds) {
        this.roleIds = roleIds;
    }

    public TokenInfoBean getTokenInfo() {
        return tokenInfo;
    }

    public void setTokenInfo(TokenInfoBean tokenInfo) {
        this.tokenInfo = tokenInfo;
    }

    public static class UserInfoBean {
        /**
         * userId : 18899999999
         * orgId : 167dbe79-8f56-4333-ae68-c64f1ec87a84
         * pwd : e10adc3949ba59abbe56e057f20f883e
         * name : Android李用
         * gender : 1
         * image : http://svm201704271433.oss-cn-shanghai.aliyuncs.com/82FC4CDFE7224A3C9A678BD9DD00845F.jpg
         * dutyId : 20100030
         * mobile : 18899999999
         * telephone :
         * wechat :
         * email :
         * status : 1
         * createUser : API
         * createTime : 2017-06-13T09:37:04.000+0000
         * updateTime : 2017-06-15T07:21:01.000+0000
         * remark :
         * orgName : 安亭经销商
         * orgAddress : 安亭地铁站1号口
         * orgProvinceId : 1001
         * orgCityId : 1009
         */

        private String userId;
        private String orgId;
        private String pwd;
        private String name;
        private String gender;
        private String image;
        private String dutyId;
        private String mobile;
        private String telephone;
        private String wechat;
        private String email;
        private String status;
        private String createUser;
        private String createTime;
        private String updateTime;
        private String remark;
        private String orgName;
        private String orgAddress;
        private String orgProvinceId;
        private String orgCityId;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getOrgId() {
            return orgId;
        }

        public void setOrgId(String orgId) {
            this.orgId = orgId;
        }

        public String getPwd() {
            return pwd;
        }

        public void setPwd(String pwd) {
            this.pwd = pwd;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getDutyId() {
            return dutyId;
        }

        public void setDutyId(String dutyId) {
            this.dutyId = dutyId;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }

        public String getWechat() {
            return wechat;
        }

        public void setWechat(String wechat) {
            this.wechat = wechat;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCreateUser() {
            return createUser;
        }

        public void setCreateUser(String createUser) {
            this.createUser = createUser;
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

        public String getOrgName() {
            return orgName;
        }

        public void setOrgName(String orgName) {
            this.orgName = orgName;
        }

        public String getOrgAddress() {
            return orgAddress;
        }

        public void setOrgAddress(String orgAddress) {
            this.orgAddress = orgAddress;
        }

        public String getOrgProvinceId() {
            return orgProvinceId;
        }

        public void setOrgProvinceId(String orgProvinceId) {
            this.orgProvinceId = orgProvinceId;
        }

        public String getOrgCityId() {
            return orgCityId;
        }

        public void setOrgCityId(String orgCityId) {
            this.orgCityId = orgCityId;
        }
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
