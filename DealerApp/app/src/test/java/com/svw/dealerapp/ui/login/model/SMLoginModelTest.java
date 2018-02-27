package com.svw.dealerapp.ui.login.model;

import com.google.gson.Gson;
import com.svw.dealerapp.entity.SMResEntity;
import com.svw.dealerapp.entity.login.SMLoginEntity;
import com.svw.dealerapp.global.Constants;
import com.svw.dealerapp.utils.RxSchedulersOverrideRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import rx.functions.Action1;

/**
 * Created by lijinkui on 2018/1/25.
 */
public class SMLoginModelTest {

    private SMLoginModel mModel;

    @Rule
    public final RxSchedulersOverrideRule mOverrideSchedulersRule = new RxSchedulersOverrideRule();

    @Before
    public void setUp() throws Exception {
        mModel = new SMLoginModel();
    }

    @Test
    public void smLogin() throws Exception {
        //with api gateway
        Constants.setApiBaseUrlService(1);
        Constants.set3thApiBaseUrlService(1);
        Constants.setSMCommonParam(1);
        //without api gateway
//        Constants.setApiBaseUrlServiceTest();

        Map<String, Object> options = new HashMap<>();
        options.put("username","17715169776");
        options.put("password","p1234567");
        options.put("client_id", Constants.CLIENT_ID);
        options.put("category","08");
        mModel.smLogin(options).doOnNext(new Action1<SMResEntity<SMLoginEntity>>() {
            @Override
            public void call(SMResEntity<SMLoginEntity> smLoginEntitySMResEntity) {
                System.out.println(new Gson().toJson(smLoginEntitySMResEntity));
            }
        }).toBlocking().single();
    }

}