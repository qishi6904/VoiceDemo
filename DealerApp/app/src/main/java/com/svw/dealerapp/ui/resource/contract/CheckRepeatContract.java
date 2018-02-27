package com.svw.dealerapp.ui.resource.contract;

import android.content.Context;

import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.home.CheckRepeatEntity;
import com.svw.dealerapp.mvpframe.listfragment.ShowToastView;

import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * Created by lijinkui on 2017/11/11.
 */

public class CheckRepeatContract {
    public interface View extends ShowToastView {

        void checkRepeatSuccess(int repeatNum);

        void checkRepeatFailed();

        void showCheckRepeatTimeout();

    }


    public interface Model {

        Observable<ResEntity<List<CheckRepeatEntity>>> checkRepeat(Map<String, Object> options);

    }

    public interface Presenter {

        void checkRepeat(Context context, Map<String, Object> options);
    }
}
