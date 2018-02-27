package com.svw.dealerapp.ui.order.model;

import com.google.gson.Gson;
import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.order.CancelOrderReqEntity;
import com.svw.dealerapp.global.Constants;
import com.svw.dealerapp.utils.RxSchedulersOverrideRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Action1;

import static org.junit.Assert.*;

/**
 * Created by lijinkui on 2017/9/28.
 */
public class CancelOrderFollowupModelTest {

    CancelOrderFollowupModel mModel;

    @Rule
    public final RxSchedulersOverrideRule mOverrideSchedulersRule = new RxSchedulersOverrideRule();

    @Before
    public void setUp() throws Exception {
        mModel = new CancelOrderFollowupModel();
        //with api gateway
        Constants.setApiBaseUrlService(1);
        Constants.set3thApiBaseUrlService(1);
        Constants.setSMCommonParam(1);
        //without api gateway
//        Constants.setApiBaseUrlServiceTest();
    }

    @Test
    public void cancelOrderFollowup() throws Exception {
        mModel.cancelOrderFollowup(optionsForCancelOrderFollowup())
                .doOnNext(new Action1<ResEntity<Object>>() {
                    @Override
                    public void call(ResEntity<Object> entity) {
                        System.out.println(new Gson().toJson(entity));
                    }
                }).toBlocking().single();
    }

    private CancelOrderReqEntity optionsForCancelOrderFollowup(){
        CancelOrderReqEntity reqEntity = new CancelOrderReqEntity();
        reqEntity.setOrderId("e40c4025-68c2-4c31-850d-dc74ed192f6c");
        reqEntity.setOppId("f9d34a31-c624-4e7f-b19c-db1b35366349");
        reqEntity.setModeId("15020");
        List<CancelOrderReqEntity.CancelOrderFollowupResult> cancelOrderFollowupResultList = new ArrayList<>();
        CancelOrderReqEntity.CancelOrderFollowupResult cancelOrderFollowupResult = new CancelOrderReqEntity.CancelOrderFollowupResult();
        cancelOrderFollowupResult.setDictId("15570");
        cancelOrderFollowupResult.setResultDesc("战败原因描述");
        cancelOrderFollowupResultList.add(cancelOrderFollowupResult);
        reqEntity.setFollowupResults(cancelOrderFollowupResultList);
        reqEntity.setNextModeId("15020");
        reqEntity.setScheduleDateStr("2017-10-15T10:01:06.006+0800");
        reqEntity.setNextScheduleDesc("跟进计划描述");
        //assignment
        return reqEntity;
    }

}