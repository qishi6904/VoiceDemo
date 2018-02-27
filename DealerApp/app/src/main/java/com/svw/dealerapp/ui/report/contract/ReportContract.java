package com.svw.dealerapp.ui.report.contract;

import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.report.WebTokenEntity;
import com.svw.dealerapp.mvpframe.BasePresenter;

import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by lijinkui on 2017/8/1.
 */

@Deprecated
public interface ReportContract {
    interface View {
        void getWebTokenSuccess(String result);
        void getWebTokenFail(String result);
    }

    interface Model {
        Observable<ResEntity<WebTokenEntity>> updateWebToken(String webToken);
    }

    abstract class Presenter extends BasePresenter<Model,View> {
        public abstract void updateWebToken(String webToken);
    }
}
