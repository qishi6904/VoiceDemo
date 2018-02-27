package com.svw.dealerapp.ui.newcustomer.model;

import com.svw.dealerapp.api.NetworkManager;
import com.svw.dealerapp.api.RequestBaseParamsConfig;
import com.svw.dealerapp.api.mine.ScheduleDeleteApi;
import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.mvpframe.rx.RxSchedulers;
import com.svw.dealerapp.ui.newcustomer.contract.CustomerDetailContract;

import java.util.Map;

import rx.Observable;

/**
 * Created by qinshi on 6/26/2017.
 */

public class CustomerDetailModel implements CustomerDetailContract.Model {
    @Override
    public Observable<ResEntity<Object>> cancelSchedule(Map<String, Object> options) {
        Map<String, Object> params = RequestBaseParamsConfig.addRequestBaseParams2(options);
        return NetworkManager.getInstance().getApiInstance(ScheduleDeleteApi.class, true)
                .scheduleDelete(params)
                .compose(RxSchedulers.<ResEntity<Object>>io_main());
    }
}
