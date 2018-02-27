package com.svw.dealerapp.ui.mine.model;

import com.svw.dealerapp.api.NetworkManager;
import com.svw.dealerapp.api.RequestBaseParamsConfig;
import com.svw.dealerapp.api.mine.LogoutApi;
import com.svw.dealerapp.api.mine.MineHomeApi;
import com.svw.dealerapp.api.upload.UploadApi;
import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.mine.LogoutEntity;
import com.svw.dealerapp.entity.mine.MineHomeEntity;
import com.svw.dealerapp.entity.mine.UploadEntity;
import com.svw.dealerapp.mvpframe.rx.RxSchedulers;
import com.svw.dealerapp.ui.mine.contract.MineContract;

import java.util.Map;

import okhttp3.MultipartBody;
import rx.Observable;

/**
 * Created by lijinkui on 2017/6/7.
 */

public class MineModel implements MineContract.Model {
    @Override
    public Observable<ResEntity<UploadEntity>> updateResetHeader(MultipartBody.Part body) {
        Map<String, Object> params = RequestBaseParamsConfig.getRequestBaseParams2();
        return NetworkManager.getInstance().getApiInstance(UploadApi.class, true)
                .uploadPic(body,params)
                .compose(RxSchedulers.<ResEntity<UploadEntity>>io_main());
    }

    @Override
    public Observable<ResEntity<MineHomeEntity>> getMineHomeData(Map<String, Object> options) {
        Map<String, Object> params = RequestBaseParamsConfig.addRequestBaseParams2(options);
        return NetworkManager.getInstance().getApiInstance(MineHomeApi.class, true)
                .getMineHomeData(params)
                .compose(RxSchedulers.<ResEntity<MineHomeEntity>>io_main());
    }

    @Override
    public Observable<ResEntity<LogoutEntity>> logout() {
        Map<String, Object> params = RequestBaseParamsConfig.getRequestBaseParams2();
        return NetworkManager.getInstance().getApiInstance(LogoutApi.class, true)
                .logout(params)
                .compose(RxSchedulers.<ResEntity<LogoutEntity>>io_main());
    }
}
