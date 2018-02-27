package com.svw.dealerapp.ui.mine.model;

import com.google.gson.Gson;
import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.mine.ApproveWaitEntity;
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
public class ApproveWaitModelTest {

    private ApproveWaitModel mModel;

    @Rule
    public final RxSchedulersOverrideRule mOverrideSchedulersRule = new RxSchedulersOverrideRule();

    @Before
    public void setUp() throws Exception {
        mModel = new ApproveWaitModel();
    }

    @Test
    public void testPostApprove() throws Exception {
        Map<String,Object> options = new HashMap<>();
        options.put("approvalId","2a84d1e3-5dba-4a1a-8c86-6372948a382d");
        options.put("approvalDesc","不知道啊");
        options.put("approvalStatusId","18520");
        mModel.postApprove(options).doOnNext(new Action1<ResEntity<Object>>() {
            @Override
            public void call(ResEntity<Object> objectResEntity) {
                System.out.println(new Gson().toJson(objectResEntity));
            }
        }).toBlocking().single();
    }

    @Test
    public void testGetDataFromServer() throws Exception {
        mModel.getDataFromServer("1","10","1","0").doOnNext(new Action1<ResEntity<ApproveWaitEntity>>() {
            @Override
            public void call(ResEntity<ApproveWaitEntity> approveWaitEntityResEntity) {
                System.out.println(new Gson().toJson(approveWaitEntityResEntity));
            }
        }).toBlocking().single();
    }

}