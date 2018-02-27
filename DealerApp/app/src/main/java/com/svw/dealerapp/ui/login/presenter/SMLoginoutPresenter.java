package com.svw.dealerapp.ui.login.presenter;

import com.svw.dealerapp.DealerApp;
import com.svw.dealerapp.entity.SMResEntity;
import com.svw.dealerapp.entity.login.SMLoginoutEntity;
import com.svw.dealerapp.mvpframe.base.BaseObserver;
import com.svw.dealerapp.ui.login.contract.SMLoginoutContract;
import com.svw.dealerapp.util.JLog;

import java.util.Map;

import rx.Observer;

/**
 * Created by lijinkui on 2018/1/25.
 */

public class SMLoginoutPresenter extends SMLoginoutContract.Presenter {

    private static final String TAG = "SMLoginPresenter";

    public SMLoginoutPresenter(SMLoginoutContract.Model model, SMLoginoutContract.View view) {
        this.mModel = model;
        this.mView = view;
    }

    @Override
    public void smLoginout(Map<String, Object> options) {
        mRxManager.add(mModel.smLoginout(options).subscribe(smLoginoutObserver));
    }

    private BaseObserver<SMResEntity<Object>> smLoginoutObserver = new BaseObserver<SMResEntity<Object>>() {
        @Override
        public void onCompleted() {
            JLog.d(TAG, "smLoginoutObserver onCompleted");
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            JLog.d(TAG, "smLoginoutObserver onError");
            mView.onLogoutFail();
        }

        @Override
        public void onNext(SMResEntity<Object> smLoginoutEntitySMResEntity) {
            JLog.d(TAG, "smLoginoutObserver onNext");
            if (smLoginoutEntitySMResEntity.getStatus().equals("200")) {
//                SMLoginoutEntity smLoginoutEntity = smLoginoutEntitySMResEntity.getData();
//                if (null != smLoginoutEntity) {
                    cleanUserInfo();
//                }
                mView.onLogoutSuccess();
            } else {
                mView.onLogoutFail();
            }
        }
    };

    private void cleanUserInfo() {
        DealerApp.dbHelper.delete("SMToken", null, null);
        DealerApp.dbHelper.delete("SMUser", null, null);
    }
}
