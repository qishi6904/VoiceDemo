package com.svw.dealerapp.ui.task.model;

import android.text.TextUtils;

import com.svw.dealerapp.api.NetworkManager;
import com.svw.dealerapp.api.RequestBaseParamsConfig;
import com.svw.dealerapp.api.resource.PostVipCustomerApi;
import com.svw.dealerapp.api.task.BenefitApi;
import com.svw.dealerapp.api.task.TaskECommerceApi;
import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.task.BenefitEntity;
import com.svw.dealerapp.entity.task.TaskECommerceEntity;
import com.svw.dealerapp.mvpframe.listfragment.ListFragmentContract;
import com.svw.dealerapp.mvpframe.rx.RxSchedulers;
import com.svw.dealerapp.ui.task.contract.ECommerceContract;

import java.util.Map;

import rx.Observable;

/**
 * Created by qinshi on 5/19/2017.
 */

public class ECommerceModel implements ListFragmentContract.Model<ResEntity<TaskECommerceEntity>>, ECommerceContract.Model {
    @Override
    public Observable<ResEntity<TaskECommerceEntity>> getDataFromServer(String pageIndex, String pageSize, String orderType, String filter, String... moreParams) {
        Map<String, String> params = RequestBaseParamsConfig.getRequestBaseParams();
        params.put("pageIndex", pageIndex);
        params.put("pageSize", pageSize);
//        params.put("orderType", orderType);
        if(!TextUtils.isEmpty(filter)) {
            params.put("filter", filter);
        }
        return NetworkManager.getInstance().getApiInstance(TaskECommerceApi.class, true)
                .getTaskECommerceList(params)
                .compose(RxSchedulers.<ResEntity<TaskECommerceEntity>>io_main());
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

    @Override
    public Observable<ResEntity<BenefitEntity>> getBenefitDate(Map<String, Object> options) {
        Map<String, Object> params = RequestBaseParamsConfig.addRequestBaseParams2(options);
        return NetworkManager.getInstance().getApiInstance(BenefitApi.class, true)
                .getBenefitData(params)
                .compose(RxSchedulers.<ResEntity<BenefitEntity>>io_main());
    }
}
