package com.svw.dealerapp.ui.order.contract;

import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.order.CheckMembershipReqEntity;
import com.svw.dealerapp.entity.order.CheckMembershipResEntity;
import com.svw.dealerapp.mvpframe.BasePresenter;
import com.svw.dealerapp.mvpframe.BaseView;

import rx.Observable;

/**
 * Created by xupan on 19/09/2017.
 */

public interface MembershipContract {

    interface Model {
        Observable<ResEntity<CheckMembershipResEntity>> checkMembership(CheckMembershipReqEntity option);
    }

    interface View extends BaseView {
        void onCheckMembershipSuccess(CheckMembershipResEntity entity);

        void onCheckMembershipFailure(String msg);
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        public abstract void checkMembership(CheckMembershipReqEntity option);
    }
}
