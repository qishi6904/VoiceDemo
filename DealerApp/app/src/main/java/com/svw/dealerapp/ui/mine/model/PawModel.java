package com.svw.dealerapp.ui.mine.model;

import com.svw.dealerapp.api.NetworkManager;
import com.svw.dealerapp.api.RequestBaseParamsConfig;
import com.svw.dealerapp.api.mine.PawApi;
import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.mine.ResetPassEntity;
import com.svw.dealerapp.mvpframe.rx.RxSchedulers;
import com.svw.dealerapp.ui.mine.contract.PawContract;

import java.util.Map;

import rx.Observable;

/**
 * Created by qinshi on 6/1/2017.
 */

@Deprecated
public class PawModel implements PawContract.Model {
    @Override
    public Observable<ResEntity<ResetPassEntity>> postResetPaw(String userId, String oldPaw, String newPaw) {
        Map<String, String> params = RequestBaseParamsConfig.getRequestBaseParams();
        params.put("userId", userId);
        params.put("oldPassword", oldPaw);
        params.put("newPassword", newPaw);
        return NetworkManager.getInstance().getApiInstance(PawApi.class, true)
                .postResetPaw(params)
                .compose(RxSchedulers.<ResEntity<ResetPassEntity>>io_main());

    }
}
