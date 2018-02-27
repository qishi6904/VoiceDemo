package com.svw.dealerapp.ui.resource.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.resource.TrafficEntity;
import com.svw.dealerapp.entity.resource.TrafficEntity.TrafficInfoEntity;
import com.svw.dealerapp.entity.resource.YellowCardEntity;
import com.svw.dealerapp.mvpframe.listfragment.ListFragmentContract;
import com.svw.dealerapp.mvpframe.listfragment.ListFragmentPresenter;
import com.svw.dealerapp.mvpframe.listfragment.ToastObserver;
import com.svw.dealerapp.ui.resource.contract.TrafficContract;

import java.util.List;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by qinshi on 5/11/2017.
 */
@Deprecated
public class TrafficFragmentPresenter extends ListFragmentPresenter<TrafficEntity, TrafficInfoEntity>
        implements TrafficContract.Presenter {

    private Subscription postInvalidSubscription;
    private TrafficContract.View fragmentView;

    public TrafficFragmentPresenter(ListFragmentContract.View<TrafficEntity, TrafficInfoEntity> view, ListFragmentContract.Model<ResEntity<TrafficEntity>> model) {
        super(view, model);
        if(view instanceof TrafficContract.View){
            fragmentView = (TrafficContract.View) view;
        }
    }

    @Override
    public void loadMore(TrafficEntity t) {
        List<TrafficInfoEntity> trafficInfoList = t.getData();
        if(null != trafficInfoList && trafficInfoList.size() > 0){
            this.getDataList().addAll(trafficInfoList);
            mView.loadMore();
        }else {
            mView.showLoadMoreNoData();
        }
    }

    @Override
    public void refresh(TrafficEntity t) {
        List<TrafficInfoEntity> trafficInfoList = t.getData();
        if(null != trafficInfoList && trafficInfoList.size() > 0){
            this.getDataList().clear();
            this.getDataList().addAll(trafficInfoList);
            mView.refresh();
        }else {
            switch (requestType){
                case REQUEST_BY_PULL_DOWN:
                    dealNoDataByPullDown();
                    break;
                case REQUEST_BY_INIT:
                    mView.showNoDataLayout();
                    break;
            }
        }
    }

    @Override
    public void postInvalidTrafficStatus(Context context, String trafficId, String status, String reason, final int dealInvalidPosition){

        if(mModel instanceof TrafficContract.Model) {
            TrafficContract.Model trafficModel = (TrafficContract.Model) mModel;
            Observable observable = trafficModel.postInvalidTrafficStatus(trafficId, status, reason)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());

            ToastObserver observer = new ToastObserver<ResEntity<Object>>(mView, handler, handlerAction1) {

                @Override
                public void dealResult(ResEntity<Object> entity) {
                    if(null != entity && !TextUtils.isEmpty(entity.getRetCode()) && "200".equals(entity.getRetCode())){
                        dataList.get(dealInvalidPosition).setLeadsStatus("InValid");
                        fragmentView.changeToInvalidStatus(dealInvalidPosition);
                        fragmentView.showChangeToInvalidSuccessToast();
                    }else {
                        fragmentView.showChangeToInvalidFailToast();
                    }
                }
            };
            postInvalidSubscription = requestByToast(context, observable, observer, mView, handlerAction1);
        }
    }

    @Override
    protected void doHandlerAction1() { //无法建卡请求超时后执行
        if(null != postInvalidSubscription){
            postInvalidSubscription.unsubscribe();
            mView.showTimeOutToast();
        }
        mView.hideLoadingDialog();
    }
}
