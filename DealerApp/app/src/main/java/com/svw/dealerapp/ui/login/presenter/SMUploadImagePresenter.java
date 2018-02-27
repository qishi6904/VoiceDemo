package com.svw.dealerapp.ui.login.presenter;

import android.text.TextUtils;

import com.svw.dealerapp.entity.SMResEntity;
import com.svw.dealerapp.mvpframe.base.BaseObserver;
import com.svw.dealerapp.ui.login.contract.SMUploadImageContract;
import com.svw.dealerapp.util.JLog;

import java.io.File;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observer;

/**
 * Created by lijinkui on 2018/1/25.
 */

public class SMUploadImagePresenter extends SMUploadImageContract.Presenter {

    private static final String TAG = "SMUploadImagePresenter";

    public SMUploadImagePresenter(SMUploadImageContract.Model model, SMUploadImageContract.View view) {
        mModel = model;
        mView = view;
    }

    @Override
    public void smUploadImage(File file) {
        RequestBody requestBody = RequestBody.create(MultipartBody.FORM, file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        mRxManager.add(mModel.smUploadImage(body).subscribe(mUploadImageObserver));
    }

    private BaseObserver<SMResEntity<String>> mUploadImageObserver = new BaseObserver<SMResEntity<String>>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            JLog.d(TAG, "onError");
            e.printStackTrace();
        }

        @Override
        public void onNext(SMResEntity<String> smUploadImageEntitySMResEntity) {
            JLog.d(TAG, "onNext");
            if (smUploadImageEntitySMResEntity == null) {
                return;
            }
            if ("200".equals(smUploadImageEntitySMResEntity.getStatus())) {
                String url = smUploadImageEntitySMResEntity.getData();
                if (!TextUtils.isEmpty(url)) {
                    mView.updateHeaderSuccess(smUploadImageEntitySMResEntity.getData());
                } else {
                    mView.updateHeaderFail(smUploadImageEntitySMResEntity.getMessage());
                }
            } else {
                mView.updateHeaderFail(smUploadImageEntitySMResEntity.getMessage());
            }
        }
    };
}
