package com.svw.dealerapp.ui.dictionary;

import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.dictionary.DictionaryEntity;
import com.svw.dealerapp.mvpframe.BasePresenter;

import rx.Observable;

/**
 * Created by lijinkui on 2017/5/15.
 */

public interface DictionaryContract {

    interface View {
        void getDictSuccess();
        void getDictFail();
    }

    interface Model {
        Observable<ResEntity<DictionaryEntity>> getDictionary(String version);
    }

    abstract class Presenter extends BasePresenter<Model,View> {
        public abstract void getDictionary(String version);
    }
}
