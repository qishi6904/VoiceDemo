package com.svw.dealerapp.ui.mine.contract;

import android.content.Context;

import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.mvpframe.listfragment.ShowToastView;

import java.util.Map;

import rx.Observable;

/**
 * Created by qinshi on 6/5/2017.
 */

public class ScheduleUpdateContract {

    public interface View extends ShowToastView{
        /**
         * 更新预约成功
         */
        void updateScheduleSuccess(int dealPosition);
    }

    public interface Model{
        Observable<ResEntity<Object>> updateSchedule(Map<String, Object> options);
    }

    public interface Presenter{
        void updateSchedule(Context context, Map<String, Object> options, int dealPosition);
    }

}
