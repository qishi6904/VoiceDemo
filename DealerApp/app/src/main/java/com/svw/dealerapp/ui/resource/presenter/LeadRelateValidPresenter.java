package com.svw.dealerapp.ui.resource.presenter;

import android.content.Context;

import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.mvpframe.listfragment.BaseHandlerPresenter;
import com.svw.dealerapp.mvpframe.listfragment.ToastObserver;
import com.svw.dealerapp.ui.resource.contract.LeadRelateValidContract;

import java.util.Map;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by lijinkui on 2017/11/22.
 */

public class LeadRelateValidPresenter extends BaseHandlerPresenter<LeadRelateValidContract.Model, LeadRelateValidContract.View> implements LeadRelateValidContract.Presenter {

    private Subscription leadRelateValidSubscription;

    public LeadRelateValidPresenter(LeadRelateValidContract.View view, LeadRelateValidContract.Model model) {
        super(view, model);
    }

    @Override
    public void leadRelateValid(Context context, Map<String, Object> options) {
        Observable observable = mModel.leadRelateValid(options)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        ToastObserver observer = new ToastObserver<ResEntity<Object>>(mView, handler, handlerAction1) {
            @Override
            public void dealResult(ResEntity<Object> entity) {
                if (null != entity) {
                    if ("200".equals(entity.getRetCode())) {
                        mView.leadRelateValidSuccess(entity.getRetMessage());
                    } else if ("10001".equals(entity.getRetCode()) ||
                            "10002".equals(entity.getRetCode())) {
                        mView.leadRelateValidFail(entity.getRetCode(), entity.getRetMessage());
                    }
                }
            }
        };

        leadRelateValidSubscription = requestByToast(context, observable, observer, mView, handlerAction1);
    }

    @Override
    protected void doHandlerAction1() {  //请求超时后执行
        if (null != leadRelateValidSubscription) {
            leadRelateValidSubscription.unsubscribe();
            mView.leadRelateValidTimeout();
        }
        mView.hideLoadingDialog();
    }
}
