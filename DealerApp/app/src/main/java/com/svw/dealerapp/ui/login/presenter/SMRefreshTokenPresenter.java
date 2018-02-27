package com.svw.dealerapp.ui.login.presenter;

import com.svw.dealerapp.entity.SMResEntity;
import com.svw.dealerapp.entity.login.SMRefreshTokenEntity;
import com.svw.dealerapp.mvpframe.base.BaseObserver;
import com.svw.dealerapp.ui.login.contract.SMRefreshTokenContract;
import com.svw.dealerapp.util.JLog;

import java.util.Map;

import rx.Observer;

/**
 * Created by lijinkui on 2018/1/25.
 */

public class SMRefreshTokenPresenter extends SMRefreshTokenContract.Presenter{
    private static final String TAG = "SMRefreshTokenPresenter";

    public SMRefreshTokenPresenter(SMRefreshTokenContract.Model model, SMRefreshTokenContract.View view) {
        this.mModel = model;
        this.mView = view;
    }

    @Override
    public void smRefreshToken(Map<String, Object> options) {
        mRxManager.add(mModel.smRefreshToken(options).subscribe(mRefreshTokenObserver));
    }

    private BaseObserver<SMResEntity<SMRefreshTokenEntity>> mRefreshTokenObserver = new BaseObserver<SMResEntity<SMRefreshTokenEntity>>() {
        @Override
        public void onCompleted() {
            JLog.d(TAG, "mRefreshTokenObserver onCompleted");
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            JLog.d(TAG, "mRefreshTokenObserver onError");
            mView.getWebTokenFail("error");
        }

        @Override
        public void onNext(SMResEntity<SMRefreshTokenEntity> smRefreshTokenEntitySMResEntity) {
            JLog.d(TAG, "mRefreshTokenObserver onNext");
            if (smRefreshTokenEntitySMResEntity.getStatus().equals("200")) {
                SMRefreshTokenEntity smRefreshTokenEntity = smRefreshTokenEntitySMResEntity.getData();
                if (null != smRefreshTokenEntity) {
                    String subToken = smRefreshTokenEntity.getAccess_token();
                    saveSubToken(subToken);
                    mView.getWebTokenSuccess(subToken);
                }
            } else {
                mView.getWebTokenFail(smRefreshTokenEntitySMResEntity.getMessage());
            }
        }
    };

    private void saveSubToken(String subToken) {

    }
}
