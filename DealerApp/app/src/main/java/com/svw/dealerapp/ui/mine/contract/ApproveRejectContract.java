package com.svw.dealerapp.ui.mine.contract;

import android.content.Context;

import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.mvpframe.listfragment.ShowToastView;

import java.util.Map;

import rx.Observable;

/**
 * Created by qinshi on 6/7/2017.
 */

public class ApproveRejectContract {

    public interface View extends ShowToastView{
        /**
         * 拒绝审批成功
         */
        void rejectApproveSuccess();
    }

    public interface Model{
        /**
         * 提交拒绝审批
         * @param options
         * @return
         */
        Observable<ResEntity<Object>> rejectApprove(Map<String, Object> options);
    }

    public interface Presenter{
        /**
         * 提交拒绝审批
         * @param context
         * @param options
         */
        void rejectApprove(Context context, Map<String, Object> options);
    }
}
