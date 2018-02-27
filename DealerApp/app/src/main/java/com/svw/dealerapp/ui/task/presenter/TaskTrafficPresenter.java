package com.svw.dealerapp.ui.task.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.task.TaskTrafficEntity;
import com.svw.dealerapp.mvpframe.listfragment.ListFragmentContract;
import com.svw.dealerapp.mvpframe.listfragment.ListFragmentPresenter;
import com.svw.dealerapp.mvpframe.listfragment.ToastObserver;
import com.svw.dealerapp.ui.task.contract.TaskTrafficContract;

import java.util.List;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by qinshi on 5/25/2017.
 */

@Deprecated
public class TaskTrafficPresenter extends ListFragmentPresenter<TaskTrafficEntity, TaskTrafficEntity.TaskTrafficInfoEntity>
        implements TaskTrafficContract.Presenter {

    private Subscription postInvalidSubscription;
    private TaskTrafficContract.View fragmentView;

    public TaskTrafficPresenter(ListFragmentContract.View<TaskTrafficEntity, TaskTrafficEntity.TaskTrafficInfoEntity> view, ListFragmentContract.Model<ResEntity<TaskTrafficEntity>> model) {
        super(view, model);
        if(view instanceof TaskTrafficContract.View){
            fragmentView = (TaskTrafficContract.View) view;
        }
    }

    @Override
    public void loadMore(TaskTrafficEntity t) {
        List<TaskTrafficEntity.TaskTrafficInfoEntity> trafficInfoList = t.getData();
        if(null != trafficInfoList && trafficInfoList.size() > 0){
            this.getDataList().addAll(trafficInfoList);
            mView.loadMore();
        }else {
            mView.showLoadMoreNoData();
        }
    }

    @Override
    public void refresh(TaskTrafficEntity t) {
        List<TaskTrafficEntity.TaskTrafficInfoEntity> trafficInfoList = t.getData();
        if(null != trafficInfoList && trafficInfoList.size() > 0){
            this.getDataList().clear();
            this.getDataList().addAll(trafficInfoList);
            mView.refresh();
            fragmentView.setTabTipNumber(String.valueOf(t.getPage().getTotalCount()));
        }else {
            switch (requestType){
                case REQUEST_BY_PULL_DOWN:
                    mView.showNoDataToast();
                    break;
                case REQUEST_BY_INIT:
                    mView.showNoDataLayout();
                    break;
            }
            fragmentView.setTabTipNumber("0");
        }
    }

    @Override
    public void postInvalidTrafficStatus(Context context, String trafficId, String status, String reason, final int dealInvalidPosition){
        if(mModel instanceof TaskTrafficContract.Model) {
            TaskTrafficContract.Model trafficModel = (TaskTrafficContract.Model) mModel;
            Observable observable = trafficModel.postInvalidTrafficStatus(trafficId, status, reason)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());

            ToastObserver observer = new ToastObserver<ResEntity<Object>>(mView, handler, handlerAction1){

                @Override
                public void dealResult(ResEntity<Object> entity) {
                    if(null != entity && !TextUtils.isEmpty(entity.getRetCode()) && "200".equals(entity.getRetCode())){
                        dataList.get(dealInvalidPosition).setLeadsStatusId("10530");
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
    protected void doHandlerAction1() {     //无法建卡请求超时后执行
        if(null != postInvalidSubscription){
            postInvalidSubscription.unsubscribe();
            mView.showTimeOutToast();
        }
        mView.hideLoadingDialog();
    }
}
