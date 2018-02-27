package com.svw.dealerapp.ui.mine.model;

import android.text.TextUtils;

import com.svw.dealerapp.api.NetworkManager;
import com.svw.dealerapp.api.RequestBaseParamsConfig;
import com.svw.dealerapp.api.mine.ScheduleCompleteApi;
import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.mine.ScheduleCompleteEntity;
import com.svw.dealerapp.mvpframe.listfragment.ListFragmentContract;
import com.svw.dealerapp.mvpframe.rx.RxSchedulers;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;

/**
 * Created by qinshi on 6/5/2017.
 */

public class ScheduleCompleteModel implements ListFragmentContract.Model<ResEntity<ScheduleCompleteEntity>> {

    @Override
    public Observable<ResEntity<ScheduleCompleteEntity>> getDataFromServer(String pageIndex, String pageSize, String orderType, String filter, String... moreParams) {
        Map<String, Object> options = new HashMap<>();
        options.put("pageIndex", pageIndex);
        options.put("pageSize", pageSize);
//        options.put("orderType", orderType);
        if(!TextUtils.isEmpty(filter)){
            options.put("filter", filter);
        }
        Map<String, Object> params = RequestBaseParamsConfig.addRequestBaseParams2(options);
        return NetworkManager.getInstance().getApiInstance(ScheduleCompleteApi.class, true)
                .getScheduleComplete(params)
                .compose(RxSchedulers.<ResEntity<ScheduleCompleteEntity>>io_main());
    }
}
