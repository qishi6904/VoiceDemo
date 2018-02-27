package com.example.qinshi.voicedemo;

/**
 * Created by qinshi on 2/27/2018.
 */

public class CustomDataEntity {
    private String Key;
    private String Value;
    private String valueHint;

    public CustomDataEntity() {

    }

    public CustomDataEntity(String key, String valueHint) {
        Key = key;
        this.valueHint = valueHint;
    }

    public CustomDataEntity(String key, String value, String valueHint) {
        Key = key;
        Value = value;
        this.valueHint = valueHint;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }

    public String getValueHint() {
        return valueHint;
    }

    public void setValueHint(String valueHint) {
        this.valueHint = valueHint;
    }
}
