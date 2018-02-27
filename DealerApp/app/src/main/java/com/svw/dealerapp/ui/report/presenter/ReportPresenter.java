package com.svw.dealerapp.ui.report.presenter;

import android.text.TextUtils;

import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.report.WebTokenEntity;
import com.svw.dealerapp.mvpframe.base.BaseObserver;
import com.svw.dealerapp.ui.report.contract.ReportContract;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by lijinkui on 2017/8/1.
 */

@Deprecated
public class ReportPresenter extends ReportContract.Presenter{

    private Subscription updateWebTokenSubscription;

    public ReportPresenter(ReportContract.Model model, ReportContract.View view){
        this.mModel = model;
        this.mView = view;
    }

    @Override
    public void updateWebToken(String webToken) {
        updateWebTokenSubscription = mModel.updateWebToken(webToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<ResEntity<WebTokenEntity>>(){

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mView.getWebTokenFail("Get WebToken Fail");
                    }

                    @Override
                    public void onNext(ResEntity<WebTokenEntity> entity) {
                        if(null != entity && !TextUtils.isEmpty(entity.getRetCode()) && "200".equals(entity.getRetCode())){
                            mView.getWebTokenSuccess(entity.getRetData().getTokenInfo().getWebTokent());
                        }
                    }
                });
        mRxManager.add(updateWebTokenSubscription);
    }

}
