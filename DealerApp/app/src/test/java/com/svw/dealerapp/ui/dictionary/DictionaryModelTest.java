package com.svw.dealerapp.ui.dictionary;

import com.google.gson.Gson;
import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.dictionary.DictionaryEntity;
import com.svw.dealerapp.global.Constants;
import com.svw.dealerapp.utils.RxSchedulersOverrideRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import rx.functions.Action1;

import static org.junit.Assert.*;

/**
 * Created by lijinkui on 2017/5/15.
 */
public class DictionaryModelTest {

    private DictionaryModel mModel;

    @Rule
    public final RxSchedulersOverrideRule mOverrideSchedulersRule = new RxSchedulersOverrideRule();

    @Before
    public void setUp() throws Exception {
        mModel = new DictionaryModel();
    }

    @Test
    public void testForGetDictionary() throws Exception {
        //with api gateway
        Constants.setApiBaseUrlService(1);
        Constants.set3thApiBaseUrlService(1);
        Constants.setSMCommonParam(1);
        //without api gateway
//        Constants.setApiBaseUrlServiceTest();
        mModel.getDictionary("0.9").doOnNext(new Action1<ResEntity<DictionaryEntity>>() {
            @Override
            public void call(ResEntity<DictionaryEntity> dictionaryEntityResEntity) {
                System.out.println(new Gson().toJson(dictionaryEntityResEntity));
            }
        }).toBlocking().single();
    }

}