package com.example.qinshi.voicedemo;

import java.util.List;

/**
 * Created by qinshi on 2/28/2018.
 */

public class ReturnEntity {
    private String retCode;
    private String retMessage;
    private List<ResultEntity> retData;

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public String getRetMessage() {
        return retMessage;
    }

    public void setRetMessage(String retMessage) {
        this.retMessage = retMessage;
    }

    public List<ResultEntity> getRetData() {
        return retData;
    }

    public void setRetData(List<ResultEntity> retData) {
        this.retData = retData;
    }
}
