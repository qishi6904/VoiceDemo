package com.svw.dealerapp.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.sdk.android.push.CloudPushService;
import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.svw.dealerapp.DealerApp;

/**
 * Created by Gavin on 02/05/2017.
 */

public class AliCloudPushUtils {

    private static final String TAG = "AliCloudPushUtils Init";
    private static CloudPushService pushService;

    /**
     * 初始化云推送通道
     * @param applicationContext
     */
    public static void initCloudChannel(Context applicationContext) {
        PushServiceFactory.init(applicationContext);
        pushService = PushServiceFactory.getCloudPushService();
        pushService.register(applicationContext, new CommonCallback() {
            @Override
            public void onSuccess(String response) {
                Log.d(TAG, "init cloudchannel success");
                if(!TextUtils.isEmpty(pushService.getDeviceId())){
                    SharedPreferences aliPushDeviceIdSP = DealerApp.getContext().getSharedPreferences("appSettingSP", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = aliPushDeviceIdSP.edit();
                    editor.putString("aliPushDeviceId", pushService.getDeviceId());
                    editor.commit();
                }
            }
            @Override
            public void onFailed(String errorCode, String errorMessage) {
                Log.d(TAG, "init cloudchannel failed -- errorcode:" + errorCode + " -- errorMessage:" + errorMessage);
            }
        });
    }
}
