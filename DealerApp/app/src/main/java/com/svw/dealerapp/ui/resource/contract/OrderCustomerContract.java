package com.svw.dealerapp.ui.resource.contract;

import android.content.Context;

import com.svw.dealerapp.entity.ResEntity;

import java.util.Map;

import rx.Observable;

/**
 * Created by qinshi on 7/28/2017.
 */

public class OrderCustomerContract {

    public interface View {
        void cancelSuccess(int dealPosition);

        void cancelFail();

        /**
         * 设置Tab标签上的圆圈里的数据
         *
         * @param number
         */
        void setTabTipNumber(String number);
    }

    public interface Presenter {
        void postCancelOrder(Context context, Map<String, Object> options, int dealPosition);
    }

    public interface Model {
        Observable<ResEntity<Object>> postCancelOrder(Map<String, Object> options);

    }
}
