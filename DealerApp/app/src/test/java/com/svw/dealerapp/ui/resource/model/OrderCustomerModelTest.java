package com.svw.dealerapp.ui.resource.model;

import com.google.gson.Gson;
import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.resource.OrderCustomerEntity;
import com.svw.dealerapp.utils.RxSchedulersOverrideRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import rx.functions.Action1;

/**
 * Created by lijinkui on 2017/5/10.
 */
public class OrderCustomerModelTest {

    private OrderCustomerModel mModel;

    @Rule
    public final RxSchedulersOverrideRule mOverrideSchedulersRule = new RxSchedulersOverrideRule();

    @Before
    public void setUp() throws Exception {
        mModel = new OrderCustomerModel();
    }

    @Test
    public void testGetDataFromServer() throws Exception {
        mModel.getDataFromServer("1", "10", "", "").doOnNext(new Action1<ResEntity<OrderCustomerEntity>>() {
            @Override
            public void call(ResEntity<OrderCustomerEntity> entity) {
                System.out.println(new Gson().toJson(entity));
            }
        }).toBlocking().single();
    }

    @Test
    public void testPostCancelOrder() {
        Map<String, Object> params = new HashMap<>();
        // TODO: 8/7/2017 添加请求参数
        mModel.postCancelOrder(params)
                .doOnNext(new Action1<ResEntity<Object>>() {
                    @Override
                    public void call(ResEntity<Object> entity) {
                        System.out.println(new Gson().toJson(entity));
                    }
                }).toBlocking().single();
    }

}