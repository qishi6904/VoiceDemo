package com.svw.dealerapp.ui.task.contract;

import android.content.Context;

import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.resource.YellowCardEntity;

import rx.Observable;

/**
 * Created by qinshi on 5/25/2017.
 */

public class TaskLeadsContract {

    public interface View{
        /**
         * 设置为无效流量后刷新页面
         * @param position
         */
        void changeToInvalidStatus(int position);

        /**
         * 显示设置为无效流量成功的吐丝
         */
        void showChangeToInvalidSuccessToast();

        /**
         * 显示设置为无效流量失败的吐丝
         */
        void showChangeToInvalidFailToast();

        /**
         * 设置Tab标签上的圆圈里的数据
         * @param number
         */
        void setTabTipNumber(String number);

    }


    public interface Model{
        /**
         * 提效无法建卡请求
         * @param trafficId
         * @param status
         * @param reason
         */
        Observable<ResEntity<Object>> postInvalidTrafficStatus(String trafficId, String status, String reason);

    }

    public interface Presenter{
        /**
         * 提效无法建卡请求
         * @param context
         * @param trafficId
         * @param status
         * @param reason
         * @return
         */
        void postInvalidTrafficStatus(Context context, String trafficId, String status, String reason, int dealInvalidPosition);

        /**
         * 检查流量是否已创建黄卡
         * @param context
         * @param leadsId
         */
    }
}
