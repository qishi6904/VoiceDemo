package com.svw.dealerapp.ui.report.model;


import com.svw.dealerapp.api.NetworkManager;
import com.svw.dealerapp.api.RequestBaseParamsConfig;
import com.svw.dealerapp.api.report.ReportHomeApi;
import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.report.ReportHomeEntity;
import com.svw.dealerapp.mvpframe.listfragment.ListFragmentContract;
import com.svw.dealerapp.mvpframe.rx.RxSchedulers;
import com.svw.dealerapp.ui.report.contract.ReportHomeContract;

import java.util.Map;

import rx.Observable;

/**
 * Created by qinshi on 8/29/2017.
 */

public class ReportHomeModel implements ListFragmentContract.Model<ResEntity<ReportHomeEntity>>,ReportHomeContract.Model {
    @Override
    public Observable<ResEntity<ReportHomeEntity>> getDataFromServer(String pageIndex, String pageSize, String orderType, String filter, String... moreParams) {
        Map<String, Object> params = RequestBaseParamsConfig.getRequestBaseParams2();
        return NetworkManager.getInstance().getApiInstance(ReportHomeApi.class, true)
                .getReportHomeData(params)
                .compose(RxSchedulers.<ResEntity<ReportHomeEntity>>io_main());
    }
}
