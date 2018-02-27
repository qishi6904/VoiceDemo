package com.svw.dealerapp.ui.resource.model;

import com.svw.dealerapp.api.NetworkManager;
import com.svw.dealerapp.api.home.CheckRepeatApi;
import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.home.CheckRepeatEntity;
import com.svw.dealerapp.mvpframe.rx.RxSchedulers;
import com.svw.dealerapp.ui.resource.contract.CheckRepeatContract;

import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * Created by lijinkui on 2017/11/11.
 */

public class CheckRepeatModel implements CheckRepeatContract.Model {
    @Override
    public Observable<ResEntity<List<CheckRepeatEntity>>> checkRepeat(Map<String, Object> options) {
        return NetworkManager.getInstance().getApiInstance(CheckRepeatApi.class, true)
                .checkRepeat(options)
                .compose(RxSchedulers.<ResEntity<List<CheckRepeatEntity>>>io_main());
    }
}
