package com.example.qinshi.voicedemo;

import android.content.Context;

import com.baidu.speech.EventListener;
import com.baidu.speech.EventManager;
import com.baidu.speech.EventManagerFactory;
import com.baidu.speech.asr.SpeechConstant;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by qinshi on 2/27/2018.
 */

public class BaiDuVoiceUtils {

    private static EventManager eventManager;

    /**
     * 初始化
     * @param context
     */
    public static void init(Context context){
        eventManager = EventManagerFactory.create(context, "asr");
    }

    /**
     * 注册事件监听
     * @param eventListener
     */
    public static void registerEventListener(EventListener eventListener){
        eventManager.registerListener(eventListener);
    }

    /**
     * 发送事件
     * @param eventName
     * @param inputJson
     */
    public static void sendEvent(String eventName, String inputJson){
        eventManager.send(eventName, inputJson, null, 0, 0);
    }

    /**
     * 判断是否是最终结果
     * @param param
     * @return
     */
    public static boolean isFinalResult(String param) {
        try {
            JSONObject jsonObject = new JSONObject(param);
            if(jsonObject.has("result_type")) {
                String resultType = jsonObject.getString("result_type");
                if("final_result".equals(resultType)) {
                    return true;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取最终结果
     * @param param
     * @return
     */
    public static String getFinalResult(String param) {
        try {
            JSONObject jsonObject = new JSONObject(param);
            if(jsonObject.has("best_result")) {
                String result = jsonObject.getString("best_result");
                return result;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void release(EventListener eventListener){
        if(null != eventManager) {
            eventManager.unregisterListener(eventListener);
            eventManager.send(SpeechConstant.ASR_CANCEL, "{}", null, 0, 0);
            eventManager = null;
        }
    }
}
