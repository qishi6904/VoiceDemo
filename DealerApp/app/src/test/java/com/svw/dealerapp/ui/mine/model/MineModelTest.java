package com.svw.dealerapp.ui.mine.model;

import com.google.gson.Gson;
import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.mine.LogoutEntity;
import com.svw.dealerapp.entity.mine.MineHomeEntity;
import com.svw.dealerapp.utils.RxSchedulersOverrideRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import rx.functions.Action1;

import static org.junit.Assert.*;

/**
 * Created by lijinkui on 2017/6/7.
 */
public class MineModelTest {

    private MineModel mModel;

    @Rule
    public final RxSchedulersOverrideRule mOverrideSchedulersRule = new RxSchedulersOverrideRule();

    @Before
    public void setUp() throws Exception {
        mModel = new MineModel();
    }

    @Test
    public void testGetMineHomeData() throws Exception {
        Map<String,Object> options = new HashMap<>();
        options.put("userId","");
        mModel.getMineHomeData(options).doOnNext(new Action1<ResEntity<MineHomeEntity>>() {
            @Override
            public void call(ResEntity<MineHomeEntity> mineHomeEntityResEntity) {
                System.out.println(new Gson().toJson(mineHomeEntityResEntity));
            }
        }).toBlocking().single();
    }

    @Test
    public void testLogout() throws Exception {
        mModel.logout().doOnNext(new Action1<ResEntity<LogoutEntity>>() {
            @Override
            public void call(ResEntity<LogoutEntity> logoutEntityResEntity) {
                System.out.println(new Gson().toJson(logoutEntityResEntity));
            }
        }).toBlocking().single();
    }
}