package com.svw.dealerapp.ui.order.model;

import com.svw.dealerapp.api.NetworkManager;
import com.svw.dealerapp.api.RequestBaseParamsConfig;
import com.svw.dealerapp.api.order.CreateOrderApi;
import com.svw.dealerapp.api.order.EditOrderApi;
import com.svw.dealerapp.api.order.PostCancelOrderApi;
import com.svw.dealerapp.api.order.QueryOrderDetailApi;
import com.svw.dealerapp.api.order.ReportOtdOrderApi;
import com.svw.dealerapp.entity.ReqEntity;
import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.order.CreateOrderEntity;
import com.svw.dealerapp.entity.order.QueryOrderDetailResponseEntity;
import com.svw.dealerapp.mvpframe.rx.RxSchedulers;
import com.svw.dealerapp.ui.order.contract.OrderContract;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;

/**
 * Created by xupan on 28/08/2017.
 */

public class OrderModel implements OrderContract.Model {

    @Override
    public Observable<ResEntity<Object>> createOrder(CreateOrderEntity entity) {
        return NetworkManager.getInstance().getApiInstance(CreateOrderApi.class, true).createOrder(entity)
                .compose(RxSchedulers.<ResEntity<Object>>io_main());
    }

    @Override
    public Observable<ResEntity<Object>> editOrder(CreateOrderEntity entity) {
        return NetworkManager.getInstance().getApiInstance(EditOrderApi.class, true).editOrder(entity)
                .compose(RxSchedulers.<ResEntity<Object>>io_main());
    }

//    private Map<String, String> optionsForQueryOrderDetail(String orderId) {
//        Map<String, String> options = new HashMap<>();
//        options.put("orderId", orderId);
//        options = RequestBaseParamsConfig.addRequestBaseParams(options);
//        return options;
//    }

    private Map<String, Object> optionsForCancelOrder(String orderId) {
        Map<String, Object> options = new HashMap<>();
        options.put("orderId", orderId);
        options = RequestBaseParamsConfig.addRequestBaseParams2(options);
        return options;
    }

    @Override
    public Observable<ResEntity<QueryOrderDetailResponseEntity>> queryOrderDetail(Map<String, Object> options) {
        options = RequestBaseParamsConfig.addRequestBaseParams2(options);
        return NetworkManager.getInstance().getApiInstance(QueryOrderDetailApi.class, true).queryOrderDetail(options)
                .compose(RxSchedulers.<ResEntity<QueryOrderDetailResponseEntity>>io_main());
    }

    @Override
    public Observable<ResEntity<Object>> cancelOrder(String orderId) {
        return NetworkManager.getInstance().getApiInstance(PostCancelOrderApi.class, true).postCancelOrder(optionsForCancelOrder(orderId))
                .compose(RxSchedulers.<ResEntity<Object>>io_main());
    }

    @Override
    public Observable<ResEntity<Object>> reportOtdOrder(String orderId) {
        return NetworkManager.getInstance().getApiInstance(ReportOtdOrderApi.class, true).reportOtdOrder(orderId, new ReqEntity())
                .compose(RxSchedulers.<ResEntity<Object>>io_main());
    }
}
