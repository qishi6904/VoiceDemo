package com.svw.dealerapp.ui.mine.contract;

import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.mine.ScheduleCompleteEntity;
import com.svw.dealerapp.entity.mine.ScheduleCreateResEntity;
import com.svw.dealerapp.entity.mine.ScheduleWaitEntity;

import java.util.Map;

import rx.Observable;

/**
 * Created by lijinkui on 2017/6/3.
 */

public class ScheduleContract {
    public interface View{

    }

    public interface Model{

        Observable<ResEntity<ScheduleCreateResEntity>> scheduleCreate(Map<String, Object> options);

    }


    public interface Presenter{

    }
}
