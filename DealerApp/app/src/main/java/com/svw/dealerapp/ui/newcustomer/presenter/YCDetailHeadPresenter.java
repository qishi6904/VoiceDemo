package com.svw.dealerapp.ui.newcustomer.presenter;

import android.text.TextUtils;

import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.task.BenefitEntity;
import com.svw.dealerapp.mvpframe.base.BaseObserver;
import com.svw.dealerapp.ui.newcustomer.contract.YCDetailHeadContract;

import java.util.Map;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by lijinkui on 2017/6/21.
 */

public class YCDetailHeadPresenter extends YCDetailHeadContract.Presenter{

    private Subscription setVipSubscription;
    private Subscription getBenefitSubscription;
    private String currentStatus = "";

    public YCDetailHeadPresenter(YCDetailHeadContract.Model model,YCDetailHeadContract.View view){
        this.mModel = model;
        this.mView = view;
    }

    @Override
    public void postVipCustomer(String oppId, String isKeyuser) {
        currentStatus = isKeyuser;
        setVipSubscription = mModel.postVipCustomer(oppId,isKeyuser)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<ResEntity<Object>>(){

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }

                    @Override
                    public void onNext(ResEntity<Object> entity) {
                        if(null != entity && !TextUtils.isEmpty(entity.getRetCode()) && "200".equals(entity.getRetCode())){
                            if("0".equals(currentStatus)){
                                mView.setVipStatusSuccess("0");
                            }else if("1".equals(currentStatus)){
                                mView.setVipStatusSuccess("1");
                            }
                        }
                    }
                });
        mRxManager.add(setVipSubscription);
    }

    @Override
    public void getBenefitDate(Map<String, Object> options) {
        getBenefitSubscription = mModel.getBenefitDate(options)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<ResEntity<BenefitEntity>>(){

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }

                    @Override
                    public void onNext(ResEntity<BenefitEntity> entity) {
                        if(null != entity && !TextUtils.isEmpty(entity.getRetCode())){
                            if("200".equals(entity.getRetCode())) {
                                if (!TextUtils.isEmpty(entity.getRetData().getRight())) {
                                    mView.getBenefitDataSuccess(entity.getRetData().getRight());
                                } else {
                                    mView.getBenefitDataEmpty();
                                }
                            }else if("019002".equals(entity.getRetCode())){
                                mView.getBenefitDataEmpty();
                            }else {
                                mView.getBenefitDataFail();
                            }
                        }else {
                            mView.getBenefitDataFail();
                        }
                    }
                });
        mRxManager.add(getBenefitSubscription);
    }
}
