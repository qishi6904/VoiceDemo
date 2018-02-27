package com.svw.dealerapp.ui.home.presenter;

import android.content.Context;
import android.util.Log;

import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.home.HomeEntity;
import com.svw.dealerapp.entity.home.HomeEntity.HomeInfoEntity;
import com.svw.dealerapp.entity.report.ReportHomeEntity;
import com.svw.dealerapp.mvpframe.listfragment.ListFragmentContract;
import com.svw.dealerapp.mvpframe.listfragment.ListFragmentPresenter;
import com.svw.dealerapp.mvpframe.listfragment.ToastObserver;
import com.svw.dealerapp.ui.home.contract.HomeContract;

import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by lijinkui on 2017/4/27.
 */
@Deprecated
public class HomePresenter extends ListFragmentPresenter<HomeEntity, HomeEntity.HomeInfoEntity>
        implements HomeContract.Presenter {

    private HomeContract.View fragmentView;
    private Subscription cancelScheduleSubscription;
    private Subscription getAutoPlayDataSubscription;

    public HomePresenter(ListFragmentContract.View<HomeEntity, HomeInfoEntity> view, ListFragmentContract.Model<ResEntity<HomeEntity>> model) {
        super(view, model);
        if(view instanceof HomeContract.View){
            fragmentView = (HomeContract.View) view;
        }
    }

    @Override
    public void loadMore(HomeEntity homeEntity) {
        List<HomeInfoEntity> appointmentList = homeEntity.getAppointmentList();
        if(null != appointmentList && appointmentList.size() > 0){
            this.getDataList().addAll(appointmentList);
            mView.loadMore();
        }else {
            mView.showLoadMoreNoData();
        }
    }

    @Override
    public void refresh(HomeEntity homeEntity) {
        fragmentView.setIconCountText(homeEntity.getPassengerCount(),
                homeEntity.getEcommernceCount(),
                homeEntity.getFollowupCount(),
                homeEntity.getUnapprovedCount());
        fragmentView.setDelayTip(homeEntity.getPassengerDelayedCount(),
                homeEntity.getEcommernceDelayedCount(),
                homeEntity.getFollowupDelayedCount());
        List<HomeInfoEntity> appointmentList = homeEntity.getAppointmentList();
        if(null != appointmentList && appointmentList.size() > 0){
            dataList.clear();
            dataList.addAll(appointmentList);
            mView.refresh();
        }else {
            switch (requestType){
                case REQUEST_BY_PULL_DOWN:
                    dealNoDataByPullDown();
                    break;
                case REQUEST_BY_INIT:
                    mView.showNoDataLayout();
                    dataList.clear();
                    mView.refresh();
                    break;
            }
        }
    }

    @Override
    public void cancelSchedule(Context context, Map<String, Object> options, final int position) {

        if(mModel instanceof HomeContract.Model) {
            HomeContract.Model homeModel = (HomeContract.Model) mModel;
            Observable observable = homeModel.cancelSchedule(options).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());

            ToastObserver observer= new ToastObserver<ResEntity<Object>>(mView, handler, handlerAction1) {
                @Override
                public void dealResult(ResEntity<Object> entity) {
                    if(null != entity && "200".equals(entity.getRetCode())) {
//                        dataList.remove(position);
                        dataList.get(position).setAppmStatusId("17540");
                        dataList.get(position).setShowEdit(false);
                        fragmentView.cancelScheduleSuccess(position);
                    }else {
                        fragmentView.cancelScheduleFailed();
                    }
                }
            };

            cancelScheduleSubscription = requestByToast(context, observable, observer, mView, handlerAction1);
        }
    }

    @Override
    public void getAutoPlayData(Context context, Map<String, Object> options) {
        if(mModel instanceof HomeContract.Model) {
            HomeContract.Model homeModel = (HomeContract.Model) mModel;
            Observable observable = homeModel.getAutoPlayData(options).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());

            ToastObserver observer= new ToastObserver<ResEntity<ReportHomeEntity>>(mView, handler, handlerAction2){
                @Override
                public void dealResult(ResEntity<ReportHomeEntity> entity) {
                    if(null != entity && "200".equals(entity.getRetCode())) {
                        if(null != entity && null != entity.getRetData() &&
                                null != entity.getRetData().getItemList() && entity.getRetData().getItemList().size() > 0){
                            fragmentView.getAutoPlayDataSuccess(entity.getRetData().getItemList());
                        }else {
                            fragmentView.getAutoPlayDataServerEmpty();
                        }
                    }else {
                        fragmentView.getAutoPlayDataServerError();
                    }
                }

                @Override
                public void onError(Throwable e) {
                    fragmentView.getAutoPlayDataServerError();
                }
            };

            getAutoPlayDataSubscription = requestByToast(context, observable, observer, mView, handlerAction2, false);
        }
    }

    @Override
    protected void doHandlerAction1() { //取消预约请求超时后执行
        if(null != cancelScheduleSubscription){
            cancelScheduleSubscription.unsubscribe();
            mView.showTimeOutToast();
        }
        mView.hideLoadingDialog();
    }

    @Override
    protected void doHandlerAction2() { //获取轮播数据超时后执行
        if(null != getAutoPlayDataSubscription){
            getAutoPlayDataSubscription.unsubscribe();
            fragmentView.getAutoPlayDataServerTimeout();
        }
    }

    /**
     * 提交更新成功后更新本地的数据
     * @param position
     * @param dateTime
     * @param scheduleCode
     */
    public void updateSchedule(int position, String dateTime, String scheduleCode){
        dataList.get(position).setAppmDateStr(dateTime);
        dataList.get(position).setAppmTypeId(scheduleCode);
        dataList.get(position).setShowEdit(false);
    }
}
