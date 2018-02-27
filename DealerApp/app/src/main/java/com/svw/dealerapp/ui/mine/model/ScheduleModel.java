package com.svw.dealerapp.ui.mine.model;

import com.svw.dealerapp.api.NetworkManager;
import com.svw.dealerapp.api.RequestBaseParamsConfig;
import com.svw.dealerapp.api.mine.ScheduleCreateApi;
import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.mine.ScheduleCreateResEntity;
import com.svw.dealerapp.mvpframe.rx.RxSchedulers;
import com.svw.dealerapp.ui.mine.contract.ScheduleContract;

import java.util.Map;

import rx.Observable;

/**
 * Created by lijinkui on 2017/6/3.
 */

public class ScheduleModel implements ScheduleContract.Model{

    @Override
    public Observable<ResEntity<ScheduleCreateResEntity>> scheduleCreate(Map<String, Object> options) {
        Map<String, Object> params = RequestBaseParamsConfig.addRequestBaseParams2(options);
        return NetworkManager.getInstance().getApiInstance(ScheduleCreateApi.class, true)
                .scheduleCreate(params)
                .compose(RxSchedulers.<ResEntity<ScheduleCreateResEntity>>io_main());
    }

}
