package com.svw.dealerapp.ui.entity;

/**
 * Created by qinshi on 6/2/2017.
 */

public class KeyValueListItemEntity {
    private String key;
    private String value;

    public KeyValueListItemEntity(){

    }

    public KeyValueListItemEntity(String title, String value) {
        this.key = title;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
