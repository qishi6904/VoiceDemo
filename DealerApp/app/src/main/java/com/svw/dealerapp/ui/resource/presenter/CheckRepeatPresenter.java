package com.svw.dealerapp.ui.resource.presenter;

import android.content.Context;

import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.home.CheckRepeatEntity;
import com.svw.dealerapp.mvpframe.listfragment.BaseHandlerPresenter;
import com.svw.dealerapp.mvpframe.listfragment.ToastObserver;
import com.svw.dealerapp.ui.resource.contract.CheckRepeatContract;

import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by lijinkui on 2017/11/11.
 */

public class CheckRepeatPresenter extends BaseHandlerPresenter<CheckRepeatContract.Model, CheckRepeatContract.View> implements CheckRepeatContract.Presenter {

    private Subscription checkRepeatSubscription;

    public CheckRepeatPresenter(CheckRepeatContract.View view, CheckRepeatContract.Model model) {
        super(view, model);
    }

    @Override
    public void checkRepeat(Context context, Map<String, Object> options) {
        Observable observable = mModel.checkRepeat(options)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        ToastObserver observer = new ToastObserver<ResEntity<List<CheckRepeatEntity>>>(mView, handler, handlerAction1) {
            @Override
            public void dealResult(ResEntity<List<CheckRepeatEntity>> entity) {
                if(null != entity && "200".equals(entity.getRetCode())) {
                    List<CheckRepeatEntity> list = entity.getRetData();
                    int repeatNum = 0;
                    if(null != list && list.size() > 0) {
                        repeatNum = list.size();
                    }
                    mView.checkRepeatSuccess(repeatNum);
                }else {
                    mView.checkRepeatFailed();
                }
            }
        };

        checkRepeatSubscription = requestByToast(context, observable, observer, mView, handlerAction1);
    }

    @Override
    protected void doHandlerAction1() {  //查重请求超时后执行
        if(null != checkRepeatSubscription){
            checkRepeatSubscription.unsubscribe();
            mView.showCheckRepeatTimeout();
        }
        mView.hideLoadingDialog();
    }
}
