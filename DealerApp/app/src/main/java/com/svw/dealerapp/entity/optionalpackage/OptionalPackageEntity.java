package com.svw.dealerapp.entity.optionalpackage;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lijinkui on 2017/11/30.
 */

public class OptionalPackageEntity {

    private List<OptionListBean> optionList;

    public List<OptionListBean> getOptionList() {
        return optionList;
    }

    public void setOptionList(List<OptionListBean> optionList) {
        this.optionList = optionList;
    }

    public static class OptionListBean implements Parcelable, Serializable {
        /**
         * optionCode : PTG
         * optionNameEn :
         * optionNameCn : 舒雅选装包，标配7座
         * list : [{"optionCode":"PT8","optionNameEn":"","optionNameCn":"集成式儿童座椅"},{"optionCode":"PTI","optionNameEn":"","optionNameCn":"舒雅6座选装包"}]
         */

        private String optionCode;
        private String optionNameEn;
        private String optionNameCn;
        private List<ListBean> list;

        private boolean isSelect = false;

        public String getOptionCode() {
            return optionCode;
        }

        public void setOptionCode(String optionCode) {
            this.optionCode = optionCode;
        }

        public String getOptionNameEn() {
            return optionNameEn;
        }

        public void setOptionNameEn(String optionNameEn) {
            this.optionNameEn = optionNameEn;
        }

        public String getOptionNameCn() {
            return optionNameCn;
        }

        public void setOptionNameCn(String optionNameCn) {
            this.optionNameCn = optionNameCn;
        }

        public List<ListBean> getList() {
            if (list == null) {
                return new ArrayList<>();
            }
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        public static class ListBean implements Parcelable, Serializable {
            /**
             * optionCode : PT8
             * optionNameEn :
             * optionNameCn : 集成式儿童座椅
             */

            private String optionCode;
            private String optionNameEn;
            private String optionNameCn;

            private boolean isSelect;

            public String getOptionCode() {
                return optionCode;
            }

            public void setOptionCode(String optionCode) {
                this.optionCode = optionCode;
            }

            public String getOptionNameEn() {
                return optionNameEn;
            }

            public void setOptionNameEn(String optionNameEn) {
                this.optionNameEn = optionNameEn;
            }

            public String getOptionNameCn() {
                return optionNameCn;
            }

            public void setOptionNameCn(String optionNameCn) {
                this.optionNameCn = optionNameCn;
            }

            public boolean isSelect() {
                return isSelect;
            }

            public void setSelect(boolean select) {
                isSelect = select;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.optionCode);
                dest.writeString(this.optionNameEn);
                dest.writeString(this.optionNameCn);
                dest.writeByte(this.isSelect ? (byte) 1 : (byte) 0);
            }

            public ListBean() {
            }

            protected ListBean(Parcel in) {
                this.optionCode = in.readString();
                this.optionNameEn = in.readString();
                this.optionNameCn = in.readString();
                this.isSelect = in.readByte() != 0;
            }

            public static final Creator<ListBean> CREATOR = new Creator<ListBean>() {
                @Override
                public ListBean createFromParcel(Parcel source) {
                    return new ListBean(source);
                }

                @Override
                public ListBean[] newArray(int size) {
                    return new ListBean[size];
                }
            };

            @Override
            public String toString() {
                return "ListBean{" +
                        "optionCode='" + optionCode + '\'' +
                        ", optionNameEn='" + optionNameEn + '\'' +
                        ", optionNameCn='" + optionNameCn + '\'' +
                        ", isSelect=" + isSelect +
                        '}';
            }
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.optionCode);
            dest.writeString(this.optionNameEn);
            dest.writeString(this.optionNameCn);
            dest.writeList(this.list);
            dest.writeByte(this.isSelect ? (byte) 1 : (byte) 0);
        }

        public OptionListBean() {
        }

        protected OptionListBean(Parcel in) {
            this.optionCode = in.readString();
            this.optionNameEn = in.readString();
            this.optionNameCn = in.readString();
            this.list = new ArrayList<ListBean>();
            in.readList(this.list, ListBean.class.getClassLoader());
            this.isSelect = in.readByte() != 0;
        }

        public static final Parcelable.Creator<OptionListBean> CREATOR = new Parcelable.Creator<OptionListBean>() {
            @Override
            public OptionListBean createFromParcel(Parcel source) {
                return new OptionListBean(source);
            }

            @Override
            public OptionListBean[] newArray(int size) {
                return new OptionListBean[size];
            }
        };

        public static OptionListBean cloneWithoutList(OptionListBean optionListBean) {
            OptionListBean cloneEntity = new OptionListBean();
            cloneEntity.setSelect(optionListBean.isSelect());
            cloneEntity.setOptionCode(optionListBean.getOptionCode());
            cloneEntity.setOptionNameCn(optionListBean.getOptionNameCn());
            cloneEntity.setOptionNameEn(optionListBean.getOptionNameEn());
            cloneEntity.setList(new ArrayList<ListBean>());
            return cloneEntity;
        }

        @Override
        public String toString() {
            return "OptionListBean{" +
                    "optionCode='" + optionCode + '\'' +
                    ", optionNameEn='" + optionNameEn + '\'' +
                    ", optionNameCn='" + optionNameCn + '\'' +
                    ", list=" + list +
                    ", isSelect=" + isSelect +
                    '}';
        }
    }
}
