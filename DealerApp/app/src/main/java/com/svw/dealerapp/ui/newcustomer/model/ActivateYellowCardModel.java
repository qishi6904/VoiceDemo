package com.svw.dealerapp.ui.newcustomer.model;

import com.svw.dealerapp.api.NetworkManager;
import com.svw.dealerapp.api.newcustomer.ActivateYellowCardApi;
import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.newcustomer.FollowupCreateReqEntity;
import com.svw.dealerapp.mvpframe.rx.RxSchedulers;
import com.svw.dealerapp.ui.newcustomer.contract.ActivateYellowCardContract;

import rx.Observable;

/**
 * Created by lijinkui on 2017/12/2.
 */

public class ActivateYellowCardModel implements ActivateYellowCardContract.Model {
    @Override
    public Observable<ResEntity<Object>> activateYellowCard(FollowupCreateReqEntity options) {
        return NetworkManager.getInstance().getApiInstance(ActivateYellowCardApi.class, true)
                .activateYellowCard(options)
                .compose(RxSchedulers.<ResEntity<Object>>io_main());
    }
}
