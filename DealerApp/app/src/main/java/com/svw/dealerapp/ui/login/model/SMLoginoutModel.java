package com.svw.dealerapp.ui.login.model;

import com.svw.dealerapp.api.NetworkManager;
import com.svw.dealerapp.api.login.SMLoginoutApi;
import com.svw.dealerapp.entity.SMResEntity;
import com.svw.dealerapp.entity.login.SMLoginoutEntity;
import com.svw.dealerapp.mvpframe.rx.RxSchedulers;
import com.svw.dealerapp.ui.login.contract.SMLoginoutContract;

import java.util.Map;

import rx.Observable;

/**
 * Created by lijinkui on 2018/1/25.
 */

public class SMLoginoutModel implements SMLoginoutContract.Model {
    @Override
    public Observable<SMResEntity<Object>> smLoginout(Map<String, Object> options) {
        return NetworkManager.getInstance().getApiInstance(SMLoginoutApi.class, true)
                .smLoginout(options)
                .compose(RxSchedulers.<SMResEntity<Object>>io_main());
    }
}
