package com.svw.dealerapp.ui.login.model;

import com.svw.dealerapp.api.NetworkManager;
import com.svw.dealerapp.api.login.SMUserPrivilegeApi;
import com.svw.dealerapp.entity.SMResEntity;
import com.svw.dealerapp.entity.login.SMUserPrivilegeByAppIdEntity;
import com.svw.dealerapp.mvpframe.rx.RxSchedulers;
import com.svw.dealerapp.ui.login.contract.SMUserPrivilegeContract;

import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * Created by lijinkui on 2018/1/25.
 */

public class SMUserPrivilegeModel implements SMUserPrivilegeContract.Model {
    @Override
    public Observable<SMResEntity<List<SMUserPrivilegeByAppIdEntity>>> smUserPrivilege(Map<String,Object> options) {
        return NetworkManager.getInstance().getApiInstance(SMUserPrivilegeApi.class, true)
                .smUserPrivilege(options)
                .compose(RxSchedulers.<SMResEntity<List<SMUserPrivilegeByAppIdEntity>>>io_main());
    }
}
