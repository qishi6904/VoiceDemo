package com.svw.dealerapp.mvpframe.listfragment;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.svw.dealerapp.mvpframe.BasePresenter;
import com.svw.dealerapp.util.NetworkUtils;

import java.lang.ref.WeakReference;

import rx.Observable;
import rx.Subscription;

/**
 * Created by qinshi on 6/8/2017.
 */

public class BaseHandlerPresenter<M, V> extends BasePresenter<M, V> {

    protected static final int handlerAction1 = 9001;
    protected static final int handlerAction2 = 9002;
    protected static final int handlerAction3 = 9003;
    protected static final int handlerAction4 = 9004;
    protected static final int handlerAction5 = 9005;
    protected static final int handlerAction6 = 9006;
    protected static final int handlerAction7 = 9007;
    protected static final int handlerAction8 = 9008;
    protected static final int handlerAction9 = 9009;
    protected static final int timeoutMill = 30000;

    protected BasePresenterHandler handler;

    public BaseHandlerPresenter(V view, M model){
        this.mView = view;
        this.mModel = model;
        handler = new BasePresenterHandler(this);
    }

    /**
     * 异常结果用Toast提示的请求
     * @param context
     * @param observable
     * @param observer
     * @param showToastView
     * @param handlerActionFlag
     * @return
     */
    protected Subscription requestByToast(Context context, Observable observable, ToastObserver observer,
                                          ShowToastView showToastView, int handlerActionFlag){

        return requestByToast(context, observable, observer, showToastView, handlerActionFlag, true);
    }

    /**
     * 异常结果用Toast提示的请求
     * @param context
     * @param observable
     * @param observer
     * @param showToastView
     * @param handlerActionFlag
     * @param isShowLoading         是否加载框和异常提示
     * @return
     */
    protected Subscription requestByToast(Context context, Observable observable, ToastObserver observer,
                                          ShowToastView showToastView, int handlerActionFlag, boolean isShowLoading){

        if(!NetworkUtils.isNetworkAvailable(context)){
            if(isShowLoading) {
                showToastView.showNetWorkErrorToast();
            }
            return null;
        }

        if(isShowLoading) {
            showToastView.showLoadingDialog();
        }
        handler.sendEmptyMessageDelayed(handlerActionFlag, timeoutMill);

        Subscription subscription = observable.subscribe(observer);
        mRxManager.add(subscription);

        return subscription;
    }

    protected class BasePresenterHandler extends Handler {

        WeakReference<BasePresenter> weakReference;

        BasePresenterHandler(BasePresenter weakReference){
            this.weakReference = new WeakReference<>(weakReference);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case handlerAction1:
                    doHandlerAction1();
                    break;
                case handlerAction2:
                    doHandlerAction2();
                    break;
                case handlerAction3:
                    doHandlerAction3();
                    break;
                case handlerAction4:
                    doHandlerAction4();
                    break;
                case handlerAction5:
                    doHandlerAction5();
                    break;
                case handlerAction6:
                    doHandlerAction6();
                    break;
                case handlerAction7:
                    doHandlerAction7();
                    break;
                case handlerAction8:
                    doHandlerAction8();
                    break;
                case handlerAction9:
                    doHandlerAction9();
                    break;
            }
        }
    }

    protected void doHandlerAction1(){

    }
    protected void doHandlerAction2(){

    }
    protected void doHandlerAction3(){

    }
    protected void doHandlerAction4(){

    }
    protected void doHandlerAction5(){

    }
    protected void doHandlerAction6(){

    }
    protected void doHandlerAction7(){

    }
    protected void doHandlerAction8(){

    }
    protected void doHandlerAction9(){

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != handler){
            handler.removeCallbacksAndMessages(null);
        }
        if(mView instanceof ShowToastView){
            ((ShowToastView) mView).hideLoadingDialog();
        }
    }
}
