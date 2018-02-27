package com.svw.dealerapp.ui.resource.entity;

/**
 * Created by qinshi on 12/6/2017.
 */

public class SeriesFilterEntity {

    String seriesName;
    String seriesCode;

    boolean isSelect;
    boolean isAll;

    public SeriesFilterEntity(){

    }

    public SeriesFilterEntity(String seriesCode, String seriesName) {
        this.seriesCode = seriesCode;
        this.seriesName = seriesName;
    }

    public SeriesFilterEntity(String seriesCode, String seriesName, boolean isAll) {
        this.seriesCode = seriesCode;
        this.seriesName = seriesName;
        this.isAll = isAll;
    }

    public String getSeriesName() {
        return seriesName;
    }

    public void setSeriesName(String seriesName) {
        this.seriesName = seriesName;
    }

    public String getSeriesCode() {
        return seriesCode;
    }

    public void setSeriesCode(String seriesCode) {
        this.seriesCode = seriesCode;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public boolean isAll() {
        return isAll;
    }

    public void setAll(boolean all) {
        isAll = all;
    }
}
