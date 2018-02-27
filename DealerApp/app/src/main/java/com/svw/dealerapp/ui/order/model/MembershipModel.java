package com.svw.dealerapp.ui.order.model;

import com.svw.dealerapp.api.NetworkManager;
import com.svw.dealerapp.api.order.MembershipApi;
import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.order.CheckMembershipReqEntity;
import com.svw.dealerapp.entity.order.CheckMembershipResEntity;
import com.svw.dealerapp.mvpframe.rx.RxSchedulers;
import com.svw.dealerapp.ui.order.contract.MembershipContract;

import rx.Observable;

/**
 * Created by xupan on 19/09/2017.
 */

public class MembershipModel implements MembershipContract.Model {

    @Override
    public Observable<ResEntity<CheckMembershipResEntity>> checkMembership(CheckMembershipReqEntity option) {
        return NetworkManager.getInstance().getApiInstance(MembershipApi.class, true).checkMembership(option)
                .compose(RxSchedulers.<ResEntity<CheckMembershipResEntity>>io_main());
    }

}
