package com.svw.dealerapp.ui.home.entity;

import java.util.List;

/**
 * Created by qinshi on 8/25/2017.
 */

public class AutoPlayEntity {
    private List<AutoPlayItemEntity> tabList;
    private List<AutoPlayItemEntity> itemList;

    public List<AutoPlayItemEntity> getTabList() {
        return tabList;
    }

    public void setTabList(List<AutoPlayItemEntity> tabList) {
        this.tabList = tabList;
    }

    public List<AutoPlayItemEntity> getItemList() {
        return itemList;
    }

    public void setItemList(List<AutoPlayItemEntity> itemList) {
        this.itemList = itemList;
    }

    public static class AutoPlayItemEntity {
        private String tabId;
        private String name;
        private String value;
        private String iconUrl;
        private int iconId;
        private String url;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getIconUrl() {
            return iconUrl;
        }

        public void setIconUrl(String iconUrl) {
            this.iconUrl = iconUrl;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getIconId() {
            return iconId;
        }

        public void setIconId(int iconId) {
            this.iconId = iconId;
        }

        public String getTabId() {
            return tabId;
        }

        public void setTabId(String tabId) {
            this.tabId = tabId;
        }
    }
}
