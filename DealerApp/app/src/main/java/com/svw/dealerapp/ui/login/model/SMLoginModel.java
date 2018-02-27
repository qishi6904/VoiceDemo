package com.svw.dealerapp.ui.login.model;

import com.svw.dealerapp.api.NetworkManager;
import com.svw.dealerapp.api.login.SMLoginApi;
import com.svw.dealerapp.entity.SMResEntity;
import com.svw.dealerapp.entity.login.SMLoginEntity;
import com.svw.dealerapp.mvpframe.rx.RxSchedulers;
import com.svw.dealerapp.ui.login.contract.SMLoginContract;

import java.util.Map;

import rx.Observable;

/**
 * Created by lijinkui on 2018/1/25.
 */

public class SMLoginModel implements SMLoginContract.Model {

    @Override
    public Observable<SMResEntity<SMLoginEntity>> smLogin(Map<String, Object> options) {
        return NetworkManager.getInstance().getApiInstance(SMLoginApi.class, false)
                .smLogin(options)
                .compose(RxSchedulers.<SMResEntity<SMLoginEntity>>io_main());
    }
}
