package com.svw.dealerapp.ui.newcustomer.presenter;

import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.newcustomer.FollowupCreateReqEntity;
import com.svw.dealerapp.mvpframe.base.BaseObserver;
import com.svw.dealerapp.ui.newcustomer.contract.NewCustomerContract;
import com.svw.dealerapp.util.JLog;

import rx.Observer;

/**
 * Created by xupan on 05/06/2017.
 */

public class NewCustomerStepFourPresenter extends NewCustomerContract.StepFourPresenter {
    private static final String TAG = "NewCustomerStepFourPresenter";

    private BaseObserver<ResEntity<Object>> observer = new BaseObserver<ResEntity<Object>>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            JLog.d(TAG, "onError");
            mView.onSubmitFollowupInfoFailure();
        }

        @Override
        public void onNext(ResEntity<Object> objectResEntity) {
            JLog.d(TAG, "onNext");
            if (objectResEntity.getRetCode().equals("200")) {
                mView.onSubmitFollowupInfoSuccess();
            } else {
                mView.onSubmitFollowupInfoFailure();
            }
        }
    };

    @Override
    public void submitFollowupInfo(FollowupCreateReqEntity options) {
        mRxManager.add(mModel.submitFollowupInfo(options).subscribe(observer));
    }
}
