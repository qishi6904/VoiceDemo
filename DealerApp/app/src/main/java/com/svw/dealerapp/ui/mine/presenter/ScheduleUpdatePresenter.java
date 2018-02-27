package com.svw.dealerapp.ui.mine.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.mvpframe.listfragment.BaseHandlerPresenter;
import com.svw.dealerapp.mvpframe.listfragment.ToastObserver;
import com.svw.dealerapp.ui.mine.contract.ScheduleUpdateContract;
import com.svw.dealerapp.util.NetworkUtils;

import java.util.Map;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by qinshi on 6/5/2017.
 */

public class ScheduleUpdatePresenter extends BaseHandlerPresenter<ScheduleUpdateContract.Model, ScheduleUpdateContract.View>
        implements ScheduleUpdateContract.Presenter {

    private Subscription postUpdateSchedule;

    public ScheduleUpdatePresenter(ScheduleUpdateContract.View view, ScheduleUpdateContract.Model model){
        super(view, model);
    }

    @Override
    public void updateSchedule(Context context, Map<String, Object> options, final int dealPosition) {
        Observable observable = mModel.updateSchedule(options)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        ToastObserver observer =  new ToastObserver<ResEntity<Object>>(mView, handler, handlerAction1) {

            @Override
            public void dealResult(ResEntity<Object> entity) {
                if(null != entity && !TextUtils.isEmpty(entity.getRetCode()) && "200".equals(entity.getRetCode())){
                    mView.updateScheduleSuccess(dealPosition);
                }else {
                    mView.showServerErrorToast();
                }
            }
        };

        postUpdateSchedule = requestByToast(context, observable, observer, mView, handlerAction1);
    }

    @Override
    protected void doHandlerAction1() {    //更新预约请求超时后执行
        if(null != postUpdateSchedule){
            postUpdateSchedule.unsubscribe();
            mView.showTimeOutToast();
        }
        mView.hideLoadingDialog();
    }
}
