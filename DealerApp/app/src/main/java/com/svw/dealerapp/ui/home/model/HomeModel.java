package com.svw.dealerapp.ui.home.model;

import android.text.TextUtils;

import com.svw.dealerapp.api.NetworkManager;
import com.svw.dealerapp.api.RequestBaseParamsConfig;
import com.svw.dealerapp.api.home.HomeApi;
import com.svw.dealerapp.api.mine.ScheduleDeleteApi;
import com.svw.dealerapp.api.report.ReportHomeApi;
import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.home.HomeEntity;
import com.svw.dealerapp.entity.report.ReportHomeEntity;
import com.svw.dealerapp.mvpframe.listfragment.ListFragmentContract;
import com.svw.dealerapp.mvpframe.rx.RxSchedulers;
import com.svw.dealerapp.ui.home.contract.HomeContract;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;

/**
 * Created by lijinkui on 2017/4/27.
 */

public class HomeModel implements HomeContract.Model, ListFragmentContract.Model<ResEntity<HomeEntity>>{
    @Override
    public Observable<ResEntity<Object>> cancelSchedule(Map<String, Object> options) {
        Map<String, Object> params = RequestBaseParamsConfig.addRequestBaseParams2(options);
//        String appmId = options.get("appmId").toString();
        return NetworkManager.getInstance().getApiInstance(ScheduleDeleteApi.class, true)
                .scheduleDelete(params)
                .compose(RxSchedulers.<ResEntity<Object>>io_main());
    }

    @Override
    public Observable<ResEntity<ReportHomeEntity>> getAutoPlayData(Map<String, Object> options) {
        Map<String, Object> params = RequestBaseParamsConfig.getRequestBaseParams2();
        return NetworkManager.getInstance().getApiInstance(ReportHomeApi.class, true)
                .getReportHomeData(params)
                .compose(RxSchedulers.<ResEntity<ReportHomeEntity>>io_main());
    }

    @Override
    public Observable<ResEntity<HomeEntity>> getDataFromServer(String pageIndex, String pageSize, String orderType, String filter, String... moreParams) {
        Map<String, Object> options = new HashMap<>();
        options.put("pageIndex", pageIndex);
        options.put("pageSize", pageSize);
//        options.put("orderType", orderType);
        if(!TextUtils.isEmpty(filter)) {
            options.put("filter", filter);
        }
        Map<String, Object> params = RequestBaseParamsConfig.addRequestBaseParams2(options);
        return NetworkManager.getInstance().getApiInstance(HomeApi.class, true)
                .getHomeData(params)
                .compose(RxSchedulers.<ResEntity<HomeEntity>>io_main());
    }
}
