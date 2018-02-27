package com.svw.dealerapp.ui.resource.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.newcustomer.CarTypesEntity;
import com.svw.dealerapp.mvpframe.listfragment.BaseHandlerPresenter;
import com.svw.dealerapp.mvpframe.listfragment.ToastObserver;
import com.svw.dealerapp.ui.resource.contract.FilterGetCarNameContractor;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by qinshi on 12/19/2017.
 */

public class FilterGetCarNamePresenter extends BaseHandlerPresenter<FilterGetCarNameContractor.Model, FilterGetCarNameContractor.View>
        implements FilterGetCarNameContractor.Presenter {

    private Subscription getCarTypeSubscription;

    public FilterGetCarNamePresenter(FilterGetCarNameContractor.View view, FilterGetCarNameContractor.Model model) {
        super(view, model);
    }

    @Override
    public void getCarTypeByCode(Context context, String code) {
        Observable observable = mModel.getCarTypeByCode(code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        ToastObserver observer = new ToastObserver<ResEntity<CarTypesEntity>>(mView, handler, handlerAction1) {
            @Override
            public void dealResult(ResEntity<CarTypesEntity> entity) {
                if(null != entity && !TextUtils.isEmpty(entity.getRetCode()) &&
                        "200".equals(entity.getRetCode())){              //匹配到车型
                    mView.refreshView(entity.getRetData());
                    mView.showMatchCarSuccess();
                }else if(null != entity && !TextUtils.isEmpty(entity.getRetCode()) &&
                        "011004".equals(entity.getRetCode())){          //没有匹配到车型
                    mView.showNoDataToast();
                }else {
                    mView.showMatchCarFailed();
                }
            }
        };

        getCarTypeSubscription = requestByToast(context, observable, observer, mView, handlerAction1);
    }

    @Override
    protected void doHandlerAction1() {     //获取车型请求超时后执行
        if(null != getCarTypeSubscription){
            getCarTypeSubscription.unsubscribe();
            mView.showTimeOutToast();
        }
        mView.hideLoadingDialog();
    }
}

