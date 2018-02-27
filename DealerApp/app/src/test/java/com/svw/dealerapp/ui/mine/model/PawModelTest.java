package com.svw.dealerapp.ui.mine.model;

import com.google.gson.Gson;
import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.mine.ResetPassEntity;
import com.svw.dealerapp.utils.RxSchedulersOverrideRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import rx.functions.Action1;

import static org.junit.Assert.*;

/**
 * Created by lijinkui on 2017/6/7.
 */
public class PawModelTest {

    private PawModel mModel;

    @Rule
    public final RxSchedulersOverrideRule mOverrideSchedulersRule = new RxSchedulersOverrideRule();


    @Before
    public void setUp() throws Exception {
        mModel = new PawModel();
    }

    @Test
    public void testPostResetPaw() throws Exception {
        mModel.postResetPaw("","123456","e10adc3949ba59abbe56e057f20f883e").doOnNext(new Action1<ResEntity<ResetPassEntity>>() {
            @Override
            public void call(ResEntity<ResetPassEntity> resetPassEntityResEntity) {
                System.out.println(new Gson().toJson(resetPassEntityResEntity));
            }
        }).toBlocking().single();
    }

}