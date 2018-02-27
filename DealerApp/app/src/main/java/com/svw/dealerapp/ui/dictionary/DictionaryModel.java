package com.svw.dealerapp.ui.dictionary;

import com.svw.dealerapp.api.NetworkManager;
import com.svw.dealerapp.api.RequestBaseParamsConfig;
import com.svw.dealerapp.api.dictionary.DictionaryApi;
import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.dictionary.DictionaryEntity;
import com.svw.dealerapp.mvpframe.rx.RxSchedulers;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;

/**
 * Created by lijinkui on 2017/5/15.
 */

public class DictionaryModel implements DictionaryContract.Model {

    private Map<String, String> optionsForDictionary(String version) {

        Map<String, String> options = new HashMap<String, String>();
        options.put("version", version);
        options = RequestBaseParamsConfig.addRequestBaseParams(options);

        return options;
    }

    @Override
    public Observable<ResEntity<DictionaryEntity>> getDictionary(String version) {
        return NetworkManager.getInstance().getApiInstance(DictionaryApi.class, true)
                .getDictionary(optionsForDictionary(version))
                .compose(RxSchedulers.<ResEntity<DictionaryEntity>>io_main());
    }
}
