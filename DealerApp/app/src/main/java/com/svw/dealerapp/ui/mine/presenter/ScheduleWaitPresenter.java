package com.svw.dealerapp.ui.mine.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.home.HomeEntity;
import com.svw.dealerapp.entity.mine.ScheduleWaitEntity;
import com.svw.dealerapp.entity.mine.ScheduleWaitEntity.ScheduleWaitInfoEntity;
import com.svw.dealerapp.mvpframe.listfragment.ListFragmentContract;
import com.svw.dealerapp.mvpframe.listfragment.ListFragmentPresenter;
import com.svw.dealerapp.mvpframe.listfragment.ToastObserver;
import com.svw.dealerapp.ui.mine.contract.ScheduleWaitContract;
import com.svw.dealerapp.util.NetworkUtils;

import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by qinshi on 6/5/2017.
 */

public class ScheduleWaitPresenter extends ListFragmentPresenter<ScheduleWaitEntity, ScheduleWaitInfoEntity>
        implements ScheduleWaitContract.Presenter {


    private Subscription postCancelSchedule;
    private ScheduleWaitContract.View fragmentView;

    public ScheduleWaitPresenter(ListFragmentContract.View<ScheduleWaitEntity, ScheduleWaitInfoEntity> view, ListFragmentContract.Model<ResEntity<ScheduleWaitEntity>> model) {
        super(view, model);
        if(view instanceof ScheduleWaitContract.View){
            fragmentView = (ScheduleWaitContract.View) view;
        }
    }

    @Override
    public void loadMore(ScheduleWaitEntity scheduleWaitEntity) {
        List<ScheduleWaitInfoEntity> appointmentList = scheduleWaitEntity.getAppointmentList();
        if(null != appointmentList && appointmentList.size() > 0){
            this.getDataList().addAll(appointmentList);
            mView.loadMore();
        }else {
            mView.showLoadMoreNoData();
        }
    }

    @Override
    public void refresh(ScheduleWaitEntity scheduleWaitEntity) {
        List<ScheduleWaitInfoEntity> appointmentList = scheduleWaitEntity.getAppointmentList();
        if(null != appointmentList && appointmentList.size() > 0){
            this.getDataList().clear();
            this.getDataList().addAll(appointmentList);
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
    public void cancelSchedule(Context context, Map<String, Object> options, final int position) {
        if(mModel instanceof ScheduleWaitContract.Model){
            ScheduleWaitContract.Model scheduleWaitModel = (ScheduleWaitContract.Model) mModel;
            Observable observable = scheduleWaitModel.cancelSchedule(options)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());

            ToastObserver observer = new ToastObserver<ResEntity<Object>>(mView, handler, handlerAction1){
                @Override
                public void dealResult(ResEntity<Object> entity) {
                    if(null != entity && !TextUtils.isEmpty(entity.getRetCode()) && "200".equals(entity.getRetCode())){
                        dataList.remove(position);
                        fragmentView.cancelScheduleSuccess(position);
                    }else {
                        fragmentView.cancelScheduleFailed();
                    }
                }
            };

            postCancelSchedule = requestByToast(context, observable, observer, mView, handlerAction1);
        }
    }

    @Override
    protected void doHandlerAction1() {     //取消预约请求超时后执行
        if(null != postCancelSchedule){
            postCancelSchedule.unsubscribe();
            mView.showTimeOutToast();
        }
        mView.hideLoadingDialog();
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
