package com.svw.dealerapp.ui.resource.model;

import android.text.TextUtils;

import com.svw.dealerapp.api.NetworkManager;
import com.svw.dealerapp.api.RequestBaseParamsConfig;
import com.svw.dealerapp.api.order.PostCancelOrderApi;
import com.svw.dealerapp.api.resource.OrderCustomerApi;
import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.resource.OrderCustomerEntity;
import com.svw.dealerapp.mvpframe.listfragment.ListFragmentContract;
import com.svw.dealerapp.mvpframe.rx.RxSchedulers;
import com.svw.dealerapp.ui.resource.contract.OrderCustomerContract;

import java.util.Map;

import rx.Observable;

/**
 * Created by qinshi on 7/28/2017.
 */

public class OrderCustomerModel implements ListFragmentContract.Model<ResEntity<OrderCustomerEntity>>, OrderCustomerContract.Model {

    @Override
    public Observable<ResEntity<Object>> postCancelOrder(Map<String, Object> options) {
        Map<String, Object> params = RequestBaseParamsConfig.addRequestBaseParams2(options);
        return NetworkManager.getInstance().getApiInstance(PostCancelOrderApi.class, true)
                .postCancelOrder(params)
                .compose(RxSchedulers.<ResEntity<Object>>io_main());
    }

    @Override
    public Observable<ResEntity<OrderCustomerEntity>> getDataFromServer(String pageIndex, String pageSize, String orderType, String filter, String... moreParams) {
        Map<String, String> params = RequestBaseParamsConfig.getRequestBaseParams();
        params.put("pageIndex", pageIndex);
        params.put("pageSize", pageSize);
        if (!TextUtils.isEmpty(filter)) {
            params.put("filter", filter);
        }
        return NetworkManager.getInstance().getApiInstance(OrderCustomerApi.class, true)
                .getOrderCustomerList(params)
                .compose(RxSchedulers.<ResEntity<OrderCustomerEntity>>io_main());
    }

}
