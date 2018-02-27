
package com.svw.dealerapp.entity.topic;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by ibm on 2017/4/20.
 */
public class CategoryEntity {

    private List<Category> data;

    public List<Category> getData() {
        return data;
    }

    public void setData(List<Category> data) {
        this.data = data;
    }

    public static class Category implements Parcelable {
        private int id;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeString(this.name);
        }

        public Category() {
        }

        protected Category(Parcel in) {
            this.id = in.readInt();
            this.name = in.readString();
        }

        public static final Creator<Category> CREATOR = new Creator<Category>() {
            @Override
            public Category createFromParcel(Parcel source) {
                return new Category(source);
            }

            @Override
            public Category[] newArray(int size) {
                return new Category[size];
            }
        };
    }
}
