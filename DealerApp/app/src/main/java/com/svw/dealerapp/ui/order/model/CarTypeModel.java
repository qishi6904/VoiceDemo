package com.svw.dealerapp.ui.order.model;

import com.svw.dealerapp.api.NetworkManager;
import com.svw.dealerapp.api.RequestBaseParamsConfig;
import com.svw.dealerapp.api.newcustomer.CarTypesApi;
import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.newcustomer.CarTypesEntity;
import com.svw.dealerapp.mvpframe.rx.RxSchedulers;
import com.svw.dealerapp.ui.order.contract.CarTypeContract;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;

/**
 * Created by xupan on 09/08/2017.
 */

public class CarTypeModel implements CarTypeContract.Model {

    private Map<String, String> optionsForSearchByCarType(String ecModelId) {
        Map<String, String> options = new HashMap<>();
        options.put("ecModelId", ecModelId);
        options = RequestBaseParamsConfig.addRequestBaseParams(options);
        return options;
    }

    @Override
    public Observable<ResEntity<CarTypesEntity>> searchByCarType(String ecModelId) {
        return NetworkManager.getInstance().getApiInstance(CarTypesApi.class, true)
                .searchByCarType(optionsForSearchByCarType(ecModelId))
                .compose(RxSchedulers.<ResEntity<CarTypesEntity>>io_main());
    }

}
