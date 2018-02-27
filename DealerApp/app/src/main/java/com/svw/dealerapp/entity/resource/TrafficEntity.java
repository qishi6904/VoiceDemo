package com.svw.dealerapp.entity.resource;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import java.util.List;

/**
 * Created by lijinkui on 2017/5/9.
 */

public class TrafficEntity {

    /**
     * page : {"totalCount":99,"pageIndex":1,"pageSize":5}
     * data : [{"leadsID":"2017050500001","custName":"赵女士","custGender":"1","custDescription":"购买意愿强烈，红色 途昂","leadsStatus":"0","createTime":"2017-05-05 10:11:23"},{"leadsID":"2017050500002","custName":"钱先生","custGender":"0","custDescription":"预算20万，贷款","leadsStatus":"2","createTime":"2017-05-04 10:11:23"}]
     */

    private PageBean page;
    private List<TrafficInfoEntity> data;

    public PageBean getPage() {
        return page;
    }

    public void setPage(PageBean page) {
        this.page = page;
    }

    public List<TrafficInfoEntity> getData() {
        return data;
    }

    public void setData(List<TrafficInfoEntity> data) {
        this.data = data;
    }

    public static class TrafficInfoEntity implements Parcelable {
        /**
         * leadsID : 2017050500001
         * custName : 赵女士
         * custGender : 1
         * custDescription : 购买意愿强烈，红色 途昂
         * leadsStatus : 0
         * createTime : 2017-05-05 10:11:23
         */


        private String leadsId;
        private String custName;
        private String custGender;
        private String custDescription;
        private String leadsStatus;
        private String createTime;
        private String oppId;
        private String custMobile;
        private String srcTypeId;
        private String channelId;

        private String[] custDescriptions;

        public String getLeadsId() {
            return leadsId;
        }

        public void setLeadsId(String leadsId) {
            this.leadsId = leadsId;
        }

        public String getCustName() {
            return custName;
        }

        public void setCustName(String custName) {
            this.custName = custName;
        }

        public String getCustGender() {
            return custGender;
        }

        public void setCustGender(String custGender) {
            this.custGender = custGender;
        }

        public String getCustDescription() {
            return custDescription;
        }

        public void setCustDescription(String custDescription) {
            this.custDescription = custDescription;
        }

        public String getLeadsStatus() {
            return leadsStatus;
        }

        public void setLeadsStatus(String leadsStatus) {
            this.leadsStatus = leadsStatus;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getOppId() {
            return oppId;
        }

        public void setOppId(String oppId) {
            this.oppId = oppId;
        }

        public String getCustMobile() {
            return custMobile;
        }

        public void setCustMobile(String custMobile) {
            this.custMobile = custMobile;
        }

        public String getSrcTypeId() {
            return srcTypeId;
        }

        public void setSrcTypeId(String srcTypeId) {
            this.srcTypeId = srcTypeId;
        }

        public String getChannelId() {
            return channelId;
        }

        public void setChannelId(String channelId) {
            this.channelId = channelId;
        }

        /**
         * 折分描述
         */
        public void splitDescriptions(){
            if(!TextUtils.isEmpty(custDescription)){
                custDescription = custDescription.replaceAll(" +", " ");
                if(custDescription.contains(" ")){
                    custDescriptions = custDescription.split(" ");
                }else {
                    custDescriptions = new String[]{custDescription};
                }
            }else {
                custDescriptions = null;
            }
        }

        public String[] getCustDescriptions(){
            return custDescriptions;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.leadsId);
            dest.writeString(this.custName);
            dest.writeString(this.custGender);
            dest.writeString(this.custDescription);
            dest.writeString(this.leadsStatus);
            dest.writeString(this.createTime);
            dest.writeString(this.oppId);
            dest.writeString(this.custMobile);
            dest.writeString(this.srcTypeId);
            dest.writeString(this.channelId);
            dest.writeStringArray(this.custDescriptions);
        }

        public TrafficInfoEntity() {
        }

        protected TrafficInfoEntity(Parcel in) {
            this.leadsId = in.readString();
            this.custName = in.readString();
            this.custGender = in.readString();
            this.custDescription = in.readString();
            this.leadsStatus = in.readString();
            this.createTime = in.readString();
            this.oppId = in.readString();
            this.custMobile = in.readString();
            this.srcTypeId = in.readString();
            this.channelId = in.readString();
            this.custDescriptions = in.createStringArray();
        }

        public static final Parcelable.Creator<TrafficInfoEntity> CREATOR = new Parcelable.Creator<TrafficInfoEntity>() {
            @Override
            public TrafficInfoEntity createFromParcel(Parcel source) {
                return new TrafficInfoEntity(source);
            }

            @Override
            public TrafficInfoEntity[] newArray(int size) {
                return new TrafficInfoEntity[size];
            }
        };
    }
}
