package com.svw.dealerapp.ui.login.presenter;

import com.svw.dealerapp.entity.SMResEntity;
import com.svw.dealerapp.entity.login.SMRefreshSubTokenEntity;
import com.svw.dealerapp.mvpframe.base.BaseObserver;
import com.svw.dealerapp.ui.login.contract.SMRefreshSubTokenContract;
import com.svw.dealerapp.util.JLog;

import java.util.Map;

import rx.Observer;

/**
 * Created by lijinkui on 2018/1/25.
 */

public class SMRefreshSubTokenPresenter extends SMRefreshSubTokenContract.Presenter {
    private static final String TAG = "SMRefreshSubTokenPresenter";

    public SMRefreshSubTokenPresenter(SMRefreshSubTokenContract.Model model, SMRefreshSubTokenContract.View view) {
        this.mModel = model;
        this.mView = view;
    }

    @Override
    public void smRefreshSubToken(Map<String, Object> options) {
        mRxManager.add(mModel.smRefreshSubToken(options).subscribe(mRefreshSubTokenObserver));
    }

    private BaseObserver<SMResEntity<SMRefreshSubTokenEntity>> mRefreshSubTokenObserver = new BaseObserver<SMResEntity<SMRefreshSubTokenEntity>>() {
        @Override
        public void onCompleted() {
            JLog.d(TAG, "mRefreshSubTokenObserver onCompleted");
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            JLog.d(TAG, "mRefreshSubTokenObserver onError");
            mView.getWebTokenFail("error");
        }

        @Override
        public void onNext(SMResEntity<SMRefreshSubTokenEntity> smRefreshSubTokenEntitySMResEntity) {
            JLog.d(TAG, "mRefreshSubTokenObserver onNext");
            if (smRefreshSubTokenEntitySMResEntity.getStatus().equals("200")) {
                SMRefreshSubTokenEntity smRefreshSubTokenEntity = smRefreshSubTokenEntitySMResEntity.getData();
                if (null != smRefreshSubTokenEntity) {
                    String subToken = smRefreshSubTokenEntity.getAccessToken();
                    saveSubToken(subToken);
                    mView.getWebTokenSuccess(subToken);
                }
            } else {
                mView.getWebTokenFail(smRefreshSubTokenEntitySMResEntity.getMessage());
            }
        }
    };

    private void saveSubToken(String subToken) {

    }

}
