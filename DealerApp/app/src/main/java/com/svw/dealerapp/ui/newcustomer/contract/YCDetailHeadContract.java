package com.svw.dealerapp.ui.newcustomer.contract;

import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.task.BenefitEntity;
import com.svw.dealerapp.mvpframe.BasePresenter;

import java.util.Map;

import rx.Observable;

/**
 * Created by lijinkui on 2017/6/21.
 */

public interface YCDetailHeadContract {
    /**
     * 设置为重点客户的视图接口
     */
    interface View{

        void setVipStatusSuccess(String status);
        void setVipStatusFail(String message);
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

    abstract class Presenter extends BasePresenter<Model,View> {
        /**
         * 设置为重点客户
         * @param oppId
         * @param isKeyuser 0：是，1：否
         */
        public abstract void postVipCustomer(String oppId, String isKeyuser);
        /**
         * 获取权益数据
         * @param options
         */
        public abstract void getBenefitDate(Map<String, Object> options);
    }

    interface Model{
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
