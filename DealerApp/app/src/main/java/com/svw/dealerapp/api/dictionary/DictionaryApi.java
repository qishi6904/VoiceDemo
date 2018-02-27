package com.svw.dealerapp.api.dictionary;

import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.dictionary.DictionaryEntity;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by lijinkui on 2017/5/15.
 */

public interface DictionaryApi{
    @GET("api/dictionaries")
    Observable<ResEntity<DictionaryEntity>> getDictionary(@QueryMap Map<String, String> options);
}
