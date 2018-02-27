package com.svw.dealerapp.ui.newcustomer;

import com.google.gson.Gson;
import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.ui.newcustomer.model.YCDetailHeadModel;
import com.svw.dealerapp.utils.RxSchedulersOverrideRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import rx.functions.Action1;

/**
 * Created by lijinkui on 2017/6/21.
 */
public class YCDetailHeadModelTest {

    YCDetailHeadModel mModel;

    @Rule
    public final RxSchedulersOverrideRule mOverrideSchedulersRule = new RxSchedulersOverrideRule();

    @Before
    public void setUp() throws Exception {
        mModel = new YCDetailHeadModel();
    }

    @Test
    public void testPostVipCustomer() {
        mModel.postVipCustomer("a8f1b10e-dc4a-419c-a423-98ed4147d690", "0").doOnNext(new Action1<ResEntity<Object>>() {
            @Override
            public void call(ResEntity<Object> objectResEntity) {
                System.out.println(new Gson().toJson(objectResEntity));
            }
        }).toBlocking().single();
    }
}