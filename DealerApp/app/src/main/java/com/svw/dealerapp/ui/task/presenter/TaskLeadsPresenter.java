package com.svw.dealerapp.ui.task.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.task.TaskLeadsEntity;
import com.svw.dealerapp.mvpframe.listfragment.ListFragmentContract;
import com.svw.dealerapp.mvpframe.listfragment.ListFragmentPresenter;
import com.svw.dealerapp.mvpframe.listfragment.ToastObserver;
import com.svw.dealerapp.ui.task.contract.TaskLeadsContract;

import java.util.List;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by qinshi on 5/25/2017.
 */

public class TaskLeadsPresenter extends ListFragmentPresenter<TaskLeadsEntity, TaskLeadsEntity.TaskLeadsInfoEntity>
        implements TaskLeadsContract.Presenter {

    private Subscription postInvalidSubscription;
    private TaskLeadsContract.View fragmentView;

    public TaskLeadsPresenter(ListFragmentContract.View<TaskLeadsEntity, TaskLeadsEntity.TaskLeadsInfoEntity> view, ListFragmentContract.Model<ResEntity<TaskLeadsEntity>> model) {
        super(view, model);
        if(view instanceof TaskLeadsContract.View){
            fragmentView = (TaskLeadsContract.View) view;
        }
    }

    @Override
    public void loadMore(TaskLeadsEntity t) {
        List<TaskLeadsEntity.TaskLeadsInfoEntity> leadsInfoList = t.getData();
        if(null != leadsInfoList && leadsInfoList.size() > 0){
            this.getDataList().addAll(leadsInfoList);
            mView.loadMore();
        }else {
            mView.showLoadMoreNoData();
        }
    }

    @Override
    public void refresh(TaskLeadsEntity t) {
        List<TaskLeadsEntity.TaskLeadsInfoEntity> leadsInfoList = t.getData();
        if(null != leadsInfoList && leadsInfoList.size() > 0){
            this.getDataList().clear();
            this.getDataList().addAll(leadsInfoList);
            mView.refresh();
            fragmentView.setTabTipNumber(String.valueOf(t.getPage().getTotalCount()));
        }else {
            switch (requestType){
                case REQUEST_BY_PULL_DOWN:
                    dealNoDataByPullDown();
                    break;
                case REQUEST_BY_INIT:
                    mView.showNoDataLayout();
                    break;
            }
            fragmentView.setTabTipNumber("0");
        }
    }

    /**
     * 下拉无数据处理
     */
    protected void dealNoDataByPullDown(){
        this.getDataList().clear();
        mView.refresh();
        mView.showNoDataLayout();
        mView.showNoDataToast();
    }

    @Override
    public void postInvalidTrafficStatus(Context context, String trafficId, String status, String reason, final int dealInvalidPosition){
        if(mModel instanceof TaskLeadsContract.Model) {
            TaskLeadsContract.Model model = (TaskLeadsContract.Model) mModel;
            Observable observable = model.postInvalidTrafficStatus(trafficId, status, reason)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());

            ToastObserver observer = new ToastObserver<ResEntity<Object>>(mView, handler, handlerAction1){

                @Override
                public void dealResult(ResEntity<Object> entity) {
                    if(null != entity && !TextUtils.isEmpty(entity.getRetCode()) && "200".equals(entity.getRetCode())){
//                        dataList.get(dealInvalidPosition).setLeadsStatusId("10530");
                        dataList.remove(dealInvalidPosition);
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
