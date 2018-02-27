package com.svw.dealerapp.ui.order.model;

import com.svw.dealerapp.api.NetworkManager;
import com.svw.dealerapp.api.order.CancelOrderFollowupApi;
import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.order.CancelOrderReqEntity;
import com.svw.dealerapp.mvpframe.rx.RxSchedulers;
import com.svw.dealerapp.ui.order.contract.CancelOrderFollowupContract;

import rx.Observable;

/**
 * Created by lijinkui on 2017/9/28.
 */

public class CancelOrderFollowupModel implements CancelOrderFollowupContract.Model {

    @Override
    public Observable<ResEntity<Object>> cancelOrderFollowup(CancelOrderReqEntity entity) {
        return NetworkManager.getInstance().getApiInstance(CancelOrderFollowupApi.class, true)
                .cancelOrderFollowup(entity)
                .compose(RxSchedulers.<ResEntity<Object>>io_main());
    }
}
