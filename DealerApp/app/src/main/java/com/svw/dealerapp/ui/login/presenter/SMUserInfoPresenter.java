package com.svw.dealerapp.ui.login.presenter;

import android.content.ContentValues;

import com.svw.dealerapp.BuildConfig;
import com.svw.dealerapp.DealerApp;
import com.svw.dealerapp.entity.SMResEntity;
import com.svw.dealerapp.entity.login.SMUserInfoEntity;
import com.svw.dealerapp.mvpframe.base.BaseObserver;
import com.svw.dealerapp.ui.login.contract.SMUserInfoContract;
import com.svw.dealerapp.util.AesUtils;
import com.svw.dealerapp.util.JLog;

import java.util.Map;

import rx.Observer;

/**
 * Created by lijinkui on 2018/1/25.
 */

public class SMUserInfoPresenter extends SMUserInfoContract.Presenter {
    private static final String TAG = "SMUserInfoPresenter";

    public SMUserInfoPresenter(SMUserInfoContract.Model model, SMUserInfoContract.View view) {
        this.mModel = model;
        this.mView = view;
    }

    @Override
    public void smUserInfo(Map<String, Object> options) {
        mRxManager.add(mModel.smUserInfo(options).subscribe(mSMUserInfoObserver));
    }

    private BaseObserver<SMResEntity<SMUserInfoEntity>> mSMUserInfoObserver = new BaseObserver<SMResEntity<SMUserInfoEntity>>() {
        @Override
        public void onCompleted() {
            JLog.d(TAG, "mSMUserInfoObserver onCompleted");
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            JLog.d(TAG, "mSMUserInfoObserver onError");
            mView.onUserInfoError("error");
        }

        @Override
        public void onNext(SMResEntity<SMUserInfoEntity> smUserInfoEntitySMResEntity) {
            JLog.d(TAG, "mSMUserInfoObserver onNext");
            if (smUserInfoEntitySMResEntity.getStatus().equals("200")) {
                SMUserInfoEntity smUserInfoEntity = smUserInfoEntitySMResEntity.getData();
                if (null != smUserInfoEntity) {
                    saveUserInfo(smUserInfoEntity);
                }
                mView.onUserInfoSuccess(smUserInfoEntity);
            } else {
                mView.onUserInfoError(smUserInfoEntitySMResEntity.getMessage());
            }
        }
    };

    private void saveUserInfo(SMUserInfoEntity smUserInfoEntity) {
        DealerApp.dbHelper.delete("SMUser", null, null);
        ContentValues values = new ContentValues();
        values.put("accountId", AesUtils.encrypt(BuildConfig.AES_SECRET_KEY, smUserInfoEntity.getAccountId()));
        values.put("account", AesUtils.encrypt(BuildConfig.AES_SECRET_KEY, smUserInfoEntity.getAccount()));
        values.put("displayName", AesUtils.encrypt(BuildConfig.AES_SECRET_KEY, smUserInfoEntity.getDisplayName()));
        values.put("name", AesUtils.encrypt(BuildConfig.AES_SECRET_KEY, smUserInfoEntity.getName()));
        values.put("status", AesUtils.encrypt(BuildConfig.AES_SECRET_KEY, smUserInfoEntity.getStatus()));
        values.put("email", AesUtils.encrypt(BuildConfig.AES_SECRET_KEY, smUserInfoEntity.getEmail()));
        values.put("mobile", AesUtils.encrypt(BuildConfig.AES_SECRET_KEY, smUserInfoEntity.getMobile()));
        values.put("avatarUrl", AesUtils.encrypt(BuildConfig.AES_SECRET_KEY, smUserInfoEntity.getAvatarUrl()));
        values.put("currentCategory", AesUtils.encrypt(BuildConfig.AES_SECRET_KEY, smUserInfoEntity.getCurrentCategory()));
        values.put("currentRootCategory", AesUtils.encrypt(BuildConfig.AES_SECRET_KEY, smUserInfoEntity.getCurrentRootCategory()));
        values.put("activate", AesUtils.encrypt(BuildConfig.AES_SECRET_KEY, Boolean.toString(smUserInfoEntity.getActivate())));
        values.put("districtId", AesUtils.encrypt(BuildConfig.AES_SECRET_KEY, smUserInfoEntity.getDistrictId()));
        values.put("orgId", AesUtils.encrypt(BuildConfig.AES_SECRET_KEY, smUserInfoEntity.getOrgId()));
        values.put("orgName", AesUtils.encrypt(BuildConfig.AES_SECRET_KEY, smUserInfoEntity.getOrgName()));
        values.put("depId", AesUtils.encrypt(BuildConfig.AES_SECRET_KEY, smUserInfoEntity.getDepId()));
        values.put("depName", AesUtils.encrypt(BuildConfig.AES_SECRET_KEY, smUserInfoEntity.getDepName()));
        values.put("shopId", AesUtils.encrypt(BuildConfig.AES_SECRET_KEY, smUserInfoEntity.getShopId()));
        DealerApp.dbHelper.insert("SMUser", values);
    }
}
