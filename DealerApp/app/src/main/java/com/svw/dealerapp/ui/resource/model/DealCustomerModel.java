package com.svw.dealerapp.ui.resource.model;

import android.text.TextUtils;

import com.svw.dealerapp.api.NetworkManager;
import com.svw.dealerapp.api.RequestBaseParamsConfig;
import com.svw.dealerapp.api.resource.DealCustomerApi;
import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.resource.DealCustomerEntity;
import com.svw.dealerapp.mvpframe.listfragment.ListFragmentContract;
import com.svw.dealerapp.mvpframe.rx.RxSchedulers;

import java.util.Map;

import rx.Observable;

/**
 * Created by qinshi on 7/28/2017.
 */

public class DealCustomerModel implements ListFragmentContract.Model<ResEntity<DealCustomerEntity>> {

    @Override
    public Observable<ResEntity<DealCustomerEntity>> getDataFromServer(String pageIndex, String pageSize, String orderType, String filter, String... moreParams) {
        Map<String, String> params = RequestBaseParamsConfig.getRequestBaseParams();
        params.put("pageIndex", pageIndex);
        params.put("pageSize", pageSize);
        if(!TextUtils.isEmpty(filter)) {
            params.put("filter", filter);
        }
        return NetworkManager.getInstance().getApiInstance(DealCustomerApi.class, true)
                .getDealCustomerList(params)
                .compose(RxSchedulers.<ResEntity<DealCustomerEntity>>io_main());
    }
}
