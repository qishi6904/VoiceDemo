package com.svw.dealerapp.entity.order;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by qinshi on 11/22/2017.
 */

public class AppraiserEntity {

    private List<AppraiserInfoEntity> data;

    public List<AppraiserInfoEntity> getData() {
        return data;
    }

    public void setData(List<AppraiserInfoEntity> data) {
        this.data = data;
    }

    public static class AppraiserInfoEntity implements Parcelable {



        /**
         * appraiserId : 1000001079
         * appraiserName : 芦凯
         * phone : 11111111111
         * mobile : 15105208878
         * dlrStoreId : 1000000747
         * storeName : 徐州沪彭众达汽车销售服务有限公司
         * storeCode : 74309810_1
         * dlrCode : 74309810
         * orgId : 2210209
         * sentTime : null
         * createTime : 2017-11-23T11:15:34.000+0800
         * updateTime : 2017-11-23T11:15:34.000+0800
         * remark : null
         */

        private String appraiserId;
        private String appraiserName;
        private String phone;
        private String mobile;
        private String dlrStoreId;
        private String storeName;
        private String storeCode;
        private String dlrCode;
        private String orgId;
        private String sentTime;
        private String createTime;
        private String updateTime;
        private String remark;

        private boolean isSelect;

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        public String getAppraiserId() {
            return appraiserId;
        }

        public void setAppraiserId(String appraiserId) {
            this.appraiserId = appraiserId;
        }

        public String getAppraiserName() {
            return appraiserName;
        }

        public void setAppraiserName(String appraiserName) {
            this.appraiserName = appraiserName;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getDlrStoreId() {
            return dlrStoreId;
        }

        public void setDlrStoreId(String dlrStoreId) {
            this.dlrStoreId = dlrStoreId;
        }

        public String getStoreName() {
            return storeName;
        }

        public void setStoreName(String storeName) {
            this.storeName = storeName;
        }

        public String getStoreCode() {
            return storeCode;
        }

        public void setStoreCode(String storeCode) {
            this.storeCode = storeCode;
        }

        public String getDlrCode() {
            return dlrCode;
        }

        public void setDlrCode(String dlrCode) {
            this.dlrCode = dlrCode;
        }

        public String getOrgId() {
            return orgId;
        }

        public void setOrgId(String orgId) {
            this.orgId = orgId;
        }

        public Object getSentTime() {
            return sentTime;
        }

        public void setSentTime(String sentTime) {
            this.sentTime = sentTime;
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

        public Object getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.appraiserId);
            dest.writeString(this.appraiserName);
            dest.writeString(this.phone);
            dest.writeString(this.mobile);
            dest.writeString(this.dlrStoreId);
            dest.writeString(this.storeName);
            dest.writeString(this.storeCode);
            dest.writeString(this.dlrCode);
            dest.writeString(this.orgId);
            dest.writeString(this.sentTime);
            dest.writeString(this.createTime);
            dest.writeString(this.updateTime);
            dest.writeString(this.remark);
            dest.writeByte(this.isSelect ? (byte) 1 : (byte) 0);
        }

        public AppraiserInfoEntity() {
        }

        protected AppraiserInfoEntity(Parcel in) {
            this.appraiserId = in.readString();
            this.appraiserName = in.readString();
            this.phone = in.readString();
            this.mobile = in.readString();
            this.dlrStoreId = in.readString();
            this.storeName = in.readString();
            this.storeCode = in.readString();
            this.dlrCode = in.readString();
            this.orgId = in.readString();
            this.sentTime = in.readString();
            this.createTime = in.readString();
            this.updateTime = in.readString();
            this.remark = in.readString();
            this.isSelect = in.readByte() != 0;
        }

        public static final Parcelable.Creator<AppraiserInfoEntity> CREATOR = new Parcelable.Creator<AppraiserInfoEntity>() {
            @Override
            public AppraiserInfoEntity createFromParcel(Parcel source) {
                return new AppraiserInfoEntity(source);
            }

            @Override
            public AppraiserInfoEntity[] newArray(int size) {
                return new AppraiserInfoEntity[size];
            }
        };
    }
}
