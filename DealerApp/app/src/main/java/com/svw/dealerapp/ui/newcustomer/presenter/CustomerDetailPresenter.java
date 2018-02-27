package com.svw.dealerapp.ui.newcustomer.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.mvpframe.listfragment.BaseHandlerPresenter;
import com.svw.dealerapp.mvpframe.listfragment.ToastObserver;
import com.svw.dealerapp.ui.newcustomer.contract.CustomerDetailContract;

import java.util.Map;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by qinshi on 6/26/2017.
 */

public class CustomerDetailPresenter extends BaseHandlerPresenter<CustomerDetailContract.Model, CustomerDetailContract.View> implements CustomerDetailContract.Presenter {

    private Subscription postCancelSchedule;

    public CustomerDetailPresenter(CustomerDetailContract.View view, CustomerDetailContract.Model model) {
        super(view, model);
    }

    @Override
    public void cancelSchedule(Context context, Map<String, Object> options, final int position) {
        Observable observable = mModel.cancelSchedule(options)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        ToastObserver observer = new ToastObserver<ResEntity<Object>>(mView, handler, handlerAction1) {
            @Override
            public void dealResult(ResEntity<Object> entity) {
                if (null != entity && !TextUtils.isEmpty(entity.getRetCode()) && "200".equals(entity.getRetCode())) {
                    mView.cancelScheduleSuccess(position);
                } else {
                    mView.cancelScheduleFailed();
                }
            }
        };

        postCancelSchedule = requestByToast(context, observable, observer, mView, handlerAction1);
    }

    @Override
    protected void doHandlerAction1() {     //取消预约请求超时后执行
        if(null != postCancelSchedule){
            postCancelSchedule.unsubscribe();
            mView.showTimeOutToast();
        }
        mView.hideLoadingDialog();
    }
}
