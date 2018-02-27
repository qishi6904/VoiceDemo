package com.svw.dealerapp.common.drawer;

/**
 * Created by qinshi on 1/11/2018.
 */

public class RdLeftDrawerItemEntity {

    private int iconId;
    private String tagText;
    private boolean isShowDividerSpace;

    public RdLeftDrawerItemEntity(){

    }

    public RdLeftDrawerItemEntity(int iconId, String tagText, boolean isShowDividerSpace) {
        this.iconId = iconId;
        this.tagText = tagText;
        this.isShowDividerSpace = isShowDividerSpace;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public String getTagText() {
        return tagText;
    }

    public void setTagText(String tagText) {
        this.tagText = tagText;
    }

    public boolean isShowDividerSpace() {
        return isShowDividerSpace;
    }

    public void setShowDividerSpace(boolean showDividerSpace) {
        isShowDividerSpace = showDividerSpace;
    }
}
