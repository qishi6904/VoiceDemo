package com.svw.dealerapp.ui.mine.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.mine.ApproveWaitEntity;
import com.svw.dealerapp.entity.mine.ApproveWaitEntity.ApproveWaitInfoEntity;
import com.svw.dealerapp.entity.mine.ScheduleWaitEntity;
import com.svw.dealerapp.mvpframe.listfragment.ListFragmentContract;
import com.svw.dealerapp.mvpframe.listfragment.ListFragmentPresenter;
import com.svw.dealerapp.mvpframe.listfragment.ToastObserver;
import com.svw.dealerapp.ui.mine.contract.ApproveWaitContract;

import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by qinshi on 6/6/2017.
 */

public class ApproveWaitPresenter extends ListFragmentPresenter<ApproveWaitEntity, ApproveWaitInfoEntity>
        implements ApproveWaitContract.Presenter {

    private Subscription postApproveSubscription;
    private ApproveWaitContract.View fragmentView;

    public ApproveWaitPresenter(ListFragmentContract.View<ApproveWaitEntity, ApproveWaitInfoEntity> view, ListFragmentContract.Model<ResEntity<ApproveWaitEntity>> model) {
        super(view, model);
        if(view instanceof ApproveWaitContract.View){
            fragmentView = (ApproveWaitContract.View) view;
        }
    }

    @Override
    public void loadMore(ApproveWaitEntity approveWaitEntity) {
        List<ApproveWaitInfoEntity> approveList = approveWaitEntity.getApprovals();
        if(null != approveList && approveList.size() > 0){
            this.getDataList().addAll(approveList);
            mView.loadMore();
        }else {
            mView.showLoadMoreNoData();
        }
    }

    @Override
    public void refresh(ApproveWaitEntity approveWaitEntity) {
        List<ApproveWaitInfoEntity> approveList = approveWaitEntity.getApprovals();
        if(null != approveList && approveList.size() > 0){
            this.getDataList().clear();
            this.getDataList().addAll(approveList);
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
    public void postApprove(Context context, Map<String, Object> options, final int position) {
        if(mModel instanceof ApproveWaitContract.Model){
            ApproveWaitContract.Model approveModel = (ApproveWaitContract.Model) mModel;
            Observable observable = approveModel.postApprove(options)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());

            ToastObserver observer = new ToastObserver<ResEntity<Object>>(mView, handler, handlerAction1){
                @Override
                public void dealResult(ResEntity<Object> entity) {
                    if(null != entity && !TextUtils.isEmpty(entity.getRetCode()) && "200".equals(entity.getRetCode())){
                        dataList.remove(position);
                        fragmentView.approveSuccess(position);
                    }else {
                        fragmentView.approveFail();
                    }
                }
            };

            postApproveSubscription = requestByToast(context, observable, observer, mView, handlerAction1);
        }
    }

    @Override
    protected void doHandlerAction1() {       //批准审批请求超时后执行
        if(null != postApproveSubscription){
            postApproveSubscription.unsubscribe();
            mView.showTimeOutToast();
        }
        mView.hideLoadingDialog();
    }
}
