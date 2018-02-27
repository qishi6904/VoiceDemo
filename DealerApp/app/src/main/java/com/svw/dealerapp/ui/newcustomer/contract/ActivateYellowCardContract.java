package com.svw.dealerapp.ui.newcustomer.contract;

import android.content.Context;

import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.newcustomer.FollowupCreateReqEntity;
import com.svw.dealerapp.mvpframe.listfragment.ShowToastView;

import java.util.Map;

import rx.Observable;

/**
 * Created by lijinkui on 2017/12/2.
 */

public class ActivateYellowCardContract {
    public interface View extends ShowToastView {
        void activateYellowCardSuccess(String msg);

        void activateYellowCardFail(String msg);

        void activateYellowCardTimeout();
    }

    public interface Presenter {
        void activateYellowCard(Context context, FollowupCreateReqEntity options);
    }

    public interface Model {
        Observable<ResEntity<Object>> activateYellowCard(FollowupCreateReqEntity options);
    }
}
