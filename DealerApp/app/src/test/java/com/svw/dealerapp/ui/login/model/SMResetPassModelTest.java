package com.svw.dealerapp.ui.login.model;

import com.google.gson.Gson;
import com.svw.dealerapp.entity.SMResEntity;
import com.svw.dealerapp.entity.login.SMResetPassEntity;
import com.svw.dealerapp.global.Constants;
import com.svw.dealerapp.utils.RxSchedulersOverrideRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import rx.functions.Action1;

import static org.junit.Assert.*;

/**
 * Created by lijinkui on 2018/1/25.
 */
public class SMResetPassModelTest {

    private SMResetPassModel mModel;

    @Rule
    public final RxSchedulersOverrideRule mOverrideSchedulersRule = new RxSchedulersOverrideRule();

    @Before
    public void setUp() throws Exception {
        mModel = new SMResetPassModel();
    }

    @Test
    public void smResetPass() throws Exception {
        //with api gateway
        Constants.setApiBaseUrlService(1);
        Constants.set3thApiBaseUrlService(1);
        Constants.setSMCommonParam(1);
        //without api gateway
//        Constants.setApiBaseUrlServiceTest();

        Map<String, Object> options = new HashMap<>();
        options.put("oldPassword","123456r");
        options.put("newPassword","123456r1");
//        options.put("captcha","");
        mModel.smResetPass(options).doOnNext(new Action1<SMResEntity<Object>>() {
            @Override
            public void call(SMResEntity<Object> smResEntity) {
                System.out.println(new Gson().toJson(smResEntity));
            }
        }).toBlocking().single();
    }

}