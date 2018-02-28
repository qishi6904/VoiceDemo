package com.example.qinshi.voicedemo;

/**
 * Created by qinshi on 2/27/2018.
 */

public class CustomDataEntity {
    private String key;
    private String name;
    private String value;
    private String valueHint;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CustomDataEntity() {

    }

    public CustomDataEntity(String name, String key, String valueHint) {
        this.key = key;
        this.name = name;
        this.valueHint = valueHint;
    }

    public CustomDataEntity(String name, String key, String value, String valueHint) {
        this.key = key;
        this.name = name;
        this.value = value;
        this.valueHint = valueHint;
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

    public String getValueHint() {
        return valueHint;
    }

    public void setValueHint(String valueHint) {
        this.valueHint = valueHint;
    }
}
