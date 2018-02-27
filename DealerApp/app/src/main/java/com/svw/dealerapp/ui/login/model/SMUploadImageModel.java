package com.svw.dealerapp.ui.login.model;

import com.svw.dealerapp.api.NetworkManager;
import com.svw.dealerapp.api.login.SMUploadImageApi;
import com.svw.dealerapp.entity.SMResEntity;
import com.svw.dealerapp.entity.login.SMUploadImageEntity;
import com.svw.dealerapp.mvpframe.rx.RxSchedulers;
import com.svw.dealerapp.ui.login.contract.SMUploadImageContract;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MultipartBody;
import rx.Observable;

/**
 * Created by lijinkui on 2018/1/25.
 */

public class SMUploadImageModel implements SMUploadImageContract.Model {
    @Override
    public Observable<SMResEntity<String>> smUploadImage(MultipartBody.Part body) {
        Map<String, Object> params = new HashMap<>();
        return NetworkManager.getInstance().getApiInstance(SMUploadImageApi.class, true)
                .smUploadImage(body, params)
                .compose(RxSchedulers.<SMResEntity<String>>io_main());
    }
}
