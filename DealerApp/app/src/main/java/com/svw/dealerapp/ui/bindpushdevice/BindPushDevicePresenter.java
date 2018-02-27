package com.svw.dealerapp.ui.bindpushdevice;

import android.app.Activity;
import android.content.SharedPreferences;

import com.svw.dealerapp.DealerApp;
import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.mvpframe.base.BaseObserver;
import com.svw.dealerapp.util.JLog;

import java.util.HashMap;
import java.util.Map;

import rx.Observer;

/**
 * Created by lijinkui on 2017/7/7.
 */

public class BindPushDevicePresenter extends BindPushDeviceContract.Presenter {

    private static final String TAG = "BindPushDevicePresenter";

    public BindPushDevicePresenter(BindPushDeviceContract.Model model, BindPushDeviceContract.View view){
        this.mModel = model;
        this.mView = view;
    }

    private BaseObserver<ResEntity<Object>> mRegisterObserver = new BaseObserver<ResEntity<Object>>() {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            e.printStackTrace();
        }

        @Override
        public void onNext(ResEntity<Object> objectResEntity) {
            if (objectResEntity.getRetCode().equals("200")) {
                JLog.d(TAG,"push register ok");
                mView.onBindPushDeviceSuccess();
            }else{
                JLog.d(TAG,"push register error");
                mView.onBindPushDeviceFail();
            }
        }
    };

    private BaseObserver<ResEntity<Object>> mUnregisterObserver = new BaseObserver<ResEntity<Object>>() {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            e.printStackTrace();
        }

        @Override
        public void onNext(ResEntity<Object> objectResEntity) {
            if (objectResEntity.getRetCode().equals("200")) {
                JLog.d(TAG,"push unRegister ok");
                mView.onBindPushDeviceSuccess();
            }else{
                JLog.d(TAG,"push unRegister error");
                mView.onBindPushDeviceFail();
            }
        }
    };

    @Override
    public void aliPushRegisterDevice() {
        String carrier= android.os.Build.MANUFACTURER;
        SharedPreferences aliPushDeviceIdSP = DealerApp.getContext().getSharedPreferences("appSettingSP", Activity.MODE_PRIVATE);
        String aliPushDeviceId = aliPushDeviceIdSP.getString("aliPushDeviceId", "");
        Map<String,Object> params = new HashMap<>();
        params.put("deviceId",aliPushDeviceId);
        params.put("deviceType","101");
        params.put("appName","com.svw.dealerapp");
        mRxManager.add(mModel.aliPushRegisterDevice(params).subscribe(mRegisterObserver));
    }

    @Override
    public void aliPushUnregisterDevice() {
        Map<String,Object> params = new HashMap<>();
        mRxManager.add(mModel.aliPushUnregisterDevice(params).subscribe(mUnregisterObserver));
    }
}
