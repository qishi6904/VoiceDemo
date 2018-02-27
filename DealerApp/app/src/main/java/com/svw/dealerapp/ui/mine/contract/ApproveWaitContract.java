package com.svw.dealerapp.ui.mine.contract;

import android.content.Context;

import com.svw.dealerapp.entity.ResEntity;

import java.util.Map;

import rx.Observable;

/**
 * Created by qinshi on 6/6/2017.
 */

public class ApproveWaitContract {

    public interface View{

        /**
         * 审批成功
         */
        void approveSuccess(int position);

        /**
         * 审批失败
         */
        void approveFail();
    }

    public interface Model{
        /**
         * 提交审批
         * @param options
         * @return
         */
        Observable<ResEntity<Object>> postApprove(Map<String, Object> options);
    }

    public interface Presenter{
        /**
         * 提交审批
         * @param context
         * @param options
         */
        void postApprove(Context context, Map<String, Object> options, int position);
    }
}
