package com.svw.dealerapp.ui.task.contract;

import android.content.Context;

import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.task.BenefitEntity;

import java.util.Map;

import rx.Observable;

/**
 * Created by qinshi on 5/19/2017.
 */

public class ECommerceContract {

    public interface View {

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
         * @param number
         */
        void setTabTipNumber(String number);

        /**
         * 获取权益数据成功
         */
        void getBenefitDataSuccess(String benefitStr);

        /**
         * 获取权益数据失败
         */
        void getBenefitDataFail();

        /**
         * 获取权益数据为空
         */
        void getBenefitDataEmpty();
    }

    public interface Presenter{
        /**
         * 设置为重点客户
         * @param context
         * @param oppId
         * @param isKeyuser 0：是，1：否
         * @param dealPosition
         */
        void postVipCustomer(Context context, String oppId, String isKeyuser, int dealPosition);

        /**
         * 获取权益数据
         * @param context
         * @param options
         */
        void getBenefitDate(Context context, Map<String, Object> options);
    }

    public interface Model{
        /**
         * 设置为重点客户
         * @param oppId
         * @param isKeyuser 0：是，1：否
         * @return
         */
        Observable<ResEntity<Object>> postVipCustomer(String oppId, String isKeyuser);

        /**
         * 获取权益数据
         * @param options
         * @return
         */
        Observable<ResEntity<BenefitEntity>> getBenefitDate(Map<String, Object> options);

    }
}
