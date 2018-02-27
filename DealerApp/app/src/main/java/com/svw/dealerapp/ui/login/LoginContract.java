package com.svw.dealerapp.ui.login;

import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.dictionary.DictionaryEntity;
import com.svw.dealerapp.entity.login.LoginEntity;
import com.svw.dealerapp.mvpframe.BaseModel;
import com.svw.dealerapp.mvpframe.BasePresenter;
import com.svw.dealerapp.mvpframe.BaseView;

import rx.Observable;

/**
 * Created by xupan on 05/05/2017.
 */

@Deprecated
public interface LoginContract {

    interface Model {
        Observable<ResEntity<LoginEntity>> userLogin(String userId, String password);
//        Observable<ResEntity<DictionaryEntity>> getDictionary(String version);
    }

    interface View {
        void onLoginSuccess(boolean isNewUser);
        void onLoginError(String msg);
//        void getDictSuccess();
//        void getDictFail();
    }

    abstract class Presenter extends BasePresenter<Model, View>{
        public abstract void requestLogin(String username, String password);
//        public abstract void getDictionary(String version);
    }
}
