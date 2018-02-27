package com.svw.dealerapp.ui.login;

import com.google.gson.Gson;
import com.svw.dealerapp.api.NetworkManager;
import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.login.LoginEntity;
import com.svw.dealerapp.global.Constants;
import com.svw.dealerapp.utils.RxSchedulersOverrideRule;


import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import rx.functions.Action1;

import static org.junit.Assert.*;

/**
 * Created by lijinkui on 2017/5/9.
 */

@Deprecated
public class LoginModelTest {

    private LoginModel mLoginModel;

    @Rule
    public final RxSchedulersOverrideRule mOverrideSchedulersRule = new RxSchedulersOverrideRule();

    @Before
    public void setUp() throws Exception {
        mLoginModel = new LoginModel();
    }

    @Test
    public void testForUserLogin() throws Exception {
        //with api gateway
        Constants.setApiBaseUrlService(1);
        Constants.set3thApiBaseUrlService(1);
        Constants.setSMCommonParam(1);
        //without api gateway
//        Constants.setApiBaseUrlServiceTest();
        mLoginModel.userLogin("007001","e10adc3949ba59abbe56e057f20f883e").doOnNext(new Action1<ResEntity<LoginEntity>>() {
            @Override
            public void call(ResEntity<LoginEntity> loginEntityResEntity) {
//                NetworkManager.getInstance().setToken(loginEntityResEntity.getRetData().getXtoken());
                System.out.println(new Gson().toJson(loginEntityResEntity));
            }
        }).toBlocking().single();
    }

}