package com.svw.dealerapp.ui.resource.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qinshi on 7/7/2017.
 */

public class TrafficFilterIntentEntity implements Parcelable {

    private ResourceFilterItemEntity timeFilterSelectEntity;
    private List<ResourceFilterItemEntity> statusFilterSelectList;

    public TrafficFilterIntentEntity(ResourceFilterItemEntity timeFilterSelectEntity, List<ResourceFilterItemEntity> statusFilterSelectList) {
        this.timeFilterSelectEntity = timeFilterSelectEntity;
        this.statusFilterSelectList = statusFilterSelectList;
    }

    public ResourceFilterItemEntity getTimeFilterSelectEntity() {
        return timeFilterSelectEntity;
    }

    public void setTimeFilterSelectEntity(ResourceFilterItemEntity timeFilterSelectEntity) {
        this.timeFilterSelectEntity = timeFilterSelectEntity;
    }

    public List<ResourceFilterItemEntity> getStatusFilterSelectList() {
        return statusFilterSelectList;
    }

    public void setStatusFilterSelectList(List<ResourceFilterItemEntity> statusFilterSelectList) {
        this.statusFilterSelectList = statusFilterSelectList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.timeFilterSelectEntity, flags);
        dest.writeTypedList(this.statusFilterSelectList);
    }

    protected TrafficFilterIntentEntity(Parcel in) {
        this.timeFilterSelectEntity = in.readParcelable(ResourceFilterItemEntity.class.getClassLoader());
        this.statusFilterSelectList = in.createTypedArrayList(ResourceFilterItemEntity.CREATOR);
    }

    public static final Parcelable.Creator<TrafficFilterIntentEntity> CREATOR = new Parcelable.Creator<TrafficFilterIntentEntity>() {
        @Override
        public TrafficFilterIntentEntity createFromParcel(Parcel source) {
            return new TrafficFilterIntentEntity(source);
        }

        @Override
        public TrafficFilterIntentEntity[] newArray(int size) {
            return new TrafficFilterIntentEntity[size];
        }
    };
}
