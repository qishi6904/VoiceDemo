package com.svw.dealerapp.ui.mine.presenter;

import android.content.Context;

import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.mvpframe.listfragment.BaseHandlerPresenter;
import com.svw.dealerapp.mvpframe.listfragment.ToastObserver;
import com.svw.dealerapp.ui.mine.contract.ApproveRejectContract;

import java.util.Map;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by qinshi on 6/7/2017.
 */

public class ApproveRejectPresenter extends BaseHandlerPresenter<ApproveRejectContract.Model, ApproveRejectContract.View>
        implements ApproveRejectContract.Presenter {

    private Subscription rejectApproveSubscription;

    public ApproveRejectPresenter(ApproveRejectContract.View view, ApproveRejectContract.Model model){
        super(view, model);
    }

    @Override
    public void rejectApprove(Context context, Map<String, Object> options) {
        Observable observable = mModel.rejectApprove(options)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        ToastObserver observer = new ToastObserver<ResEntity<Object>>(mView, handler, handlerAction1){

            @Override
            public void dealResult(ResEntity<Object> entity) {
                if(null != entity && "200".equals(entity.getRetCode())) {
                    mView.rejectApproveSuccess();
                }else {
                    mView.showServerErrorToast();
                }
            }
        };

        rejectApproveSubscription = requestByToast(context, observable, observer, mView, handlerAction1);
    }

    @Override
    protected void doHandlerAction1() {     //拒绝审批请求超时后执行
        if(null != rejectApproveSubscription){
            rejectApproveSubscription.unsubscribe();
            mView.showTimeOutToast();
        }
        mView.hideLoadingDialog();
    }
}
