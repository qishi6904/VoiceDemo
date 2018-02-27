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

/**
 * Created by xupan on 23/05/2017.
 */

public class NewCustomerStepTwoPresenter extends NewCustomerContract.StepTwoPresenter {

    private static final String TAG = "NewCustomerStepTwoPresenter";

    private BaseObserver<ResEntity<CarTypesEntity>> mCarTypeObserver = new BaseObserver<ResEntity<CarTypesEntity>>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            JLog.d(TAG, "onError");
        }

        @Override
        public void onNext(ResEntity<CarTypesEntity> carTypesEntityResEntity) {
            JLog.d(TAG + "|mCarTypeObserver", "onNext");
//            JLog.d(TAG, "carTypesEntityResEntity" + carTypesEntityResEntity);
            if (carTypesEntityResEntity.getRetCode().equals("200")) {
                mView.onQueryCarTypeSuccess(carTypesEntityResEntity.getRetData());
            }
        }
    };

    private BaseObserver<ResEntity<OpportunityEntity>> mOpportunityObserver = new BaseObserver<ResEntity<OpportunityEntity>>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            JLog.d(TAG + "|OpportunityEntity", "onError");
        }

        @Override
        public void onNext(ResEntity<OpportunityEntity> opportunityEntityResEntity) {
            JLog.d(TAG + "|OpportunityEntity", "onNext");
            if (opportunityEntityResEntity.getRetCode().equals("200")) {
                mView.onUpdateOpportunitySuccess(opportunityEntityResEntity.getRetData());
            } else {
                mView.onUpdateOpportunityFailure();
            }
        }
    };

    @Override
    public void searchByCarType(String ecModelId) {
        mRxManager.add(mModel.searchByCarType(ecModelId).subscribe(mCarTypeObserver));
    }

    @Override
    public void updateOpportunities(Map<String, String> options) {
        mRxManager.add(mModel.updateOpportunities(options).subscribe(mOpportunityObserver));
    }

    @Override
    public void updateOpportunities(OpportunityUpdateReqEntity entity) {
        mRxManager.add(mModel.updateOpportunities(entity).subscribe(mOpportunityObserver));
    }
}
