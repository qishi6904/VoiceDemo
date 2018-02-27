package com.svw.dealerapp.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.sdk.android.push.MessageReceiver;
import com.alibaba.sdk.android.push.notification.CPushMessage;
import com.google.gson.Gson;
import com.svw.dealerapp.global.NewCustomerConstants;
import com.svw.dealerapp.ui.mine.activity.ApproveActivity;
import com.svw.dealerapp.ui.mine.activity.NotificationActivity;
import com.svw.dealerapp.ui.newcustomer.activity.CustomerDetailActivity;
import com.svw.dealerapp.ui.newcustomer.activity.CustomerDetailItemActivity;
import com.svw.dealerapp.ui.task.activity.TaskActivity;


import java.util.Map;

/**
 * Created by Gavin on 02/05/2017.
 */

public class SXMessageReceiver extends MessageReceiver {

    // 消息接收部分的LOG_TAG
    public static final String REC_TAG = "receiver";

    @Override
    public void onNotification(Context context, String title, String summary, Map<String, String> extraMap) {
        // TODO 处理推送通知
        Log.e("MyMessageReceiver", "Receive notification, title: " + title + ", summary: " + summary + ", extraMap: " + extraMap);
    }

    @Override
    public void onMessage(Context context, CPushMessage cPushMessage) {
        Log.e("MyMessageReceiver", "onMessage, messageId: " + cPushMessage.getMessageId() + ", title: " + cPushMessage.getTitle() + ", content:" + cPushMessage.getContent());
        Intent intent = new Intent("com.svw.dealerapp.newMassage");
        context.sendBroadcast(intent);
    }

    @Override
    public void onNotificationOpened(Context context, String title, String summary, String extraMap) {
        Log.e("MyMessageReceiver", "onNotificationOpened, title: " + title + ", summary: " + summary + ", extraMap:" + extraMap);
        goToActivityByMap(context, extraMap);
    }

    @Override
    protected void onNotificationClickedWithNoAction(Context context, String title, String summary, String extraMap) {
        Log.e("MyMessageReceiver", "onNotificationClickedWithNoAction, title: " + title + ", summary: " + summary + ", extraMap:" + extraMap);
        goToActivityByMap(context, extraMap);
    }

    @Override
    protected void onNotificationReceivedInApp(Context context, String title, String summary, Map<String, String> extraMap, int openType, String openActivity, String openUrl) {
        Log.e("MyMessageReceiver", "onNotificationReceivedInApp, title: " + title + ", summary: " + summary + ", extraMap:" + extraMap + ", openType:" + openType + ", openActivity:" + openActivity + ", openUrl:" + openUrl);
    }

    @Override
    protected void onNotificationRemoved(Context context, String messageId) {
        Log.e("MyMessageReceiver", "onNotificationRemoved");
    }

    private void goToActivityByMap(Context context, String extraMap) {
        Gson gson = new Gson();
        Map<String, Object> option = gson.fromJson(extraMap, Map.class);
        String activity = option.get("activity").toString();
        Object oppIdObject = option.get("oppId");
        String oppId = "";
        if(null != oppIdObject){
            oppId = oppIdObject.toString();
        }

        Intent intent = null;
        if ("1001".equals(activity)) {    // 接收线索/客流－线索跟进任务页面
//            intent = new Intent(context.getApplicationContext(), MainActivity.class);
//            intent.putExtra("firstNavPosition", 1); //要跳到的一级导航的位置
//            intent.putExtra("secondNavPosition", 1); //要跳到的二级导航的位置
            intent = new Intent(context.getApplicationContext(), TaskActivity.class);
            intent.putExtra("position", 0); //要跳到的一级导航的位置
        } else if ("1002".equals(activity)) {  // 接收客户－该客户的黄卡详情页
//            intent = new Intent(context.getApplicationContext(), MainActivity.class);
//            intent.putExtra("firstNavPosition", 1); //要跳到的一级导航的位置
//            intent.putExtra("secondNavPosition", 0); //要跳到的二级导航的位置
            if (TextUtils.isEmpty(oppId)) {
                return;
            }
            intent = new Intent(context.getApplicationContext(), CustomerDetailActivity.class);
            intent.putExtra("oppId", oppId);
        } else if ("1003".equals(activity)) {  // 客户信息更新－黄卡详情页_备注页面
            if (TextUtils.isEmpty(oppId)) {
                return;
            }
            intent = new Intent(context.getApplicationContext(), CustomerDetailItemActivity.class);
            intent.putExtra("fragment", NewCustomerConstants.FRAGMENT_TAG_REMARK);
            intent.putExtra("oppId", oppId);
        } else if ("1004".equals(activity)) {  // 预约提醒－黄卡详情页
            if (TextUtils.isEmpty(oppId)) {
                return;
            }
            intent = new Intent(context.getApplicationContext(), CustomerDetailActivity.class);
            intent.putExtra("oppId", oppId);
        } else if ("1005".equals(activity)) {  // 审批请求－审批列表_待审批
            intent = new Intent(context.getApplicationContext(), ApproveActivity.class);
            intent.putExtra("firstNavPosition", 0); //要跳到的一级导航的位置
        } else if ("1006".equals(activity)) {  // 审批通过－审批列表_已审批
            intent = new Intent(context.getApplicationContext(), ApproveActivity.class);
            intent.putExtra("firstNavPosition", 1); //要跳到的一级导航的位置
        } else if ("1007".equals(activity)) {  // 审批驳回－审批列表_已审批
            intent = new Intent(context.getApplicationContext(), ApproveActivity.class);
            intent.putExtra("firstNavPosition", 1); //要跳到的一级导航的位置
        } else if ("1008".equals(activity)){    //未处理信息－通知列表页
            intent = new Intent(context.getApplicationContext(), NotificationActivity.class);
        } else if ("1009".equals(activity)){    //查重合并－黄页详情页
            if (TextUtils.isEmpty(oppId)) {
                return;
            }
            intent = new Intent(context.getApplicationContext(), CustomerDetailActivity.class);
            intent.putExtra("oppId", oppId);
        } else if ("1010".equals(activity)){    //重复黄卡－黄页详情页
            if (TextUtils.isEmpty(oppId)) {
                return;
            }
            intent = new Intent(context.getApplicationContext(), CustomerDetailActivity.class);
            intent.putExtra("oppId", oppId);
        } else if ("1011".equals(activity)){    //订单状态变更为开票中－黄页详情页
            if (TextUtils.isEmpty(oppId)) {
                return;
            }
            intent = new Intent(context.getApplicationContext(), CustomerDetailActivity.class);
            intent.putExtra("oppId", oppId);
        }else if ("1012".equals(activity)){     //订单状态变更为已开票－黄页详情页
            if (TextUtils.isEmpty(oppId)) {
                return;
            }
            intent = new Intent(context.getApplicationContext(), CustomerDetailActivity.class);
            intent.putExtra("oppId", oppId);
        }


        if (null != intent) {
            intent.putExtra("isFromNotice", true);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.getApplicationContext().startActivity(intent);
        }

    }

}
