package com.svw.dealerapp.ui.login.presenter;

import android.content.ContentValues;
import android.database.Cursor;

import com.svw.dealerapp.DealerApp;
import com.svw.dealerapp.api.NetworkManager;
import com.svw.dealerapp.entity.SMResEntity;
import com.svw.dealerapp.entity.login.SMLoginEntity;
import com.svw.dealerapp.mvpframe.base.BaseObserver;
import com.svw.dealerapp.ui.login.contract.SMLoginContract;
import com.svw.dealerapp.util.JLog;

import java.util.Map;

import rx.Observer;

/**
 * Created by lijinkui on 2018/1/25.
 */

public class SMLoginPresenter extends SMLoginContract.Presenter {

    private static final String TAG = "SMLoginPresenter";

    private boolean isNewUser = true;

    public SMLoginPresenter(SMLoginContract.Model model, SMLoginContract.View view) {
        this.mModel = model;
        this.mView = view;
    }

    @Override
    public void smLogin(Map<String, Object> options) {
        mRxManager.add(mModel.smLogin(options).subscribe(mLoginResultObserver));
    }

    private BaseObserver<SMResEntity<SMLoginEntity>> mLoginResultObserver = new BaseObserver<SMResEntity<SMLoginEntity>>() {
        @Override
        public void onCompleted() {
            JLog.d(TAG, "mLoginResultObserver onCompleted");
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            JLog.d(TAG, "mLoginResultObserver onError");
            e.printStackTrace();
            mView.onLoginError("error");
        }

        @Override
        public void onNext(SMResEntity<SMLoginEntity> smLoginEntitySMResEntity) {
            JLog.d(TAG, "onNext");
            JLog.d(TAG, "RESULT: " + smLoginEntitySMResEntity);
            if (smLoginEntitySMResEntity.getStatus().equals("200")) {
                SMLoginEntity smLoginEntity = smLoginEntitySMResEntity.getData();
                if (null != smLoginEntity) {
                    saveToken(smLoginEntity);
                }
                mView.onLoginSuccess(isNewUser, smLoginEntitySMResEntity);
            } else {
                mView.onLoginError(smLoginEntitySMResEntity.getMessage());
            }
        }
    };

    private void saveToken(SMLoginEntity smLoginEntity) {

        ContentValues values = new ContentValues();
        values.put("accessToken", smLoginEntity.getAccessToken());
        values.put("expireAt", smLoginEntity.getExpireAt());
        values.put("refreshToken", smLoginEntity.getRefreshToken());
        values.put("refreshExpireAt", smLoginEntity.getRefreshExpireAt());
        values.put("accountId", smLoginEntity.getOpenId());
        values.put("clientId", smLoginEntity.getClientId());
        values.put("scope", smLoginEntity.getScope());
        values.put("source", smLoginEntity.getSource());
        values.put("accessSubToken", smLoginEntity.getAccessToken());

        Cursor cursor = DealerApp.dbHelper.rawQuery("select * from SMToken", null);
        if (cursor != null) {
            if (cursor.moveToNext()) {
                String oldUserId = cursor.getString(cursor.getColumnIndex("accountId"));
                if (oldUserId.equals(smLoginEntity.getOpenId())) {
                    isNewUser = false;
                }
                DealerApp.dbHelper.update("SMToken", values, null, null);
                DealerApp.ACCESS_TOKEN = smLoginEntity.getAccessToken();
            } else {
                if (DealerApp.dbHelper.insert("SMToken", values)) {
                    DealerApp.ACCESS_TOKEN = smLoginEntity.getAccessToken();
                }
            }
            NetworkManager.getInstance().setToken(DealerApp.ACCESS_TOKEN);
            cursor.close();
        }
    }

}
