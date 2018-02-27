package com.svw.dealerapp.ui.resource.contract;

import android.content.Context;

import com.svw.dealerapp.entity.ResEntity;

import java.util.Map;

import rx.Observable;

/**
 * Created by qinshi on 9/25/2017.
 */

public class FailedCustomerContract {

    public interface View{
        /**
         * 激活黄成功
         */
        void activeYellowCardSuccess(int dealPosition);

        /**
         * 激活黄卡失败
         */
        void activeYellowCardFail();

        /**
         * 显示设置为重点客户成功的吐丝
         * @param isKeyuser
         */
        void showSetVipSuccessToast(String isKeyuser);

        /**
         * 显示设置为重点客户失败的吐丝
         * @param isKeyuser
         */
        void showSetVipFailedToast(String isKeyuser);

        /**
         * 设置为重点客户成功后刷新页面
         */
        void notifyListView(int dealPosition);

        /**
         * 设置Tab标签上的圆圈里的数据
         *
         * @param number
         */
        void setTabTipNumber(String number);
    }

    public interface Presenter{
        /**
         * 激活黄卡
         * @param options
         */
        void postActiveYellow(Map<String, Object> options, int dealPosition);

        /**
         * 设置为重点客户
         * @param context
         * @param oppId
         * @param isKeyuser 0：是，1：否
         * @param dealPosition
         */
        void postVipCustomer(Context context, String oppId, String isKeyuser, int dealPosition);
    }

    public interface Model{
        /**
         * 激活黄卡
         * @param options
         * @return
         */
        Observable<ResEntity<Object>> postActiveYellow(Map<String, Object> options);

        /**
         * 设置为重点客户
         * @param oppId
         * @param isKeyuser 0：是，1：否
         * @return
         */
        Observable<ResEntity<Object>> postVipCustomer(String oppId, String isKeyuser);
    }
}
