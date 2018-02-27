package com.svw.dealerapp.ui.newcustomer.presenter;

import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.newcustomer.CarTypesEntity;
import com.svw.dealerapp.entity.newcustomer.OpportunityEntity;
import com.svw.dealerapp.entity.newcustomer.OpportunityUpdateReqEntity;
import com.svw.dealerapp.mvpframe.base.BaseObserver;
import com.svw.dealerapp.ui.newcustomer.contract.NewCustomerContract;
import com.svw.dealerapp.util.JLog;

import java.util.Map;

import rx.Observer;
import rx.Subscriber;

/**
 * Created by xupan on 20/06/2017.
 */

public class UpdateOpportunityPresenterImpl extends NewCustomerContract.UpdateOpportunityPresenter {

    private static final String TAG = "UpdateOpportunityPresenterImpl";

    private BaseObserver<ResEntity<OpportunityEntity>> mOpportunityObserver = new BaseObserver<ResEntity<OpportunityEntity>>() {
        @Override
        public void onCompleted() {
            JLog.d(TAG + "|OpportunityEntity", "onCompleted");
            mView.onRequestEnd();
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            JLog.d(TAG + "|OpportunityEntity", "onError");
            mView.onRequestEnd();
        }

        @Override
        public void onNext(ResEntity<OpportunityEntity> opportunityEntityResEntity) {
            JLog.d(TAG + "|OpportunityEntity", "onNext");
            if (opportunityEntityResEntity.getRetCode().equals("200")) {
                mView.onUpdateOpportunitySuccess(opportunityEntityResEntity.getRetData());
            } else {
                mView.onUpdateOpportunityFailure(opportunityEntityResEntity.getRetMessage());
            }
        }
    };

    private BaseObserver<ResEntity<CarTypesEntity>> mCarTypeObserver = new BaseObserver<ResEntity<CarTypesEntity>>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            e.printStackTrace();
        }

        @Override
        public void onNext(ResEntity<CarTypesEntity> carTypesEntityResEntity) {
            JLog.d(TAG, "onNext");
            JLog.d(TAG, "carTypesEntityResEntity" + carTypesEntityResEntity);
            if (carTypesEntityResEntity.getRetCode().equals("200")) {
                mView.onQueryCarTypeSuccess(carTypesEntityResEntity.getRetData());
            } else {
                mView.onQueryCarTypeError(carTypesEntityResEntity.getRetMessage());
            }
        }
    };

    @Override
    public void updateOpportunities(OpportunityUpdateReqEntity entity) {
        mView.onRequestStart();
        mRxManager.add(mModel.updateOpportunities(entity).subscribe(mOpportunityObserver));
    }

    @Override
    public void searchByCarType(String ecModelId) {
        mRxManager.add(mModel.searchByCarType(ecModelId).subscribe(mCarTypeObserver));
    }

    @Override
    public void submitRemark(Map<String, Object> options) {
        mRxManager.add(mModel.submitRemark(options).subscribe(new BaseObserver<ResEntity<Object>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }

            @Override
            public void onNext(ResEntity<Object> entity) {
                if(null != entity && "200".equals(entity.getRetCode())){
                    mView.submitRemarkSuccess();
                }else {
                    mView.submitRemarkFail();
                }
            }
        }));
    }
}
