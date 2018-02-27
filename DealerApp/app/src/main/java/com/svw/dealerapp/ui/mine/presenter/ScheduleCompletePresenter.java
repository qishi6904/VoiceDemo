package com.svw.dealerapp.ui.mine.presenter;

import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.mine.ScheduleCompleteEntity;
import com.svw.dealerapp.entity.mine.ScheduleCompleteEntity.ScheduleCompleteInfoEntity;
import com.svw.dealerapp.entity.mine.ScheduleWaitEntity;
import com.svw.dealerapp.mvpframe.listfragment.ListFragmentContract;
import com.svw.dealerapp.mvpframe.listfragment.ListFragmentPresenter;

import java.util.List;

/**
 * Created by qinshi on 6/5/2017.
 */

public class ScheduleCompletePresenter extends ListFragmentPresenter<ScheduleCompleteEntity, ScheduleCompleteInfoEntity> {

    public ScheduleCompletePresenter(ListFragmentContract.View<ScheduleCompleteEntity, ScheduleCompleteInfoEntity> view,
                                     ListFragmentContract.Model<ResEntity<ScheduleCompleteEntity>> model) {
        super(view, model);
    }

    @Override
    public void loadMore(ScheduleCompleteEntity scheduleCompleteEntity) {
        List<ScheduleCompleteInfoEntity> appointmentList = scheduleCompleteEntity.getAppointmentList();
        if(null != appointmentList && appointmentList.size() > 0){
            this.getDataList().addAll(appointmentList);
            mView.loadMore();
        }else {
            mView.showLoadMoreNoData();
        }
    }

    @Override
    public void refresh(ScheduleCompleteEntity scheduleCompleteEntity) {
        List<ScheduleCompleteInfoEntity> appointmentList = scheduleCompleteEntity.getAppointmentList();
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
}
