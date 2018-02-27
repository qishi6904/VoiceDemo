package com.svw.dealerapp.ui.newcustomer.presenter;

import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.newcustomer.OpportunityDetailEntity;
import com.svw.dealerapp.mvpframe.base.BaseObserver;
import com.svw.dealerapp.ui.newcustomer.contract.NewCustomerContract;
import com.svw.dealerapp.util.JLog;

import java.util.Map;

import rx.Observer;

/**
 * Created by xupan on 08/06/2017.
 */

public class CustomerDetailPresenterImpl extends NewCustomerContract.CustomerDetailPresenter {
    private static final String TAG = "CustomerDetailPresenterImpl";

    private BaseObserver<ResEntity<OpportunityDetailEntity>> mObserver = new BaseObserver<ResEntity<OpportunityDetailEntity>>() {
        @Override
        public void onCompleted() {
            mView.onRequestEnd();
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            JLog.d(TAG, "onError");
            e.printStackTrace();
            mView.onRequestError("");
        }

        @Override
        public void onNext(ResEntity<OpportunityDetailEntity> opportunityDetailEntityResEntity) {
            JLog.d(TAG, "onNext");
            if (opportunityDetailEntityResEntity.getRetCode().equals("200")) {
                mView.onQueryCustomerDetailSuccess(opportunityDetailEntityResEntity.getRetData());
            } else {
                mView.onQueryCustomerDetailFailure(opportunityDetailEntityResEntity.getRetMessage());
            }
        }
    };

    @Override
    public void queryCustomerDetail(String oppId) {
        mView.onRequestStart();
        mRxManager.add(mModel.showOpportunitiesDetail(oppId).subscribe(mObserver));
    }

    @Override
    public void postActiveYellow(Map<String, Object> options) {
        mRxManager.add(mModel.postActiveYellow(options).subscribe(new BaseObserver<ResEntity<Object>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mView.postActiveFail();
            }

            @Override
            public void onNext(ResEntity<Object> entity) {
                if (null != entity && "200".equals(entity.getRetCode())) {
                    mView.postActiveSuccess();
                } else {
                    mView.postActiveFail();
                }
            }
        }));
    }
}
