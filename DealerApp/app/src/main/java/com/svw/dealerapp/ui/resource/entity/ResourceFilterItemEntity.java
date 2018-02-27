package com.svw.dealerapp.ui.resource.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by qinshi on 5/12/2017.
 */

public class ResourceFilterItemEntity implements Parcelable {

    private String name;
    private String code;
    private boolean isAll;  //是否是全部
    private boolean isInitSelect = false; //是否在初始化的时候选中

    public ResourceFilterItemEntity(){

    }

    public ResourceFilterItemEntity(String name, String code, boolean isAll){
        this.name = name;
        this.code = code;
        this.isAll = isAll;
    }

    public ResourceFilterItemEntity(String name, String code, boolean isAll, boolean isInitSelect) {
        this.name = name;
        this.code = code;
        this.isAll = isAll;
        this.isInitSelect = isInitSelect;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isAll() {
        return isAll;
    }

    public void setAll(boolean all) {
        isAll = all;
    }

    public boolean isInitSelect() {
        return isInitSelect;
    }

    public void setInitSelect(boolean initSelect) {
        isInitSelect = initSelect;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.code);
        dest.writeByte(this.isAll ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isInitSelect ? (byte) 1 : (byte) 0);
    }

    protected ResourceFilterItemEntity(Parcel in) {
        this.name = in.readString();
        this.code = in.readString();
        this.isAll = in.readByte() != 0;
        this.isInitSelect = in.readByte() != 0;
    }

    public static final Parcelable.Creator<ResourceFilterItemEntity> CREATOR = new Parcelable.Creator<ResourceFilterItemEntity>() {
        @Override
        public ResourceFilterItemEntity createFromParcel(Parcel source) {
            return new ResourceFilterItemEntity(source);
        }

        @Override
        public ResourceFilterItemEntity[] newArray(int size) {
            return new ResourceFilterItemEntity[size];
        }
    };
}
