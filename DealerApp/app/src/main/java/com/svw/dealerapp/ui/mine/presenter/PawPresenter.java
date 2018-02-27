package com.svw.dealerapp.ui.mine.presenter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.svw.dealerapp.DealerApp;
import com.svw.dealerapp.api.NetworkManager;
import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.mine.ResetPassEntity;
import com.svw.dealerapp.mvpframe.listfragment.BaseHandlerPresenter;
import com.svw.dealerapp.mvpframe.listfragment.ToastObserver;
import com.svw.dealerapp.ui.mine.contract.PawContract;
import com.svw.dealerapp.util.dbtools.DBHelper;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by qinshi on 6/1/2017.
 */

@Deprecated
public class PawPresenter extends BaseHandlerPresenter<PawContract.Model, PawContract.View>
        implements PawContract.Presenter {

    private Subscription postResetPawSubscription;

    public PawPresenter(PawContract.View view, PawContract.Model model){
        super(view, model);
    }

    @Override
    public void postResetPaw(Context context, String userId, String oldPaw, String newPaw) {

        Observable observable = mModel.postResetPaw(userId, oldPaw, newPaw)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        ToastObserver observer = new ToastObserver<ResEntity<ResetPassEntity>>(mView, handler, handlerAction1) {

            @Override
            public void dealResult(ResEntity<ResetPassEntity> entity) {
                if(null != entity && "200".equals(entity.getRetCode())) {
                    ResetPassEntity resetPassEntity = entity.getRetData();
                    saveToken(resetPassEntity);
                    mView.showResetPawSuccess();
                }else if(null != entity && "006002".equals(entity.getRetCode())){
                    mView.showOldPawErrorToast();
                }else {
                    mView.showServerErrorToast();
                }


            }
        };

        postResetPawSubscription = requestByToast(context, observable, observer, mView, handlerAction1);
    }

    @Override
    protected void doHandlerAction1() {
        if(null != postResetPawSubscription){
            postResetPawSubscription.unsubscribe();
            mView.showTimeOutToast();
        }
        mView.hideLoadingDialog();
    }

    private void saveToken(ResetPassEntity resetPassEntity){
        DBHelper dbHelper = DealerApp.dbHelper;
        ContentValues values = new ContentValues();
        ResetPassEntity.TokenInfoBean tokenInfoBean = resetPassEntity.getTokenInfo();
        if(tokenInfoBean != null){
            values.put("webToken", tokenInfoBean.getWebToken());
            values.put("accessToken", tokenInfoBean.getAccessToken());
            values.put("tokenType", tokenInfoBean.getTokenType());
            values.put("refreshToken", tokenInfoBean.getRefreshToken());
            values.put("expiresIn", tokenInfoBean.getExpiresIn());
            values.put("scope", tokenInfoBean.getScope());
        }
        Cursor cursor = dbHelper.rawQuery("select * from User" ,null);
        if(cursor !=null){
            if(cursor.moveToNext()){
                dbHelper.update("User",values,null,null);
            }else {
                if(dbHelper.insert("User",values)){
                    DealerApp.ACCESS_TOKEN = tokenInfoBean.getAccessToken();
                }
            }
            NetworkManager.getInstance().setToken(DealerApp.ACCESS_TOKEN);
            cursor.close();
        }
    }
}
