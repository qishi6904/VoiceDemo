package com.svw.dealerapp.ui.login.presenter;

import com.svw.dealerapp.entity.SMResEntity;
import com.svw.dealerapp.mvpframe.base.BaseObserver;
import com.svw.dealerapp.ui.login.contract.SMResetPassContract;
import com.svw.dealerapp.util.JLog;

import java.util.Map;

import rx.Observer;

/**
 * Created by lijinkui on 2018/1/25.
 */

public class SMResetPassPresenter extends SMResetPassContract.Presenter {
    private static final String TAG = "SMResetPassPresenter";

    public SMResetPassPresenter(SMResetPassContract.Model model, SMResetPassContract.View view) {
        this.mModel = model;
        this.mView = view;
    }

    @Override
    public void smResetPass(Map<String, Object> options) {
        mRxManager.add(mModel.smResetPass(options).subscribe(mSMResetPassObserver));
    }

    private BaseObserver<SMResEntity<Object>> mSMResetPassObserver = new BaseObserver<SMResEntity<Object>>() {
        @Override
        public void onCompleted() {
            JLog.d(TAG, "mSMResetPassObserver onCompleted");
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            JLog.d(TAG, "mSMResetPassObserver onError");
            mView.showOldPawErrorToast();
        }

        @Override
        public void onNext(SMResEntity<Object> smResEntity) {
            JLog.d(TAG, "mSMResetPassObserver onNext");
            if (smResEntity.getStatus().equals("200")) {
                mView.showResetPawSuccess();
            } else {
                mView.showOldPawErrorToast();
            }
        }
    };
}
