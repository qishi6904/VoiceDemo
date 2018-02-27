package com.svw.dealerapp.ui.task.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by qinshi on 6/6/2017.
 */

public class FollowUpFilterEntity implements Parcelable {

    /**
     * userId : XXXX
     * taskTypeIdNot : 16020
     * srcTypeIdNot : 11020
     */

    private String userId;
    private String taskTypeIdNot;
    private String srcTypeIdNot;
    private String followupDateFrom;
    private String followupDateTo;
    private String isHomePage;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTaskTypeIdNot() {
        return taskTypeIdNot;
    }

    public void setTaskTypeIdNot(String taskTypeIdNot) {
        this.taskTypeIdNot = taskTypeIdNot;
    }

    public String getSrcTypeIdNot() {
        return srcTypeIdNot;
    }

    public void setSrcTypeIdNot(String srcTypeIdNot) {
        this.srcTypeIdNot = srcTypeIdNot;
    }

    public String getFollowupDateFrom() {
        return followupDateFrom;
    }

    public void setFollowupDateFrom(String followupDateFrom) {
        this.followupDateFrom = followupDateFrom;
    }

    public String getFollowupDateTo() {
        return followupDateTo;
    }

    public void setFollowupDateTo(String followupDateTo) {
        this.followupDateTo = followupDateTo;
    }

    public String getIsHomePage() {
        return isHomePage;
    }

    public void setIsHomePage(String isHomePage) {
        this.isHomePage = isHomePage;
    }

    public FollowUpFilterEntity() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userId);
        dest.writeString(this.taskTypeIdNot);
        dest.writeString(this.srcTypeIdNot);
        dest.writeString(this.followupDateFrom);
        dest.writeString(this.followupDateTo);
        dest.writeString(this.isHomePage);
    }

    protected FollowUpFilterEntity(Parcel in) {
        this.userId = in.readString();
        this.taskTypeIdNot = in.readString();
        this.srcTypeIdNot = in.readString();
        this.followupDateFrom = in.readString();
        this.followupDateTo = in.readString();
        this.isHomePage = in.readString();
    }

    public static final Creator<FollowUpFilterEntity> CREATOR = new Creator<FollowUpFilterEntity>() {
        @Override
        public FollowUpFilterEntity createFromParcel(Parcel source) {
            return new FollowUpFilterEntity(source);
        }

        @Override
        public FollowUpFilterEntity[] newArray(int size) {
            return new FollowUpFilterEntity[size];
        }
    };
}
