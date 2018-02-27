package com.svw.dealerapp.ui.login.model;

import com.svw.dealerapp.api.NetworkManager;
import com.svw.dealerapp.api.login.SMRefreshSubTokenApi;
import com.svw.dealerapp.entity.SMResEntity;
import com.svw.dealerapp.entity.login.SMRefreshSubTokenEntity;
import com.svw.dealerapp.mvpframe.rx.RxSchedulers;
import com.svw.dealerapp.ui.login.contract.SMRefreshSubTokenContract;

import java.util.Map;

import rx.Observable;

/**
 * Created by lijinkui on 2018/1/25.
 */

public class SMRefreshSubTokenModel implements SMRefreshSubTokenContract.Model {
    @Override
    public Observable<SMResEntity<SMRefreshSubTokenEntity>> smRefreshSubToken(Map<String, Object> options) {
        return NetworkManager.getInstance().getApiInstance(SMRefreshSubTokenApi.class, true)
                .smRefreshSubToken(options)
                .compose(RxSchedulers.<SMResEntity<SMRefreshSubTokenEntity>>io_main());
    }
}
