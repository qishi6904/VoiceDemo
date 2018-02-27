package com.svw.dealerapp.ui.bindpushdevice;

import com.svw.dealerapp.api.NetworkManager;
import com.svw.dealerapp.api.RequestBaseParamsConfig;
import com.svw.dealerapp.api.mine.AliPushRegisterDeviceApi;
import com.svw.dealerapp.api.mine.AliPushUnregisterDeviceApi;
import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.mvpframe.rx.RxSchedulers;

import java.util.Map;

import rx.Observable;

/**
 * Created by lijinkui on 2017/7/7.
 */

public class BindPushDeviceModel implements BindPushDeviceContract.Model {

    @Override
    public Observable<ResEntity<Object>> aliPushRegisterDevice(Map<String, Object> options) {
        Map<String, Object> params = RequestBaseParamsConfig.addRequestBaseParams2(options);
        return NetworkManager.getInstance().getApiInstance(AliPushRegisterDeviceApi.class, true)
                .aliPushRegisterDevice(params)
                .compose(RxSchedulers.<ResEntity<Object>>io_main());
    }

    @Override
    public Observable<ResEntity<Object>> aliPushUnregisterDevice(Map<String, Object> options) {
        Map<String, Object> params = RequestBaseParamsConfig.addRequestBaseParams2(options);
        return NetworkManager.getInstance().getApiInstance(AliPushUnregisterDeviceApi.class, true)
                .aliPushUnregisterDevice(params)
                .compose(RxSchedulers.<ResEntity<Object>>io_main());
    }

}
