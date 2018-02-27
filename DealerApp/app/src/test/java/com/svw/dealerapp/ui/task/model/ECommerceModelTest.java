package com.svw.dealerapp.ui.task.model;

import com.google.gson.Gson;
import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.task.BenefitEntity;
import com.svw.dealerapp.entity.task.TaskECommerceEntity;
import com.svw.dealerapp.utils.RxSchedulersOverrideRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import rx.functions.Action1;

import static org.junit.Assert.*;

/**
 * Created by lijinkui on 2017/5/31.
 */
public class ECommerceModelTest {

    private ECommerceModel mModel;

    @Rule
    public final RxSchedulersOverrideRule mOverrideSchedulersRule = new RxSchedulersOverrideRule();

    @Before
    public void setUp() throws Exception {
        mModel = new ECommerceModel();
    }

    @Test
    public void testGetDataFromServer() throws Exception {
        mModel.getDataFromServer("1","10","1","").doOnNext(new Action1<ResEntity<TaskECommerceEntity>>() {
            @Override
            public void call(ResEntity<TaskECommerceEntity> taskECommerceEntityResEntity) {
                System.out.println(new Gson().toJson(taskECommerceEntityResEntity));
            }
        }).toBlocking().single();
    }

    @Test
    public void testPostVipCustomer() throws Exception {
        mModel.postVipCustomer("0121021e-7b75-4552-bc1e-9b882e457c3b", "0").doOnNext(new Action1<ResEntity<Object>>() {
            @Override
            public void call(ResEntity<Object> objectResEntity) {
                System.out.println(new Gson().toJson(objectResEntity));
            }
        }).toBlocking().single();
    }

    @Test
    public void testGetBenefitDate() throws Exception {
        Map<String, Object>options = new HashMap();
        options.put("leadsId","ce41b68b-f5a1-468f-af3c-1c5b67469499");
        mModel.getBenefitDate(options).doOnNext(new Action1<ResEntity<BenefitEntity>>() {
            @Override
            public void call(ResEntity<BenefitEntity> benefitEntityResEntity ) {
                System.out.println(new Gson().toJson(benefitEntityResEntity));
            }
        }).toBlocking().single();
    }
}