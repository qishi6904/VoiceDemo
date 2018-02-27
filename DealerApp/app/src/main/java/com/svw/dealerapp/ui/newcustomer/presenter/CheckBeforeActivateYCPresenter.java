package com.svw.dealerapp.ui.newcustomer.presenter;

import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.mvpframe.base.BaseObserver;
import com.svw.dealerapp.ui.newcustomer.contract.CheckBeforeActivateYCContract;
import com.svw.dealerapp.util.JLog;

import java.util.Map;

import rx.Observer;

/**
 * Created by lijinkui on 2017/12/22.
 */

public class CheckBeforeActivateYCPresenter extends CheckBeforeActivateYCContract.Presenter {
    private static final String TAG = "CheckBeforeActivateYCPresenter";

    public CheckBeforeActivateYCPresenter(CheckBeforeActivateYCContract.View view, CheckBeforeActivateYCContract.Model model) {
        setVM(view, model);
    }

    private BaseObserver<ResEntity<Object>> mCheckBeforeActivateYCObserver = new BaseObserver<ResEntity<Object>>() {
        @Override
        public void onCompleted() {
            JLog.d(TAG, "mCheckBeforeActivateYCObserver onCompleted");
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            JLog.d(TAG, "mCheckBeforeActivateYCObserver onError");
            e.printStackTrace();
            mView.checkBeforeActivateYCFail("error");
        }

        @Override
        public void onNext(ResEntity<Object> objectResEntity) {
            JLog.d(TAG, "RESULT: " + objectResEntity);
            if (objectResEntity.getRetCode().equals("200")) {
                mView.checkBeforeActivateYCSuccess(objectResEntity.getRetCode(),objectResEntity.getRetMessage());
            } else if (objectResEntity.getRetCode().equals("023004")) {
                mView.checkBeforeActivateYCSuccess(objectResEntity.getRetCode(),objectResEntity.getRetMessage());
            } else {
                mView.checkBeforeActivateYCFail("error");
            }
        }
    };

    @Override
    public void checkBeforeActivateYC(Map<String, Object> options) {
        mRxManager.add(mModel.checkBeforeActivateYC(options).subscribe(mCheckBeforeActivateYCObserver));
    }
}
