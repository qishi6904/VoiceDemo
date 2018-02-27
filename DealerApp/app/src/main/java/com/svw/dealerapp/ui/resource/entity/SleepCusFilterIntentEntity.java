package com.svw.dealerapp.ui.resource.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.List;

/**
 * Created by qinshi on 8/14/2017.
 */

public class SleepCusFilterIntentEntity extends BaseFilterIntentEntity implements Parcelable {
    private Date dormancyEndTimeDate;
    private String dormancyStartTime;
    private boolean isDormancySelectAllTime = true;
    private Date suspendEndTimeDate;
    private String suspendStartTime;
    private boolean isSuspendSelectAllTime = true;
//    private String followupResult;

    public SleepCusFilterIntentEntity(List<ResourceFilterItemEntity> sourceSelectList,
                                      Date createEndTimeDate,
                                      String createStartTime,
                                      boolean isCreateSelectAllTime,
                                      Date dormancyEndTimeDate,
                                      String dormancyStartTime,
                                      boolean isDormancySelectAllTime,
                                      Date suspendEndTimeDate,
                                      String suspendStartTime,
                                      boolean isSuspendSelectAllTime,
                                      boolean isOnlyShowSelf,
                                      String carCode,
                                      String carName,
                                      String userId,
                                      String userName,
                                      String seriesCode,
                                      String seriesName) {
        this.sourceSelectList = sourceSelectList;
        this.createEndTimeDate = createEndTimeDate;
        this.createStartTime = createStartTime;
        this.isCreateSelectAllTime = isCreateSelectAllTime;
        this.dormancyEndTimeDate = dormancyEndTimeDate;
        this.dormancyStartTime = dormancyStartTime;
        this.isDormancySelectAllTime = isDormancySelectAllTime;
        this.suspendEndTimeDate = suspendEndTimeDate;
        this.suspendStartTime = suspendStartTime;
        this.isSuspendSelectAllTime = isSuspendSelectAllTime;
        this.isShowSelf = isOnlyShowSelf;
        this.carCode = carCode;
        this.carName = carName;
        this.userId = userId;
        this.userName = userName;
        this.seriesCode = seriesCode;
        this.seriesName = seriesName;
//        this.followupResult = followupResult;
    }

    public Date getDormancyEndTimeDate() {
        return dormancyEndTimeDate;
    }

    public void setDormancyEndTimeDate(Date dormancyEndTimeDate) {
        this.dormancyEndTimeDate = dormancyEndTimeDate;
    }

    public String getDormancyStartTime() {
        return dormancyStartTime;
    }

    public void setDormancyStartTime(String dormancyStartTime) {
        this.dormancyStartTime = dormancyStartTime;
    }

    public boolean isDormancySelectAllTime() {
        return isDormancySelectAllTime;
    }

    public void setDormancySelectAllTime(boolean dormancySelectAllTime) {
        isDormancySelectAllTime = dormancySelectAllTime;
    }

    public Date getSuspendEndTimeDate() {
        return suspendEndTimeDate;
    }

    public void setSuspendEndTimeDate(Date suspendEndTimeDate) {
        this.suspendEndTimeDate = suspendEndTimeDate;
    }

    public String getSuspendStartTime() {
        return suspendStartTime;
    }

    public void setSuspendStartTime(String suspendStartTime) {
        this.suspendStartTime = suspendStartTime;
    }

    public boolean isSuspendSelectAllTime() {
        return isSuspendSelectAllTime;
    }

    public void setSuspendSelectAllTime(boolean suspendSelectAllTime) {
        isSuspendSelectAllTime = suspendSelectAllTime;
    }

    //    public String getOppStatus() {
//        return followupResult;
//    }
//
//    public void setOppStatus(String followupResult) {
//        this.followupResult = followupResult;
//    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.sourceSelectList);
        dest.writeLong(this.createEndTimeDate != null ? this.createEndTimeDate.getTime() : -1);
        dest.writeString(this.createStartTime);
        dest.writeByte(this.isCreateSelectAllTime ? (byte) 1 : (byte) 0);
        dest.writeLong(this.dormancyEndTimeDate != null ? this.dormancyEndTimeDate.getTime() : -1);
        dest.writeString(this.dormancyStartTime);
        dest.writeByte(this.isDormancySelectAllTime ? (byte) 1 : (byte) 0);
        dest.writeLong(this.suspendEndTimeDate != null ? this.suspendEndTimeDate.getTime() : -1);
        dest.writeString(this.suspendStartTime);
        dest.writeByte(this.isSuspendSelectAllTime ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isShowSelf ? (byte) 1 : (byte) 0);
        dest.writeString(this.carCode);
        dest.writeString(this.carName);
        dest.writeString(this.userId);
        dest.writeString(this.userName);
        dest.writeString(this.seriesCode);
        dest.writeString(this.seriesName);
    }

    protected SleepCusFilterIntentEntity(Parcel in) {
        this.sourceSelectList = in.createTypedArrayList(ResourceFilterItemEntity.CREATOR);
        long tmpCreateEndTimeDate = in.readLong();
        this.createEndTimeDate = tmpCreateEndTimeDate == -1 ? null : new Date(tmpCreateEndTimeDate);
        this.createStartTime = in.readString();
        this.isCreateSelectAllTime = in.readByte() != 0;
        long tmpDormancyEndTimeDate = in.readLong();
        this.dormancyEndTimeDate = tmpDormancyEndTimeDate == -1 ? null : new Date(tmpDormancyEndTimeDate);
        this.dormancyStartTime = in.readString();
        this.isDormancySelectAllTime = in.readByte() != 0;
        long tmpSuspendEndTimeDate = in.readLong();
        this.suspendEndTimeDate = tmpSuspendEndTimeDate == -1 ? null : new Date(tmpSuspendEndTimeDate);
        this.suspendStartTime = in.readString();
        this.isSuspendSelectAllTime = in.readByte() != 0;
        this.isShowSelf = in.readByte() != 0;
        this.carCode = in.readString();
        this.carName = in.readString();
        this.userId = in.readString();
        this.userName = in.readString();
        this.seriesCode = in.readString();
        this.seriesName = in.readString();
    }

    public static final Parcelable.Creator<SleepCusFilterIntentEntity> CREATOR = new Parcelable.Creator<SleepCusFilterIntentEntity>() {
        @Override
        public SleepCusFilterIntentEntity createFromParcel(Parcel source) {
            return new SleepCusFilterIntentEntity(source);
        }

        @Override
        public SleepCusFilterIntentEntity[] newArray(int size) {
            return new SleepCusFilterIntentEntity[size];
        }
    };
}
