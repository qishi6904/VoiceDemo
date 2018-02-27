package com.svw.dealerapp.ui.mine.model;

import android.text.TextUtils;

import com.svw.dealerapp.api.NetworkManager;
import com.svw.dealerapp.api.RequestBaseParamsConfig;
import com.svw.dealerapp.api.mine.ScheduleDeleteApi;
import com.svw.dealerapp.api.mine.ScheduleWaitApi;
import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.mine.ScheduleWaitEntity;
import com.svw.dealerapp.mvpframe.listfragment.ListFragmentContract;
import com.svw.dealerapp.mvpframe.rx.RxSchedulers;
import com.svw.dealerapp.ui.mine.contract.ScheduleWaitContract;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;

/**
 * Created by qinshi on 6/5/2017.
 */

public class ScheduleWaitModel implements ScheduleWaitContract.Model,
        ListFragmentContract.Model<ResEntity<ScheduleWaitEntity>>{

    @Override
    public Observable<ResEntity<Object>> cancelSchedule(Map<String, Object> options) {
        Map<String, Object> params = RequestBaseParamsConfig.addRequestBaseParams2(options);
//        String appmId = options.get("appmId").toString();
        return NetworkManager.getInstance().getApiInstance(ScheduleDeleteApi.class, true)
                .scheduleDelete(params)
                .compose(RxSchedulers.<ResEntity<Object>>io_main());
    }

    @Override
    public Observable<ResEntity<ScheduleWaitEntity>> getDataFromServer(String pageIndex, String pageSize, String orderType, String filter, String... moreParams) {
        Map<String, Object> options = new HashMap<>();
        options.put("pageIndex", pageIndex);
        options.put("pageSize", pageSize);
//        options.put("orderType", orderType);
        if(!TextUtils.isEmpty(filter)) {
            options.put("filter", filter);
        }
        Map<String, Object> params = RequestBaseParamsConfig.addRequestBaseParams2(options);
        return NetworkManager.getInstance().getApiInstance(ScheduleWaitApi.class, true)
                .getScheduleWait(params)
                .compose(RxSchedulers.<ResEntity<ScheduleWaitEntity>>io_main());
    }
}
