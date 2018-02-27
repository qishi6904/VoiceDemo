package com.svw.dealerapp.ui.resource.model;

import android.text.TextUtils;

import com.svw.dealerapp.api.NetworkManager;
import com.svw.dealerapp.api.RequestBaseParamsConfig;
import com.svw.dealerapp.api.resource.PostActiveYellowCardApi;
import com.svw.dealerapp.api.resource.PostVipCustomerApi;
import com.svw.dealerapp.api.resource.SleepCustomerApi;
import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.resource.SleepCustomerEntity;
import com.svw.dealerapp.mvpframe.listfragment.ListFragmentContract;
import com.svw.dealerapp.mvpframe.rx.RxSchedulers;
import com.svw.dealerapp.ui.resource.contract.SleepCustomerContract;

import java.util.Map;

import rx.Observable;

/**
 * Created by qinshi on 7/28/2017.
 */

public class SleepCustomerModel implements ListFragmentContract.Model<ResEntity<SleepCustomerEntity>>,
        SleepCustomerContract.Model {

    @Override
    public Observable<ResEntity<SleepCustomerEntity>> getDataFromServer(String pageIndex, String pageSize, String orderType, String filter, String... moreParams) {
        Map<String, String> params = RequestBaseParamsConfig.getRequestBaseParams();
        params.put("pageIndex", pageIndex);
        params.put("pageSize", pageSize);
        params.put("type", "2");
        if(!TextUtils.isEmpty(filter)) {
            params.put("filter", filter);
        }
        return NetworkManager.getInstance().getApiInstance(SleepCustomerApi.class, true)
                .getSleepCustomerList(params)
                .compose(RxSchedulers.<ResEntity<SleepCustomerEntity>>io_main());
    }

    @Override
    public Observable<ResEntity<Object>> postActiveYellow(Map<String, Object> options) {
        Map<String, Object> params = RequestBaseParamsConfig.addRequestBaseParams2(options);
        return NetworkManager.getInstance().getApiInstance(PostActiveYellowCardApi.class, true)
                .postActiveYellowCard(params)
                .compose(RxSchedulers.<ResEntity<Object>>io_main());
    }

    @Override
    public Observable<ResEntity<Object>> postVipCustomer(String oppId, String isKeyuser) {
        Map<String, String> params = RequestBaseParamsConfig.getRequestBaseParams();
        params.put("oppId", oppId);
        params.put("isKeyuser", isKeyuser);
        return NetworkManager.getInstance().getApiInstance(PostVipCustomerApi.class, true)
                .postVipCustomer(params)
                .compose(RxSchedulers.<ResEntity<Object>>io_main());
    }
}
