package com.svw.dealerapp.api;

import com.svw.dealerapp.DealerApp;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by qinshi on 5/17/2017.
 * 请求的通用参数配置
 */

public class RequestBaseParamsConfig {

    public static Map<String, String> getRequestBaseParams() {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        String currentDate = simpleDateFormat.format(new Date());
//        String token = DealerApp.ACCESS_TOKEN;

        Map<String, String> baseParams = new HashMap<String, String>();
        baseParams.put("appId", "com.svw.dealerapp");
        baseParams.put("appType", "101");
        baseParams.put("seqNo", "ea185595-4b35-4bf1-b8da-2f6036b12667");
        baseParams.put("reqTime", currentDate);
//        baseParams.put("token", token);

        return baseParams;
    }

    public static Map<String, String> addRequestBaseParams(Map<String, String> param) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        String currentDate = simpleDateFormat.format(new Date());
//        String token = DealerApp.ACCESS_TOKEN;

        param.put("appId", "com.svw.dealerapp");
        param.put("appType", "101");
        param.put("seqNo", "ea185595-4b35-4bf1-b8da-2f6036b12667");
        param.put("reqTime", currentDate);
//        param.put("token", token);

        return param;
    }

    public static Map<String, Object> getRequestBaseParams2() {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        String currentDate = simpleDateFormat.format(new Date());
//        String token = DealerApp.ACCESS_TOKEN;

        Map<String, Object> baseParams = new HashMap<String, Object>();
        baseParams.put("appId", "com.svw.dealerapp");
        baseParams.put("appType", "101");
        baseParams.put("seqNo", "ea185595-4b35-4bf1-b8da-2f6036b12667");
        baseParams.put("reqTime", currentDate);
//        baseParams.put("token", token);

        return baseParams;
    }

    public static Map<String, Object> addRequestBaseParams2(Map<String, Object> param) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        String currentDate = simpleDateFormat.format(new Date());
//        String token = DealerApp.ACCESS_TOKEN;

        param.put("appId", "com.svw.dealerapp");
        param.put("appType", "101");
        param.put("seqNo", "ea185595-4b35-4bf1-b8da-2f6036b12667");
        param.put("reqTime", currentDate);
//        param.put("token", token);

        return param;
    }
}
