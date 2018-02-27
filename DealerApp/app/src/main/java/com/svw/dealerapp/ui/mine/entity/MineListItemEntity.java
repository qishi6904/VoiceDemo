package com.svw.dealerapp.ui.mine.entity;

/**
 * Created by qinshi on 5/31/2017.
 */

public class MineListItemEntity {
    private int iconId;
    private String title;
    private String number;
    private boolean hasNew;

    public MineListItemEntity(){

    }

    public MineListItemEntity(int iconId, String title, String totalNumber, boolean hasNew) {
        this.iconId = iconId;
        this.title = title;
        this.number = totalNumber;
        this.hasNew = hasNew;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public boolean isHasNew() {
        return hasNew;
    }

    public void setHasNew(boolean hasNew) {
        this.hasNew = hasNew;
    }
}
