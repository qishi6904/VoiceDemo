package com.svw.dealerapp.ui.newcustomer.model;

import com.svw.dealerapp.api.NetworkManager;
import com.svw.dealerapp.api.RequestBaseParamsConfig;
import com.svw.dealerapp.api.resource.PostVipCustomerApi;
import com.svw.dealerapp.api.task.BenefitApi;
import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.task.BenefitEntity;
import com.svw.dealerapp.mvpframe.rx.RxSchedulers;
import com.svw.dealerapp.ui.newcustomer.contract.YCDetailHeadContract;

import java.util.Map;

import rx.Observable;

/**
 * Created by lijinkui on 2017/6/21.
 */

public class YCDetailHeadModel implements YCDetailHeadContract.Model {

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
