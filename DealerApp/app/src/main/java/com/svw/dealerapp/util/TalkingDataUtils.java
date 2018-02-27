package com.svw.dealerapp.util;

import android.content.Context;

import com.tendcloud.tenddata.TCAgent;

import java.util.Map;

/**
 * Created by qinshi on 4/26/2017.
 */

public class TalkingDataUtils {

    /**
     * 初始化TalkingData
     * @param context
     */
    public static void initTalkingData(Context context){
        TCAgent.LOG_ON=true;
        // App ID: 在TalkingData创建应用后，进入数据报表页中，在“系统设置”-“编辑应用”页面里查看App ID。
        // 渠道 ID: 是渠道标识符，可通过不同渠道单独追踪数据。
//        TCAgent.init(context, "DCF4D3EC0E9740958831F54042D4C4D7", "Channel_Test");
        TCAgent.init(context);
        // 如果已经在AndroidManifest.xml配置了App ID和渠道ID，调用TCAgent.init(this)即可；或与AndroidManifest.xml中的对应参数保持一致。
        TCAgent.setReportUncaughtExceptions(true);
        TCAgent.setAntiCheatingEnabled(context, true);
    }

    /**
     * 进入页面时调用，必须和 onPageEnd 成对调用
     * @param context
     * @param pageName
     */
    public static void onPageStart(Context context, String pageName){
        TCAgent.onPageStart(context, pageName);
    }

    /**
     * 离开页面时调用，必须和 onPageStart 成对调用
     * @param context
     * @param pageName
     */
    public static void onPageEnd(Context context, String pageName){
        TCAgent.onPageEnd(context, pageName);
    }

    /**
     * 添加跟踪事件
     * @param context
     * @param eventId
     */
    public static void onEvent(Context context, String eventId){
        TCAgent.onEvent(context, eventId);
    }

    /**
     * 跟踪多个同类型事件，无需定义多个Event ID，
     * 可以使用Event ID做为目录名，而使用Label标签来区分这些事件
     * @param context
     * @param eventId
     * @param eventLabel
     */
    public static void onEvent(Context context, String eventId, String eventLabel){
        TCAgent.onEvent(context, eventId, eventLabel);
    }

    /**
     * 添加跟踪事件，可以给事件添加详尽的描述信息
     * 描述信息为k-v形式
     * @param context
     * @param eventId
     * @param eventLabel
     * @param map
     */
    public static void onEvent(Context context, String eventId, String eventLabel, Map map){
        TCAgent.onEvent(context, eventId, eventLabel, map);
    }
}
