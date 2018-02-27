package com.svw.dealerapp.ui.mine.model;

import com.svw.dealerapp.api.NetworkManager;
import com.svw.dealerapp.api.RequestBaseParamsConfig;
import com.svw.dealerapp.api.mine.RejectApproveApi;
import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.mvpframe.rx.RxSchedulers;
import com.svw.dealerapp.ui.mine.contract.ApproveRejectContract;

import java.util.Map;

import rx.Observable;

/**
 * Created by qinshi on 6/7/2017.
 */

public class ApproveRejectModel implements ApproveRejectContract.Model {
    @Override
    public Observable<ResEntity<Object>> rejectApprove(Map<String, Object> options) {
        Map<String, Object> params = RequestBaseParamsConfig.addRequestBaseParams2(options);
        return NetworkManager.getInstance().getApiInstance(RejectApproveApi.class, true)
                .rejectApprove(params)
                .compose(RxSchedulers.<ResEntity<Object>>io_main());
    }
}
