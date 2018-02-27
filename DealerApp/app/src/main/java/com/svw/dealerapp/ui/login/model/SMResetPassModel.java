package com.svw.dealerapp.ui.login.model;

import com.svw.dealerapp.api.NetworkManager;
import com.svw.dealerapp.api.login.SMResetPassApi;
import com.svw.dealerapp.entity.SMResEntity;
import com.svw.dealerapp.mvpframe.rx.RxSchedulers;
import com.svw.dealerapp.ui.login.contract.SMResetPassContract;

import java.util.Map;

import rx.Observable;

/**
 * Created by lijinkui on 2018/1/25.
 */

public class SMResetPassModel implements SMResetPassContract.Model {
    @Override
    public Observable<SMResEntity<Object>> smResetPass(Map<String, Object> options) {
        return NetworkManager.getInstance().getApiInstance(SMResetPassApi.class, true)
                .smResetPass(options)
                .compose(RxSchedulers.<SMResEntity<Object>>io_main());
    }
}
