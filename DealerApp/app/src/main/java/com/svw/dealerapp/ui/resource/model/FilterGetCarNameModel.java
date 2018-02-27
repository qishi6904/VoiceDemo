package com.svw.dealerapp.ui.resource.model;

import com.svw.dealerapp.api.NetworkManager;
import com.svw.dealerapp.api.RequestBaseParamsConfig;
import com.svw.dealerapp.api.newcustomer.CarTypesApi;
import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.newcustomer.CarTypesEntity;
import com.svw.dealerapp.mvpframe.rx.RxSchedulers;
import com.svw.dealerapp.ui.resource.contract.FilterGetCarNameContractor;

import java.util.Map;

import rx.Observable;

/**
 * Created by qinshi on 12/19/2017.
 */

public class FilterGetCarNameModel implements FilterGetCarNameContractor.Model {
    @Override
    public Observable<ResEntity<CarTypesEntity>> getCarTypeByCode(String code) {
        Map<String, String> params = RequestBaseParamsConfig.getRequestBaseParams();
        params.put("ecModelId", code);
        return NetworkManager.getInstance().getApiInstance(CarTypesApi.class, true)
                .searchByCarType(params)
                .compose(RxSchedulers.<ResEntity<CarTypesEntity>>io_main());
    }
}
