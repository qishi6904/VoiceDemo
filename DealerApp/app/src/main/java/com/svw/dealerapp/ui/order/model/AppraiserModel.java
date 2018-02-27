package com.svw.dealerapp.ui.order.model;

import com.svw.dealerapp.api.NetworkManager;
import com.svw.dealerapp.api.RequestBaseParamsConfig;
import com.svw.dealerapp.api.order.AppraiserApi;
import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.order.AppraiserEntity;
import com.svw.dealerapp.mvpframe.listfragment.ListFragmentContract;
import com.svw.dealerapp.mvpframe.rx.RxSchedulers;

import java.util.Map;

import rx.Observable;

/**
 * Created by qinshi on 11/22/2017.
 */

public class AppraiserModel implements ListFragmentContract.Model<ResEntity<AppraiserEntity>>  {
    @Override
    public Observable<ResEntity<AppraiserEntity>> getDataFromServer(String pageIndex, String pageSize, String orderType, String filter, String... moreParams) {
        Map<String, String> params = RequestBaseParamsConfig.getRequestBaseParams();
        return NetworkManager.getInstance().getApiInstance(AppraiserApi.class, true)
                .getAppraiserList(params)
                .compose(RxSchedulers.<ResEntity<AppraiserEntity>>io_main());
    }
}
