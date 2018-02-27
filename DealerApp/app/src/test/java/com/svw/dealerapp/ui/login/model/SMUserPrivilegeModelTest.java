package com.svw.dealerapp.ui.login.model;

import com.google.gson.Gson;
import com.svw.dealerapp.entity.SMResEntity;
import com.svw.dealerapp.entity.login.SMUserPrivilegeByAppIdEntity;
import com.svw.dealerapp.global.Constants;
import com.svw.dealerapp.utils.RxSchedulersOverrideRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.functions.Action1;

import static org.junit.Assert.*;

/**
 * Created by lijinkui on 2018/1/25.
 */
public class SMUserPrivilegeModelTest {

    private SMUserPrivilegeModel mModel;

    @Rule
    public final RxSchedulersOverrideRule mOverrideSchedulersRule = new RxSchedulersOverrideRule();

    @Before
    public void setUp() throws Exception {
        mModel = new SMUserPrivilegeModel();
    }

    @Test
    public void smUserPrivilege() throws Exception {
        //with api gateway
        Constants.setApiBaseUrlService(1);
        Constants.set3thApiBaseUrlService(1);
        Constants.setSMCommonParam(1);
        //without api gateway
//        Constants.setApiBaseUrlServiceTest();

        Map<String, Object> options = new HashMap<>();
        options.put("appId", Constants.APP_ID);
        options.put("category", "08");
        mModel.smUserPrivilege(options).doOnNext(new Action1<SMResEntity<List<SMUserPrivilegeByAppIdEntity>>>() {
            @Override
            public void call(SMResEntity<List<SMUserPrivilegeByAppIdEntity>> smUserPrivilegeEntitySMResEntity) {
                System.out.println(new Gson().toJson(smUserPrivilegeEntitySMResEntity));
            }
        }).toBlocking().single();
    }

}