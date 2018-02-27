package com.svw.dealerapp.ui.mine.contract;

import android.content.Context;

import com.svw.dealerapp.entity.ResEntity;

import java.util.Map;

import rx.Observable;

/**
 * Created by qinshi on 6/5/2017.
 */

public class ScheduleWaitContract {

    public interface View{
        /**
         * 取消预约成功
         */
        void cancelScheduleSuccess(int position);

        /**
         * 取消预约失败
         */
        void cancelScheduleFailed();
    }

    public interface Model{
        Observable<ResEntity<Object>> cancelSchedule(Map<String, Object> options);
    }

    public interface Presenter{
        void cancelSchedule(Context context, Map<String, Object> options, int position);
    }
}
