package com.svw.dealerapp.entity.newcustomer;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lijinkui on 2017/5/15.
 */

public class CarTypesEntity implements Serializable{

    /**
     * modelId : XXXXXX
     * modelDescCn : 辉昂
     * modelDescEn : Phideon
     * colorNList : [{"colorId":"K5K5","colorNameCn":"冰川银"},{"colorId":"J8J8","colorNameCn":"天鹅绒棕"}]
     * colorWList : [{"colorId":"K5K5","colorNameCn":"冰川银"},{"colorId":"J8J8","colorNameCn":"天鹅绒棕"}]
     */

    private String modelId;
    private String modelDescCn;
    private String modelDescEn;
    private List<ColorNListBean> colorNList;
    private List<ColorWListBean> colorWList;

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getModelDescCn() {
        return modelDescCn;
    }

    public void setModelDescCn(String modelDescCn) {
        this.modelDescCn = modelDescCn;
    }

    public String getModelDescEn() {
        return modelDescEn;
    }

    public void setModelDescEn(String modelDescEn) {
        this.modelDescEn = modelDescEn;
    }

    public List<ColorNListBean> getColorNList() {
        return colorNList;
    }

    public void setColorNList(List<ColorNListBean> colorNList) {
        this.colorNList = colorNList;
    }

    public List<ColorWListBean> getColorWList() {
        return colorWList;
    }

    public void setColorWList(List<ColorWListBean> colorWList) {
        this.colorWList = colorWList;
    }

    public static class ColorNListBean implements Serializable{
        /**
         * colorId : K5K5
         * colorNameCn : 冰川银
         */

        private String colorId;
        private String colorNameCn;

        public String getColorId() {
            return colorId;
        }

        public void setColorId(String colorId) {
            this.colorId = colorId;
        }

        public String getColorNameCn() {
            return colorNameCn;
        }

        public void setColorNameCn(String colorNameCn) {
            this.colorNameCn = colorNameCn;
        }
    }

    public static class ColorWListBean implements Serializable{
        /**
         * colorId : K5K5
         * colorNameCn : 冰川银
         */

        private String colorId;
        private String colorNameCn;

        public String getColorId() {
            return colorId;
        }

        public void setColorId(String colorId) {
            this.colorId = colorId;
        }

        public String getColorNameCn() {
            return colorNameCn;
        }

        public void setColorNameCn(String colorNameCn) {
            this.colorNameCn = colorNameCn;
        }
    }
}
