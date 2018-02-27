package com.svw.dealerapp.ui.home.contract;

import android.content.Context;

import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.home.ActivityEntity;
import com.svw.dealerapp.entity.home.CheckRepeatEntity;
import com.svw.dealerapp.mvpframe.listfragment.ShowToastView;

import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * Created by lijinkui on 2017/6/5.
 */

public class CreateTrafficContract {
    public interface View extends ShowToastView{
        /**
         * 创建客流成功
         */
        void createTrafficSuccess();
        void getActivityListSuccess(List<ActivityEntity.ActivityInfoBean> result);
        void getActivityListFail(String message);
        void checkRepeatSuccess(int repeatNum);
        void checkRepeatFailed();
        void showGetActivityListTimeout();
        void showCheckRepeatTimeout();

    }


    public interface Model{

        Observable<ResEntity<Object>> createTraffic(Map<String,Object>options);
        Observable<ResEntity<ActivityEntity>> getActivityList(Map<String,Object> options);
        Observable<ResEntity<List<CheckRepeatEntity>>> checkRepeat(Map<String, Object> options);

    }

    public interface Presenter{
        void createTraffic(Context context, Map<String,Object>options);
        void getActivityList(Context context);
        void checkRepeat(Context context, Map<String, Object> options);
    }
}
