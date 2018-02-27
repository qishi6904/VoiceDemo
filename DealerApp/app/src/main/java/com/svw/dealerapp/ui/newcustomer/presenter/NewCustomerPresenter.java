package com.svw.dealerapp.ui.newcustomer.presenter;

import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.newcustomer.CarTypesEntity;
import com.svw.dealerapp.entity.newcustomer.OpportunityEntity;
import com.svw.dealerapp.entity.newcustomer.OpportunitySubmitReqEntity;
import com.svw.dealerapp.entity.newcustomer.OpportunitySubmitReqEntityV2;
import com.svw.dealerapp.entity.newcustomer.OpportunityUpdateReqEntity;
import com.svw.dealerapp.mvpframe.base.BaseObserver;
import com.svw.dealerapp.ui.newcustomer.contract.NewCustomerContract;
import com.svw.dealerapp.util.JLog;

import java.util.Map;

import rx.Observer;

/**
 * Created by lijinkui on 2017/5/15.
 */

public class NewCustomerPresenter extends NewCustomerContract.Presenter {

    private static final String TAG = "NewCustomerPresenter";

    public NewCustomerPresenter(){

    }

    public NewCustomerPresenter(NewCustomerContract.Model model, NewCustomerContract.View view) {
        setVM(view, model);
    }

    private BaseObserver<ResEntity<CarTypesEntity>> mCarTypeObserver = new BaseObserver<ResEntity<CarTypesEntity>>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            JLog.d(TAG, "onError");
            e.printStackTrace();
        }

        @Override
        public void onNext(ResEntity<CarTypesEntity> carTypesEntityResEntity) {
            JLog.d(TAG, "onNext");
            JLog.d(TAG, "carTypesEntityResEntity" + carTypesEntityResEntity);
            if (carTypesEntityResEntity.getRetCode().equals("200")) {
                mView.onQueryCarTypeSuccess(carTypesEntityResEntity.getRetData());
            } else {
                mView.onQueryCarTypeFailure(carTypesEntityResEntity.getRetMessage());
            }
        }
    };

    private BaseObserver<ResEntity<OpportunityEntity>> mOpportunityObserver = new BaseObserver<ResEntity<OpportunityEntity>>() {
        @Override
        public void onCompleted() {
            JLog.d(TAG, "onCompleted");
            mView.onRequestEnd();
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            JLog.d(TAG, "onError");
            e.printStackTrace();
            mView.onRequestEnd();
        }

        @Override
        public void onNext(ResEntity<OpportunityEntity> opportunityEntityResEntity) {
            JLog.d(TAG, "onNext");
            if (opportunityEntityResEntity.getRetCode().equals("200")) {
                JLog.d(TAG, "mOpportunityObserver OK");
                mView.onStepOneSubmitSuccess(opportunityEntityResEntity.getRetData());
            } else {
                mView.onStepOneSubmitError(opportunityEntityResEntity.getRetMessage());
            }
        }
    };

    @Override
    public void submitOpportunities(Map<String, String> options) {
        mRxManager.add(mModel.submitOpportunities(options).subscribe(mOpportunityObserver));
    }

    @Override
    public void submitOpportunities(OpportunitySubmitReqEntity entity) {
        mView.onRequestStart();
        mRxManager.add(mModel.submitOpportunities(entity).subscribe(mOpportunityObserver));
    }

    @Override
    public void submitOpportunitiesV2(OpportunitySubmitReqEntityV2 options) {
        mView.onRequestStart();
        mRxManager.add(mModel.submitOpportunitiesV2(options).subscribe(mOpportunityObserver));
    }

    @Override
    public void updateOpportunities(Map<String, String> options) {

    }

    @Override
    public void updateOpportunities(OpportunityUpdateReqEntity entity) {
        mView.onRequestStart();
        mRxManager.add(mModel.updateOpportunities(entity).subscribe(mOpportunityObserver));
    }

    @Override
    public void searchByCarType(String ecModelId) {
        mRxManager.add(mModel.searchByCarType(ecModelId).subscribe(mCarTypeObserver));
    }
}
