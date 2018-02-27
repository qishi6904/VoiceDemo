package com.svw.dealerapp.ui.order.contract;

import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.order.CancelOrderReqEntity;
import com.svw.dealerapp.mvpframe.BasePresenter;
import com.svw.dealerapp.mvpframe.listfragment.ShowToastView;
import com.svw.dealerapp.ui.resource.entity.ResourceFilterItemEntity;

import java.util.List;

import rx.Observable;

/**
 * Created by lijinkui on 2017/9/28.
 */

public interface CancelOrderFollowupContract {

    interface Model {
        Observable<ResEntity<Object>> cancelOrderFollowup(CancelOrderReqEntity entity);
    }

    interface View extends ShowToastView {
        void cancelOrderSuccess(int dealListPosition, String orderStatus);

        void cancelOrderFailure(String msg);
    }

    interface Presenter{
        void cancelOrderFollowup(CancelOrderReqEntity entity, int dealListPosition);
        ResourceFilterItemEntity getContactWayMap();
        public List<ResourceFilterItemEntity> getFollowUpList(String contactId, String oppStatusId);
    }
}
