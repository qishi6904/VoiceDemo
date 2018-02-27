package com.svw.dealerapp.ui.resource.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.List;

/**
 * Created by qinshi on 8/14/2017.
 */

public class FailedCusFilterIntentEntity extends BaseFilterIntentEntity implements Parcelable {
    private List<ResourceFilterItemEntity> reasonSelectList;
    private Date failedEndTimeDate;
    private String failedStartTime;
    private boolean isFailedSelectAllTime = true;
//    private String followupResult;

    public FailedCusFilterIntentEntity(List<ResourceFilterItemEntity> sourceSelectList,
                                       List<ResourceFilterItemEntity> reasonSelectList,
                                       Date createEndTimeDate,
                                       String createStartTime,
                                       boolean isCreateSelectAllTime,
                                       Date failedEndTimeDate,
                                       String failedStartTime,
                                       boolean isFailedSelectAllTime,
                                       boolean isOnlyShowSelf,
                                       String carCode,
                                       String carName,
                                       String userId,
                                       String userName,
                                       String seriesCode,
                                       String seriesName) {
        this.sourceSelectList = sourceSelectList;
        this.reasonSelectList = reasonSelectList;
        this.createEndTimeDate = createEndTimeDate;
        this.createStartTime = createStartTime;
        this.isCreateSelectAllTime = isCreateSelectAllTime;
        this.failedEndTimeDate = failedEndTimeDate;
        this.failedStartTime = failedStartTime;
        this.isFailedSelectAllTime = isFailedSelectAllTime;
        this.isShowSelf = isOnlyShowSelf;
        this.carCode = carCode;
        this.carName = carName;
        this.userId = userId;
        this.userName = userName;
        this.seriesCode = seriesCode;
        this.seriesName = seriesName;
    }

    public List<ResourceFilterItemEntity> getReasonSelectList() {
        return reasonSelectList;
    }

    public void setReasonSelectList(List<ResourceFilterItemEntity> reasonSelectList) {
        this.reasonSelectList = reasonSelectList;
    }

    public Date getFailedEndTimeDate() {
        return failedEndTimeDate;
    }

    public void setFailedEndTimeDate(Date failedEndTimeDate) {
        this.failedEndTimeDate = failedEndTimeDate;
    }

    public String getFailedStartTime() {
        return failedStartTime;
    }

    public void setFailedStartTime(String failedStartTime) {
        this.failedStartTime = failedStartTime;
    }

    public boolean isFailedSelectAllTime() {
        return isFailedSelectAllTime;
    }

    public void setFailedSelectAllTime(boolean failedSelectAllTime) {
        isFailedSelectAllTime = failedSelectAllTime;
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
        dest.writeTypedList(this.reasonSelectList);
        dest.writeLong(this.createEndTimeDate != null ? this.createEndTimeDate.getTime() : -1);
        dest.writeString(this.createStartTime);
        dest.writeByte(this.isCreateSelectAllTime ? (byte) 1 : (byte) 0);
        dest.writeLong(this.failedEndTimeDate != null ? this.failedEndTimeDate.getTime() : -1);
        dest.writeString(this.failedStartTime);
        dest.writeByte(this.isFailedSelectAllTime ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isShowSelf ? (byte) 1 : (byte) 0);
        dest.writeString(this.carCode);
        dest.writeString(this.carName);
        dest.writeString(this.userId);
        dest.writeString(this.userName);
        dest.writeString(this.seriesCode);
        dest.writeString(this.seriesName);
    }

    public FailedCusFilterIntentEntity() {
    }

    protected FailedCusFilterIntentEntity(Parcel in) {
        this.sourceSelectList = in.createTypedArrayList(ResourceFilterItemEntity.CREATOR);
        this.reasonSelectList = in.createTypedArrayList(ResourceFilterItemEntity.CREATOR);
        long tmpCreateEndTimeDate = in.readLong();
        this.createEndTimeDate = tmpCreateEndTimeDate == -1 ? null : new Date(tmpCreateEndTimeDate);
        this.createStartTime = in.readString();
        this.isCreateSelectAllTime = in.readByte() != 0;
        long tmpDeliveryEndTimeDate = in.readLong();
        this.failedEndTimeDate = tmpDeliveryEndTimeDate == -1 ? null : new Date(tmpDeliveryEndTimeDate);
        this.failedStartTime = in.readString();
        this.isFailedSelectAllTime = in.readByte() != 0;
        this.isShowSelf = in.readByte() != 0;
        this.carCode = in.readString();
        this.carName = in.readString();
        this.userId = in.readString();
        this.userName = in.readString();
        this.seriesCode = in.readString();
        this.seriesName = in.readString();
    }

    public static final Creator<FailedCusFilterIntentEntity> CREATOR = new Creator<FailedCusFilterIntentEntity>() {
        @Override
        public FailedCusFilterIntentEntity createFromParcel(Parcel source) {
            return new FailedCusFilterIntentEntity(source);
        }

        @Override
        public FailedCusFilterIntentEntity[] newArray(int size) {
            return new FailedCusFilterIntentEntity[size];
        }
    };
}
