package com.svw.dealerapp.util;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

/**
 * Created by qinshi on 5/24/2017.
 */

public class GsonUtils {

    /**
     * 对象转成Json字符串
     * @param object
     * @return
     */
    public static String changeEntityToJsonStr(Object object){
        return new Gson().toJson(object);
    }

    public static boolean isBadJson(String json) {
        return !isGoodJson(json);
    }

    public static boolean isGoodJson(String json) {
        if (TextUtils.isEmpty(json)) {
            return false;
        }
        try {
            new JsonParser().parse(json);
            return true;
        } catch (JsonParseException e) {
            return false;
        }
    }
}
