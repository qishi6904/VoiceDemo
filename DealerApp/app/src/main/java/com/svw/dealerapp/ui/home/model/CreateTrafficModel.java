package com.svw.dealerapp.ui.home.model;

import com.svw.dealerapp.api.NetworkManager;
import com.svw.dealerapp.api.RequestBaseParamsConfig;
import com.svw.dealerapp.api.home.ActivityListApi;
import com.svw.dealerapp.api.home.CheckRepeatApi;
import com.svw.dealerapp.api.home.CreateTrafficApi;
import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.home.ActivityEntity;
import com.svw.dealerapp.entity.home.CheckRepeatEntity;
import com.svw.dealerapp.mvpframe.rx.RxSchedulers;
import com.svw.dealerapp.ui.home.contract.CreateTrafficContract;

import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * Created by lijinkui on 2017/6/5.
 */

public class CreateTrafficModel implements CreateTrafficContract.Model{
    @Override
    public Observable<ResEntity<Object>> createTraffic(Map<String, Object> options) {
        options = RequestBaseParamsConfig.addRequestBaseParams2(options);
        return NetworkManager.getInstance().getApiInstance(CreateTrafficApi.class, true)
                .createTraffic(options)
                .compose(RxSchedulers.<ResEntity<Object>>io_main());
    }

    @Override
    public Observable<ResEntity<ActivityEntity>> getActivityList(Map<String, Object> options) {
        options = RequestBaseParamsConfig.addRequestBaseParams2(options);
        return NetworkManager.getInstance().getApiInstance(ActivityListApi.class, true)
                .getActivityList(options)
                .compose(RxSchedulers.<ResEntity<ActivityEntity>>io_main());
    }

    @Override
    public Observable<ResEntity<List<CheckRepeatEntity>>> checkRepeat(Map<String, Object> options) {
        return NetworkManager.getInstance().getApiInstance(CheckRepeatApi.class, true)
                .checkRepeat(options)
                .compose(RxSchedulers.<ResEntity<List<CheckRepeatEntity>>>io_main());
    }
}
