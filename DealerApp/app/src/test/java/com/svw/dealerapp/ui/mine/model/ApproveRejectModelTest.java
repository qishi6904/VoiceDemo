package com.svw.dealerapp.ui.mine.model;

import com.google.gson.Gson;
import com.svw.dealerapp.entity.ResEntity;
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
public class ApproveRejectModelTest {

    private ApproveRejectModel mModel;

    @Rule
    public final RxSchedulersOverrideRule mOverrideSchedulersRule = new RxSchedulersOverrideRule();

    @Before
    public void setUp() throws Exception {
        mModel = new ApproveRejectModel();
    }

    @Test
    public void rejectApprove() throws Exception {
        Map<String,Object> options = new HashMap<>();
        options.put("approvalId","2a84d1e3-5dba-4a1a-8c86-6372948a382d");
        options.put("approvalDesc","不知道啊");
        options.put("approvalStatusId","18530");
        mModel.rejectApprove(options).doOnNext(new Action1<ResEntity<Object>>() {
            @Override
            public void call(ResEntity<Object> objectResEntity) {
                System.out.println(new Gson().toJson(objectResEntity));
            }
        }).toBlocking().single();
    }

}