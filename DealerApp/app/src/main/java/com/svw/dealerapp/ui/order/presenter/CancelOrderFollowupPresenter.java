package com.svw.dealerapp.ui.order.presenter;

import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.order.CancelOrderReqEntity;
import com.svw.dealerapp.global.NewCustomerConstants;
import com.svw.dealerapp.mvpframe.listfragment.BaseHandlerPresenter;
import com.svw.dealerapp.mvpframe.listfragment.ToastObserver;
import com.svw.dealerapp.ui.order.activity.OrderCancelActivity2;
import com.svw.dealerapp.ui.order.contract.CancelOrderFollowupContract;
import com.svw.dealerapp.ui.resource.entity.ResourceFilterItemEntity;
import com.svw.dealerapp.util.dbtools.DBUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by lijinkui on 2017/9/28.
 */

public class CancelOrderFollowupPresenter extends BaseHandlerPresenter<CancelOrderFollowupContract.Model, CancelOrderFollowupContract.View>
    implements CancelOrderFollowupContract.Presenter{

    private Subscription cancelOderSubscription;

    public CancelOrderFollowupPresenter(CancelOrderFollowupContract.View view, CancelOrderFollowupContract.Model model) {
        super(view, model);
    }


    @Override
    public void cancelOrderFollowup(CancelOrderReqEntity entity, final int dealListPosition) {

        Observable observable = mModel.cancelOrderFollowup(entity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        ToastObserver observer = new ToastObserver<ResEntity<Object>>(mView, handler, handlerAction1) {
            @Override
            public void dealResult(ResEntity<Object> entity) {
                if(null != entity && "200".equals(entity.getRetCode())) {
                    String orderStatus = null;
                    try {
                        JSONObject jsonObject = new JSONObject(entity.getRetData().toString());
                        if (jsonObject.has("orderStatus")) {
                            orderStatus = jsonObject.getString("orderStatus");
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    mView.cancelOrderSuccess(dealListPosition, orderStatus);
                }else {
                    mView.cancelOrderFailure(entity.getRetMessage());
                }
            }
        };

        cancelOderSubscription = requestByToast(context, observable, observer, mView, handlerAction1);
    }

    @Override
    protected void doHandlerAction1() {  //取消订单请求超时后执行
        if(null != cancelOderSubscription){
            cancelOderSubscription.unsubscribe();
            mView.showTimeOutToast();
        }
        mView.hideLoadingDialog();
    }

    @Override
    public ResourceFilterItemEntity getContactWayMap(){
        ResourceFilterItemEntity entity = new ResourceFilterItemEntity();
        if(null != NewCustomerConstants.contactMethodMap && NewCustomerConstants.contactMethodMap.size() > 0) {
            Iterator<Map.Entry<String, String>> iterator = NewCustomerConstants.contactMethodMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                if("15010".equals(entry.getKey())) {
                    entity.setCode(entry.getKey());
                    entity.setName(entry.getValue());
                }
            }
        }
        return entity;
    }

    @Override
    public List<ResourceFilterItemEntity> getFollowUpList(String contactId, String oppStatusId){
        Map<String, String> map = DBUtils.getFollowUpResultMap(contactId, oppStatusId, -1);
        List<ResourceFilterItemEntity> list = new ArrayList<>();
        Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            if(OrderCancelActivity2.FOLLOW_RESULT_CONTINUE.equals(entry.getKey())){
                list.add(new ResourceFilterItemEntity(entry.getValue(), entry.getKey(), false, true));
            }else {
                list.add(new ResourceFilterItemEntity(entry.getValue(), entry.getKey(), false));
            }
        }
        return list;
    }
}
