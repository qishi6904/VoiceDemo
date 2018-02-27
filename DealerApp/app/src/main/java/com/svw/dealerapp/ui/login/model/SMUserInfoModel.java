package com.svw.dealerapp.ui.login.model;

import com.svw.dealerapp.api.NetworkManager;
import com.svw.dealerapp.api.login.SMUserInfoApi;
import com.svw.dealerapp.entity.SMResEntity;
import com.svw.dealerapp.entity.login.SMUserInfoEntity;
import com.svw.dealerapp.mvpframe.rx.RxSchedulers;
import com.svw.dealerapp.ui.login.contract.SMUserInfoContract;

import java.util.Map;

import rx.Observable;

/**
 * Created by lijinkui on 2018/1/25.
 */

public class SMUserInfoModel implements SMUserInfoContract.Model {
    @Override
    public Observable<SMResEntity<SMUserInfoEntity>> smUserInfo(Map<String, Object> options) {
        return NetworkManager.getInstance().getApiInstance(SMUserInfoApi.class, true)
                .smUserInfo(options)
                .compose(RxSchedulers.<SMResEntity<SMUserInfoEntity>>io_main());
    }
}
