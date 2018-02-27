package com.svw.dealerapp.ui.home.presenter;

import android.content.Context;

import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.home.ActivityEntity;
import com.svw.dealerapp.entity.home.CheckRepeatEntity;
import com.svw.dealerapp.mvpframe.listfragment.BaseHandlerPresenter;
import com.svw.dealerapp.mvpframe.listfragment.ToastObserver;
import com.svw.dealerapp.ui.home.contract.CreateTrafficContract;
import com.svw.dealerapp.util.ToastUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by lijinkui on 2017/6/5.
 */

public class CreateTrafficPresenter extends BaseHandlerPresenter<CreateTrafficContract.Model, CreateTrafficContract.View>
        implements CreateTrafficContract.Presenter{

    private Subscription createTrafficSubscription;
    private Subscription getActivityListSubscription;
    private Subscription checkRepeatSubscription;

    public CreateTrafficPresenter(CreateTrafficContract.View view, CreateTrafficContract.Model model){
        super(view, model);
    }

    @Override
    public void createTraffic(Context context, Map<String, Object> options) {
        Observable observable = mModel.createTraffic(options)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        ToastObserver observer = new ToastObserver<ResEntity<Object>>(mView, handler, handlerAction1) {
            @Override
            public void dealResult(ResEntity<Object> entity) {
                if(null != entity && "200".equals(entity.getRetCode())) {
                    mView.createTrafficSuccess();
                }else {
                    mView.showServerErrorToast();
                }
            }
        };

        createTrafficSubscription = requestByToast(context, observable, observer, mView, handlerAction1);
    }

    @Override
    public void getActivityList(Context context) {
        Map<String,Object> options = new HashMap<>();
        Observable observable = mModel.getActivityList(options)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        ToastObserver observer = new ToastObserver<ResEntity<ActivityEntity>>(mView, handler, handlerAction2) {
            @Override
            public void dealResult(ResEntity<ActivityEntity> entity) {
                if(null != entity && "200".equals(entity.getRetCode())) {
                    mView.getActivityListSuccess(entity.getRetData().getReturnList());
                }else {
                    mView.getActivityListFail(entity.getRetMessage());
                }
            }
        };

        getActivityListSubscription = requestByToast(context, observable, observer, mView, handlerAction2);
    }

    @Override
    public void checkRepeat(Context context, Map<String, Object> options) {
        Observable observable = mModel.checkRepeat(options)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        ToastObserver observer = new ToastObserver<ResEntity<List<CheckRepeatEntity>>>(mView, handler, handlerAction3) {
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

        checkRepeatSubscription = requestByToast(context, observable, observer, mView, handlerAction3);
    }

    @Override
    protected void doHandlerAction1() {  //创建客流请求超时后执行
        if(null != createTrafficSubscription){
            createTrafficSubscription.unsubscribe();
            mView.showTimeOutToast();
        }
        mView.hideLoadingDialog();
    }

    @Override
    protected void doHandlerAction2() {  //获取外拓请求超时后执行
        if(null != getActivityListSubscription){
            getActivityListSubscription.unsubscribe();
            mView.showGetActivityListTimeout();
        }
        mView.hideLoadingDialog();
    }

    @Override
    protected void doHandlerAction3() {  //查重请求超时后执行
        if(null != checkRepeatSubscription){
            checkRepeatSubscription.unsubscribe();
            mView.showCheckRepeatTimeout();
        }
        mView.hideLoadingDialog();
    }

}
