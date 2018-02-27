package com.svw.dealerapp.mvpframe.listfragment;

import android.os.Handler;

import com.svw.dealerapp.mvpframe.base.BaseObserver;

import rx.Observer;

/**
 * Created by qinshi on 6/8/2017.
 */

public abstract class ToastObserver<T> extends BaseObserver<T> {

    private ShowToastView showToastView;
    private Handler handler;
    private int handlerActionFlag;
    private boolean isShowLoading = true;

    public ToastObserver(ShowToastView showToastView, Handler handler, int handlerActionFlag){
        this.showToastView = showToastView;
        this.handler = handler;
        this.handlerActionFlag = handlerActionFlag;
    }

    public ToastObserver(ShowToastView showToastView, Handler handler, int handlerActionFlag, boolean isShowLoading){
        this(showToastView, handler, handlerActionFlag);
        this.isShowLoading = isShowLoading;
    }

    @Override
    public void onCompleted() {
        if(null != handler) {
            handler.removeMessages(handlerActionFlag);
        }
        if(isShowLoading) {
            showToastView.hideLoadingDialog();
        }
    }

    @Override
    public void onServerError(Throwable e) {
        if(null != handler) {
            handler.removeMessages(handlerActionFlag);
        }
        if(isShowLoading) {
            showToastView.hideLoadingDialog();
            showToastView.showServerErrorToast();
        }
    }

    @Override
    public void onNext(T t) {
        if(null != handler) {
            handler.removeMessages(handlerActionFlag);
        }
        dealResult(t);
        if(isShowLoading) {
            showToastView.hideLoadingDialog();
        }
    }

    public abstract void dealResult(T t);
}
