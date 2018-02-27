package com.svw.dealerapp.ui.login.model;

import com.svw.dealerapp.api.NetworkManager;
import com.svw.dealerapp.api.login.SMRefreshTokenApi;
import com.svw.dealerapp.entity.SMResEntity;
import com.svw.dealerapp.entity.login.SMRefreshTokenEntity;
import com.svw.dealerapp.mvpframe.rx.RxSchedulers;
import com.svw.dealerapp.ui.login.contract.SMRefreshTokenContract;

import java.util.Map;

import rx.Observable;

/**
 * Created by lijinkui on 2018/1/25.
 */

public class SMRefreshTokenModel implements SMRefreshTokenContract.Model {
    @Override
    public Observable<SMResEntity<SMRefreshTokenEntity>> smRefreshToken(Map<String, Object> options) {
        return NetworkManager.getInstance().getApiInstance(SMRefreshTokenApi.class, true)
                .smRefreshToken(options)
                .compose(RxSchedulers.<SMResEntity<SMRefreshTokenEntity>>io_main());
    }
}
