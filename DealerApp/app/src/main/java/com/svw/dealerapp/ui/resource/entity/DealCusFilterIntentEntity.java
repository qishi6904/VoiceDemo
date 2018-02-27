package com.svw.dealerapp.ui.resource.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.List;

/**
 * Created by qinshi on 8/14/2017.
 */

public class DealCusFilterIntentEntity extends BaseFilterIntentEntity implements Parcelable {
    private Date deliveryEndTimeDate;
    private String deliveryStartTime;
    private boolean isDeliverySelectAllTime = true;
//    private String followupResult;

    public DealCusFilterIntentEntity(List<ResourceFilterItemEntity> sourceSelectList,
                                     Date createEndTimeDate,
                                     String createStartTime,
                                     boolean isCreateSelectAllTime,
                                     Date deliveryEndTimeDate,
                                     String deliveryStartTime,
                                     boolean isDeliverySelectAllTime,
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
        this.deliveryEndTimeDate = deliveryEndTimeDate;
        this.deliveryStartTime = deliveryStartTime;
        this.isDeliverySelectAllTime = isDeliverySelectAllTime;
        this.isShowSelf = isOnlyShowSelf;
        this.carCode = carCode;
        this.carName = carName;
        this.userId = userId;
        this.userName = userName;
        this.seriesCode = seriesCode;
        this.seriesName = seriesName;
//        this.followupResult = followupResult;
    }

    public Date getDeliveryEndTimeDate() {
        return deliveryEndTimeDate;
    }

    public void setDeliveryEndTimeDate(Date deliveryEndTimeDate) {
        this.deliveryEndTimeDate = deliveryEndTimeDate;
    }

    public String getDeliveryStartTime() {
        return deliveryStartTime;
    }

    public void setDeliveryStartTime(String deliveryStartTime) {
        this.deliveryStartTime = deliveryStartTime;
    }

    public boolean isDeliverySelectAllTime() {
        return isDeliverySelectAllTime;
    }

    public void setDeliverySelectAllTime(boolean deliverySelectAllTime) {
        isDeliverySelectAllTime = deliverySelectAllTime;
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
        dest.writeLong(this.deliveryEndTimeDate != null ? this.deliveryEndTimeDate.getTime() : -1);
        dest.writeString(this.deliveryStartTime);
        dest.writeByte(this.isDeliverySelectAllTime ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isShowSelf ? (byte) 1 : (byte) 0);
        dest.writeString(this.carCode);
        dest.writeString(this.carName);
        dest.writeString(this.userId);
        dest.writeString(this.userName);
        dest.writeString(this.seriesCode);
        dest.writeString(this.seriesName);
    }

    public DealCusFilterIntentEntity() {
    }

    protected DealCusFilterIntentEntity(Parcel in) {
        this.sourceSelectList = in.createTypedArrayList(ResourceFilterItemEntity.CREATOR);
        long tmpCreateEndTimeDate = in.readLong();
        this.createEndTimeDate = tmpCreateEndTimeDate == -1 ? null : new Date(tmpCreateEndTimeDate);
        this.createStartTime = in.readString();
        this.isCreateSelectAllTime = in.readByte() != 0;
        long tmpDeliveryEndTimeDate = in.readLong();
        this.deliveryEndTimeDate = tmpDeliveryEndTimeDate == -1 ? null : new Date(tmpDeliveryEndTimeDate);
        this.deliveryStartTime = in.readString();
        this.isDeliverySelectAllTime = in.readByte() != 0;
        this.isShowSelf = in.readByte() != 0;
        this.carCode = in.readString();
        this.carName = in.readString();
        this.userId = in.readString();
        this.userName = in.readString();
        this.seriesName = in.readString();
        this.seriesCode = in.readString();
    }

    public static final Parcelable.Creator<DealCusFilterIntentEntity> CREATOR = new Parcelable.Creator<DealCusFilterIntentEntity>() {
        @Override
        public DealCusFilterIntentEntity createFromParcel(Parcel source) {
            return new DealCusFilterIntentEntity(source);
        }

        @Override
        public DealCusFilterIntentEntity[] newArray(int size) {
            return new DealCusFilterIntentEntity[size];
        }
    };
}
