package com.svw.dealerapp.ui.resource.model;

import com.svw.dealerapp.api.NetworkManager;
import com.svw.dealerapp.api.resource.LeadRelateValidApi;
import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.mvpframe.rx.RxSchedulers;
import com.svw.dealerapp.ui.resource.contract.LeadRelateValidContract;

import java.util.Map;

import rx.Observable;

/**
 * Created by lijinkui on 2017/11/22.
 */

public class LeadRelateValidModel implements LeadRelateValidContract.Model{

    @Override
    public Observable<ResEntity<Object>> leadRelateValid(Map<String, Object> options) {
        return NetworkManager.getInstance().getApiInstance(LeadRelateValidApi.class, true)
                .leadRelateValid(options)
                .compose(RxSchedulers.<ResEntity<Object>>io_main());
    }
}
