package com.svw.dealerapp.ui.order.contract;

import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.order.CreateOrderEntity;
import com.svw.dealerapp.entity.order.QueryOrderDetailResponseEntity;
import com.svw.dealerapp.mvpframe.BasePresenter;
import com.svw.dealerapp.mvpframe.BaseView;

import java.util.Map;

import rx.Observable;

/**
 * Created by xupan on 28/08/2017.
 */

public interface OrderContract {

    interface Model {
        Observable<ResEntity<Object>> createOrder(CreateOrderEntity entity);

        Observable<ResEntity<Object>> editOrder(CreateOrderEntity entity);

        Observable<ResEntity<QueryOrderDetailResponseEntity>> queryOrderDetail(Map<String, Object> options);

        Observable<ResEntity<Object>> cancelOrder(String orderId);

        Observable<ResEntity<Object>> reportOtdOrder(String orderId);
    }

    interface View extends BaseView {
        void onCreateOrderSuccess();

        void onCreateOrderFailure(String msg);

        void onEditOrderSuccess();

        void onEditOrderFailure(String msg);

        void onQueryOrderDetailSuccess(QueryOrderDetailResponseEntity entity);

        void onQueryOrderDetailFailure(String msg);

        void onCancelOrderSuccess();

        void onCancelOrderFailure(String msg);

        void onReportOtdOrderSuccess();

        void onReportOtdOrderFailure(String msg);
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        public abstract void createOrder(CreateOrderEntity entity);

        public abstract void editOrder(CreateOrderEntity entity);

        public abstract void queryOrderDetail(Map<String, Object> options);

        public abstract void cancelOrder(String orderId);

        public abstract void reportOtdOrder(String orderId);
    }
}
