package com.svw.dealerapp.ui.task.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by qinshi on 1/2/2018.
 */

public class FollowUpFilterIntentEntity implements Parcelable {

    private String followupDateFrom;
    private String followupDateTo;
    private boolean isSelectAllTime;

    public FollowUpFilterIntentEntity(String followupDateFrom, String followupDateTo, boolean isSelectAllTime) {
        this.followupDateFrom = followupDateFrom;
        this.followupDateTo = followupDateTo;
        this.isSelectAllTime = isSelectAllTime;
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

    public boolean isSelectAllTime() {
        return isSelectAllTime;
    }

    public void setSelectAllTime(boolean selectAllTime) {
        isSelectAllTime = selectAllTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.followupDateFrom);
        dest.writeString(this.followupDateTo);
        dest.writeByte(this.isSelectAllTime ? (byte) 1 : (byte) 0);
    }

    protected FollowUpFilterIntentEntity(Parcel in) {
        this.followupDateFrom = in.readString();
        this.followupDateTo = in.readString();
        this.isSelectAllTime = in.readByte() != 0;
    }

    public static final Creator<FollowUpFilterIntentEntity> CREATOR = new Creator<FollowUpFilterIntentEntity>() {
        @Override
        public FollowUpFilterIntentEntity createFromParcel(Parcel source) {
            return new FollowUpFilterIntentEntity(source);
        }

        @Override
        public FollowUpFilterIntentEntity[] newArray(int size) {
            return new FollowUpFilterIntentEntity[size];
        }
    };
}
