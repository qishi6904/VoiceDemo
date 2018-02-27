package com.svw.dealerapp.ui.mine.model;

import com.svw.dealerapp.api.NetworkManager;
import com.svw.dealerapp.api.RequestBaseParamsConfig;
import com.svw.dealerapp.api.mine.ScheduleUpdateApi;
import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.mvpframe.rx.RxSchedulers;
import com.svw.dealerapp.ui.mine.contract.ScheduleUpdateContract;

import java.util.Map;

import rx.Observable;

/**
 * Created by qinshi on 6/5/2017.
 */

public class ScheduleUpdateModel implements ScheduleUpdateContract.Model {
    @Override
    public Observable<ResEntity<Object>> updateSchedule(Map<String, Object> options) {
        Map<String, Object> params = RequestBaseParamsConfig.addRequestBaseParams2(options);
        return NetworkManager.getInstance().getApiInstance(ScheduleUpdateApi.class, true)
                .scheduleUpdate(params)
                .compose(RxSchedulers.<ResEntity<Object>>io_main());
    }
}
