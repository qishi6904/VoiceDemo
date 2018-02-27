package com.svw.dealerapp.ui.newcustomer.presenter;

import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.newcustomer.FollowupCreateReqEntity;
import com.svw.dealerapp.mvpframe.base.BaseObserver;
import com.svw.dealerapp.ui.newcustomer.contract.NewCustomerContract;
import com.svw.dealerapp.util.JLog;

import rx.Observer;

/**
 * Created by xupan on 03/06/2017.
 */

public class NewCustomerStepThreePresenter extends NewCustomerContract.StepThreePresenter {
    private static final String TAG = "NewCustomerStepThreePresenter";

    private BaseObserver<ResEntity<Object>> observer = new BaseObserver<ResEntity<Object>>() {
        @Override
        public void onCompleted() {
            mView.onRequestEnd();
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            JLog.d(TAG, "onError");
            mView.onRequestEnd();
            mView.onSubmitFollowupInfoFailure("error");
        }

        @Override
        public void onNext(ResEntity<Object> objectResEntity) {
            JLog.d(TAG, "onNext");
            if (objectResEntity.getRetCode().equals("200")) {
                mView.onSubmitFollowupInfoSuccess(objectResEntity.getRetMessage());
            } else {
                mView.onSubmitFollowupInfoFailure(objectResEntity.getRetMessage());
            }
        }
    };

    @Override
    public void submitFollowupInfo(FollowupCreateReqEntity options) {
        mView.onRequestStart();
        mRxManager.add(mModel.submitFollowupInfo(options).subscribe(observer));
    }
}
