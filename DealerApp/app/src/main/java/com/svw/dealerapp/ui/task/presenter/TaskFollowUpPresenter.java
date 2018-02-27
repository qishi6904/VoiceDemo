package com.svw.dealerapp.ui.task.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.task.TaskFollowUpEntity;
import com.svw.dealerapp.entity.task.TaskFollowUpEntity.TaskFollowInfoEntity;
import com.svw.dealerapp.mvpframe.listfragment.ListFragmentContract;
import com.svw.dealerapp.mvpframe.listfragment.ListFragmentPresenter;
import com.svw.dealerapp.mvpframe.listfragment.ToastObserver;
import com.svw.dealerapp.ui.task.contract.TaskFollowUpContract;

import java.util.List;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by qinshi on 5/15/2017.
 */

public class TaskFollowUpPresenter extends ListFragmentPresenter<TaskFollowUpEntity, TaskFollowInfoEntity>
        implements TaskFollowUpContract.Presenter {

    private TaskFollowUpContract.View fragmentView;
    private Subscription postVipCustomerSubscription;

    public TaskFollowUpPresenter(ListFragmentContract.View<TaskFollowUpEntity, TaskFollowInfoEntity> view, ListFragmentContract.Model<ResEntity<TaskFollowUpEntity>> model) {
        super(view, model);
        if(view instanceof TaskFollowUpContract.View){
            fragmentView = (TaskFollowUpContract.View) view;
        }
    }

    @Override
    public void loadMore(TaskFollowUpEntity taskFollowUpEntity) {
        List<TaskFollowInfoEntity> followUpEntityList = taskFollowUpEntity.getData();
        if(null != followUpEntityList && followUpEntityList.size() > 0){
            this.getDataList().addAll(followUpEntityList);
            mView.loadMore();
        }else {
            mView.showLoadMoreNoData();
        }
    }

    @Override
    public void refresh(TaskFollowUpEntity taskFollowUpEntity) {
        List<TaskFollowInfoEntity> followUpEntityList = taskFollowUpEntity.getData();
        if(null != followUpEntityList && followUpEntityList.size() > 0){
            this.getDataList().clear();
            this.getDataList().addAll(followUpEntityList);
            mView.refresh();
            fragmentView.setTabTipNumber(String.valueOf(taskFollowUpEntity.getPage().getTotalCount()));
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

    @Override
    public void postVipCustomer(Context context, String oppId, final String isKeyuser, final int dealPosition) {
        if(mModel instanceof TaskFollowUpContract.Model) {
            TaskFollowUpContract.Model followUpModel = (TaskFollowUpContract.Model) mModel;
            Observable observable = followUpModel.postVipCustomer(oppId, isKeyuser)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());

            ToastObserver observer = new ToastObserver<ResEntity<Object>>(mView, handler, handlerAction1){
                @Override
                public void dealResult(ResEntity<Object> entity) {
                    if(null != entity && !TextUtils.isEmpty(entity.getRetCode()) && "200".equals(entity.getRetCode())){
                        dataList.get(dealPosition).setIsKeyuser(isKeyuser);
                        fragmentView.showSetVipSuccessToast(isKeyuser);
                        fragmentView.notifyListView(dealPosition);
                    }else {
                        fragmentView.showSetVipFailedToast(isKeyuser);
                    }
                }
            };

            postVipCustomerSubscription = requestByToast(context, observable, observer, mView, handlerAction1);

        }
    }

    @Override
    protected void doHandlerAction1() {  //设置重点客户请求超时后执行
        if(null != postVipCustomerSubscription){
            postVipCustomerSubscription.unsubscribe();
            mView.showTimeOutToast();
        }
    }
}
