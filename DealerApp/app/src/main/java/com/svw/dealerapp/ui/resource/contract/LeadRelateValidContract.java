package com.svw.dealerapp.ui.resource.contract;

import android.content.Context;

import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.mvpframe.listfragment.ShowToastView;

import java.util.Map;

import rx.Observable;

/**
 * Created by lijinkui on 2017/11/22.
 */

public class LeadRelateValidContract {
    public interface View extends ShowToastView {
        void leadRelateValidSuccess(String msg);

        void leadRelateValidFail(String returnCode, String msg);

        void leadRelateValidTimeout();
    }

    public interface Presenter {
        void leadRelateValid(Context context, Map<String, Object> options);
    }

    public interface Model {
        Observable<ResEntity<Object>> leadRelateValid(Map<String, Object> options);

    }
}
