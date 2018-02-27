package com.svw.dealerapp.ui.mine.model;

import com.google.gson.Gson;
import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.mine.ApproveCompleteEntity;
import com.svw.dealerapp.utils.RxSchedulersOverrideRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import rx.functions.Action1;

import static org.junit.Assert.*;

/**
 * Created by lijinkui on 2017/6/7.
 */
public class ApproveCompleteModelTest {

    private ApproveCompleteModel mModel;

    @Rule
    public final RxSchedulersOverrideRule mOverrideSchedulersRule = new RxSchedulersOverrideRule();

    @Before
    public void setUp() throws Exception {
        mModel = new ApproveCompleteModel();
    }

    @Test
    public void TestGetDataFromServer() throws Exception {
        mModel.getDataFromServer("1","10","1","1").doOnNext(new Action1<ResEntity<ApproveCompleteEntity>>() {
            @Override
            public void call(ResEntity<ApproveCompleteEntity> approveCompleteEntityResEntity) {
                System.out.println(new Gson().toJson(approveCompleteEntityResEntity));
            }
        }).toBlocking().single();
    }

}