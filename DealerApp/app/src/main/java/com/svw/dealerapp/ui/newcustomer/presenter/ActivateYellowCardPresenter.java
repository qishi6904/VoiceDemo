package com.svw.dealerapp.ui.newcustomer.presenter;

import android.content.Context;

import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.newcustomer.FollowupCreateReqEntity;
import com.svw.dealerapp.mvpframe.listfragment.BaseHandlerPresenter;
import com.svw.dealerapp.mvpframe.listfragment.ToastObserver;
import com.svw.dealerapp.ui.newcustomer.contract.ActivateYellowCardContract;

import java.util.Map;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by lijinkui on 2017/12/2.
 */

public class ActivateYellowCardPresenter extends BaseHandlerPresenter<ActivateYellowCardContract.Model, ActivateYellowCardContract.View> implements ActivateYellowCardContract.Presenter {

    private Subscription activateYellowCardSubscription;

    public ActivateYellowCardPresenter(ActivateYellowCardContract.View view, ActivateYellowCardContract.Model model) {
        super(view, model);
    }

    @Override
    public void activateYellowCard(Context context, FollowupCreateReqEntity options) {
        Observable observable = mModel.activateYellowCard(options)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        ToastObserver observer = new ToastObserver<ResEntity<Object>>(mView, handler, handlerAction1) {
            @Override
            public void dealResult(ResEntity<Object> entity) {
                if (null != entity) {
                    if ("200".equals(entity.getRetCode())) {
                        mView.activateYellowCardSuccess(entity.getRetMessage());
                    } else {
                        mView.activateYellowCardFail(entity.getRetMessage());
                    }
                }
            }
        };

        activateYellowCardSubscription = requestByToast(context, observable, observer, mView, handlerAction1);
    }

    @Override
    protected void doHandlerAction1() {  //请求超时后执行
        if (null != activateYellowCardSubscription) {
            activateYellowCardSubscription.unsubscribe();
            mView.activateYellowCardTimeout();
        }
        mView.hideLoadingDialog();
    }

}
