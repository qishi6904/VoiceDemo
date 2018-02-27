package com.svw.dealerapp.ui.newcustomer.model;

import com.svw.dealerapp.api.NetworkManager;
import com.svw.dealerapp.api.newcustomer.CheckBeforeActivateYCApi;
import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.mvpframe.rx.RxSchedulers;
import com.svw.dealerapp.ui.newcustomer.contract.CheckBeforeActivateYCContract;

import java.util.Map;

import rx.Observable;

/**
 * Created by lijinkui on 2017/12/22.
 */

public class CheckBeforeActivateYCModel implements CheckBeforeActivateYCContract.Model{
    @Override
    public Observable<ResEntity<Object>> checkBeforeActivateYC(Map<String, Object> options) {
        return NetworkManager.getInstance().getApiInstance(CheckBeforeActivateYCApi.class, true)
                .checkBeforeActivateYC(options)
                .compose(RxSchedulers.<ResEntity<Object>>io_main());
    }
}
