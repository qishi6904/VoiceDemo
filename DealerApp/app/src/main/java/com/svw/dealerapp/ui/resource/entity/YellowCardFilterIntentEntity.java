package com.svw.dealerapp.ui.resource.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.List;

/**
 * Created by qinshi on 7/7/2017.
 */

public class YellowCardFilterIntentEntity extends BaseFilterIntentEntity implements Parcelable {

    private List<ResourceFilterItemEntity> followResultSelectList;
    private List<ResourceFilterItemEntity> rankSelectList;
    private boolean isOpenOnlyVip = false;

    public YellowCardFilterIntentEntity(List<ResourceFilterItemEntity> sourceSelectList,
                                        List<ResourceFilterItemEntity> followResultSelectList,
                                        List<ResourceFilterItemEntity> rankSelectList,
                                        Date endTimeDate,
                                        String startTime,
                                        boolean isOpenOnlyVip,
                                        boolean isSelectAllTime,
                                        String carCode,
                                        String carName,
                                        String userId,
                                        String userName,
                                        boolean isOpenShowSelf,
                                        String seriesCode,
                                        String seriesName) {

        this.sourceSelectList = sourceSelectList;
        this.followResultSelectList = followResultSelectList;
        this.rankSelectList = rankSelectList;
        this.createEndTimeDate = endTimeDate;
        this.createStartTime = startTime;
        this.isOpenOnlyVip = isOpenOnlyVip;
        this.isCreateSelectAllTime = isSelectAllTime;
        this.carCode = carCode;
        this.carName = carName;
        this.userId = userId;
        this.userName = userName;
        this.isShowSelf = isOpenShowSelf;
        this.seriesCode = seriesCode;
        this.seriesName = seriesName;
    }

    public List<ResourceFilterItemEntity> getFollowResultSelectList() {
        return followResultSelectList;
    }

    public void setFollowResultSelectList(List<ResourceFilterItemEntity> followResultSelectList) {
        this.followResultSelectList = followResultSelectList;
    }

    public List<ResourceFilterItemEntity> getRankSelectList() {
        return rankSelectList;
    }

    public void setRankSelectList(List<ResourceFilterItemEntity> rankSelectList) {
        this.rankSelectList = rankSelectList;
    }

    public Date getCreateEndTimeDate() {
        return createEndTimeDate;
    }

    public void setCreateEndTimeDate(Date createEndTimeDate) {
        this.createEndTimeDate = createEndTimeDate;
    }

    public String getCreateStartTime() {
        return createStartTime;
    }

    public void setCreateStartTime(String createStartTime) {
        this.createStartTime = createStartTime;
    }

    public boolean isOpenOnlyVip() {
        return isOpenOnlyVip;
    }

    public void setOpenOnlyVip(boolean openOnlyVip) {
        isOpenOnlyVip = openOnlyVip;
    }

    public boolean isCreateSelectAllTime() {
        return isCreateSelectAllTime;
    }

    public void setCreateSelectAllTime(boolean createSelectAllTime) {
        isCreateSelectAllTime = createSelectAllTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.sourceSelectList);
        dest.writeTypedList(this.followResultSelectList);
        dest.writeTypedList(this.rankSelectList);
        dest.writeLong(this.createEndTimeDate != null ? this.createEndTimeDate.getTime() : -1);
        dest.writeString(this.createStartTime);
        dest.writeByte(this.isOpenOnlyVip ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isShowSelf ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isCreateSelectAllTime ? (byte) 1 : (byte) 0);
        dest.writeString(this.carCode);
        dest.writeString(this.carName);
        dest.writeString(this.userId);
        dest.writeString(this.userName);
        dest.writeString(this.seriesCode);
        dest.writeString(this.seriesName);
    }

    protected YellowCardFilterIntentEntity(Parcel in) {
        this.sourceSelectList = in.createTypedArrayList(ResourceFilterItemEntity.CREATOR);
        this.followResultSelectList = in.createTypedArrayList(ResourceFilterItemEntity.CREATOR);
        this.rankSelectList = in.createTypedArrayList(ResourceFilterItemEntity.CREATOR);
        long tmpEndTimeDate = in.readLong();
        this.createEndTimeDate = tmpEndTimeDate == -1 ? null : new Date(tmpEndTimeDate);
        this.createStartTime = in.readString();
        this.isOpenOnlyVip = in.readByte() != 0;
        this.isShowSelf = in.readByte() != 0;
        this.isCreateSelectAllTime = in.readByte() != 0;
        this.carCode = in.readString();
        this.carName = in.readString();
        this.userId = in.readString();
        this.userName = in.readString();
        this.seriesCode = in.readString();
        this.seriesName = in.readString();
    }

    public static final Creator<YellowCardFilterIntentEntity> CREATOR = new Creator<YellowCardFilterIntentEntity>() {
        @Override
        public YellowCardFilterIntentEntity createFromParcel(Parcel source) {
            return new YellowCardFilterIntentEntity(source);
        }

        @Override
        public YellowCardFilterIntentEntity[] newArray(int size) {
            return new YellowCardFilterIntentEntity[size];
        }
    };
}
