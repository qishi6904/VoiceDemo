package com.svw.dealerapp.ui.home.contract;

import android.content.Context;

import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.report.ReportHomeEntity;
import com.svw.dealerapp.ui.home.entity.AutoPlayEntity;

import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * Created by lijinkui on 2017/4/27.
 */

public class HomeContract {

    public interface View{
        /**
         * 取消预约成功
         */
        void cancelScheduleSuccess(int position);

        /**
         * 取消预约失败
         */
        void cancelScheduleFailed();

        /**
         * 设置首页三个icon下面的数字
         * @param trafficCount
         * @param eCommerceCount
         * @param followUpCount
         */
        void setIconCountText(String trafficCount, String eCommerceCount, String followUpCount, String unapprovedCount);

        /**
         * 设置逾期的提示
         * @param trafficDelayNum
         * @param eCommerceDelayNum
         * @param followDelayNum
         */
        void setDelayTip(String trafficDelayNum, String eCommerceDelayNum, String followDelayNum);

        void getAutoPlayDataSuccess(List<ReportHomeEntity.ReportHomeItemEntity> data);

        void getAutoPlayDataServerError();

        void getAutoPlayDataServerTimeout();

        void getAutoPlayDataServerEmpty();
    }

    public interface Model{
        Observable<ResEntity<Object>> cancelSchedule(Map<String, Object> options);
        Observable<ResEntity<ReportHomeEntity>> getAutoPlayData(Map<String, Object> options);
    }

    public interface Presenter{
        void cancelSchedule(Context context, Map<String, Object> options, int position);
        void getAutoPlayData(Context context, Map<String, Object> options);
    }
}
