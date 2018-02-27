package com.svw.dealerapp.ui.login;

import android.content.ContentValues;
import android.database.Cursor;

import com.svw.dealerapp.BuildConfig;
import com.svw.dealerapp.DealerApp;
import com.svw.dealerapp.R;
import com.svw.dealerapp.api.NetworkManager;
import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.login.LoginEntity;
import com.svw.dealerapp.util.AesUtils;
import com.svw.dealerapp.util.dbtools.DBHelper;
import com.svw.dealerapp.util.JLog;

import rx.Observer;

/**
 * Created by xupan on 10/05/2017.
 */

@Deprecated
public class LoginPresenter extends LoginContract.Presenter {
    private static final String TAG = "LoginPresenter";

    private boolean isNewUser = true;

    public LoginPresenter(LoginContract.Model model, LoginContract.View view) {
        this.mModel = model;
        this.mView = view;
    }

    private Observer<ResEntity<LoginEntity>> mLoginResultObserver = new Observer<ResEntity<LoginEntity>>() {
        @Override
        public void onCompleted() {
            JLog.d(TAG, "onCompleted");
        }

        @Override
        public void onError(Throwable e) {
            JLog.d(TAG, "onError");
            e.printStackTrace();
            mView.onLoginError("error");
        }

        @Override
        public void onNext(ResEntity<LoginEntity> loginEntityResEntity) {
            JLog.d(TAG, "onNext");
            JLog.d(TAG, "RESULT: " + loginEntityResEntity);
            if (loginEntityResEntity.getRetCode().equals("200")) {
                LoginEntity loginEntity = loginEntityResEntity.getRetData();
                saveToken(loginEntity);
                mView.onLoginSuccess(isNewUser);
            } else if (loginEntityResEntity.getRetCode().equals("001001")) {
                mView.onLoginError(DealerApp.getContext().getResources().getString(R.string.login_user_not_exist));
            } else if (loginEntityResEntity.getRetCode().equals("001002")) {
                mView.onLoginError(DealerApp.getContext().getResources().getString(R.string.login_user_passwod_error));
            } else {
                mView.onLoginError(loginEntityResEntity.getRetMessage());
            }
        }
    };

    @Override
    public void requestLogin(String username, String password) {
        mRxManager.add(mModel.userLogin(username, password).subscribe(mLoginResultObserver));
    }

    private void saveToken(LoginEntity loginEntity) {
        DBHelper dbHelper = DealerApp.dbHelper;
        ContentValues values = new ContentValues();
        LoginEntity.UserInfoBean userInfoBean = loginEntity.getUserInfo();
        LoginEntity.TokenInfoBean tokenInfoBean = loginEntity.getTokenInfo();
        if (tokenInfoBean != null) {
            values.put("webToken", tokenInfoBean.getWebToken());
            values.put("accessToken", tokenInfoBean.getAccessToken());
            values.put("tokenType", tokenInfoBean.getTokenType());
            values.put("refreshToken", tokenInfoBean.getRefreshToken());
            values.put("expiresIn", tokenInfoBean.getExpiresIn());
            values.put("scope", tokenInfoBean.getScope());
        }
        values.put("roleIds", loginEntity.getRoleIds());
        if (userInfoBean != null) {
            values.put("userId", AesUtils.encrypt(BuildConfig.AES_SECRET_KEY, userInfoBean.getUserId()));
            values.put("orgId", AesUtils.encrypt(BuildConfig.AES_SECRET_KEY, userInfoBean.getOrgId()));
            values.put("name", AesUtils.encrypt(BuildConfig.AES_SECRET_KEY, userInfoBean.getName()));
            values.put("gender", AesUtils.encrypt(BuildConfig.AES_SECRET_KEY, userInfoBean.getGender()));
            values.put("image", AesUtils.encrypt(BuildConfig.AES_SECRET_KEY, userInfoBean.getImage()));
            values.put("dutyId", AesUtils.encrypt(BuildConfig.AES_SECRET_KEY, userInfoBean.getDutyId()));
            values.put("mobile", AesUtils.encrypt(BuildConfig.AES_SECRET_KEY, userInfoBean.getMobile()));
            values.put("telephone", AesUtils.encrypt(BuildConfig.AES_SECRET_KEY, userInfoBean.getTelephone()));
            values.put("wechat", AesUtils.encrypt(BuildConfig.AES_SECRET_KEY, userInfoBean.getWechat()));
            values.put("email", AesUtils.encrypt(BuildConfig.AES_SECRET_KEY, userInfoBean.getEmail()));
            values.put("status", AesUtils.encrypt(BuildConfig.AES_SECRET_KEY, userInfoBean.getStatus()));
            values.put("createUser", AesUtils.encrypt(BuildConfig.AES_SECRET_KEY, userInfoBean.getCreateUser()));
            values.put("createTime", AesUtils.encrypt(BuildConfig.AES_SECRET_KEY, userInfoBean.getCreateTime()));
            values.put("updateTime", AesUtils.encrypt(BuildConfig.AES_SECRET_KEY, userInfoBean.getUpdateTime()));
            values.put("remark", AesUtils.encrypt(BuildConfig.AES_SECRET_KEY, userInfoBean.getRemark()));
            values.put("orgName", AesUtils.encrypt(BuildConfig.AES_SECRET_KEY, userInfoBean.getOrgName()));
            values.put("orgAddress", AesUtils.encrypt(BuildConfig.AES_SECRET_KEY, userInfoBean.getOrgAddress()));
            values.put("orgProvinceId", AesUtils.encrypt(BuildConfig.AES_SECRET_KEY, userInfoBean.getOrgProvinceId()));
            values.put("orgCityId", AesUtils.encrypt(BuildConfig.AES_SECRET_KEY, userInfoBean.getOrgCityId()));
        }
        Cursor cursor = dbHelper.rawQuery("select * from User", null);
        if (cursor != null) {
            if (cursor.moveToNext()) {
                String oldUserId = cursor.getString(cursor.getColumnIndex("userId"));
                if (oldUserId.equals(AesUtils.encrypt(BuildConfig.AES_SECRET_KEY, userInfoBean.getUserId()))) {
                    isNewUser = false;
                }
                dbHelper.update("User", values, null, null);
                DealerApp.ACCESS_TOKEN = tokenInfoBean.getAccessToken();
            } else {
                if (dbHelper.insert("User", values)) {
                    DealerApp.ACCESS_TOKEN = tokenInfoBean.getAccessToken();
                }
            }
            NetworkManager.getInstance().setToken(DealerApp.ACCESS_TOKEN);
            cursor.close();
        }
    }

//    private Observer<ResEntity<DictionaryEntity>> getDictSubscription = new Observer<ResEntity<DictionaryEntity>>() {
//        @Override
//        public void onCompleted() {
//        }
//
//        @Override
//        public void onError(Throwable e) {
//            e.printStackTrace();
//        }
//
//        @Override
//        public void onNext(ResEntity<DictionaryEntity> dictionaryEntityResEntity) {
//            if (dictionaryEntityResEntity.getRetCode().equals("200")) {
//                DictionaryEntity dictionaryEntity = dictionaryEntityResEntity.getRetData();
//                if (dictionaryEntity != null && dictionaryEntity.getDictTypeIdList() != null && dictionaryEntity.getDictTypeIdList().size() > 0) {
//                    saveDictionary(dictionaryEntity);
//                }
//                if (dictionaryEntity != null && dictionaryEntity.getDictRelationList() != null && dictionaryEntity.getDictRelationList().size() > 0) {
//                    saveDictionaryRel(dictionaryEntity.getDictRelationList());
//                }
//                if (dictionaryEntity != null && dictionaryEntity.getDicCarSeriesAndModel() != null && dictionaryEntity.getDicCarSeriesAndModel().size() > 0) {
//                    saveCarSeriesModelDict(dictionaryEntity.getDicCarSeriesAndModel());
//                }
//                if (dictionaryEntity != null && dictionaryEntity.getOrderDictTypeIdList() != null && dictionaryEntity.getOrderDictTypeIdList().size() > 0) {
//                    saveOrderDictionary(dictionaryEntity.getOrderDictTypeIdList());
//                }
//                DBUtils.setOptionsFromDB();
//                DBUtils.setOrderOptionsFromDB();
//                DBUtils.setCarSeriesFromDB();
//                mView.getDictSuccess();
//            } else if (dictionaryEntityResEntity.getRetCode().equals("401") || dictionaryEntityResEntity.getRetCode().equals("404")) {
//                mView.getDictFail();
//            }
//        }
//    };
//
//    @Override
//    public void getDictionary(String version) {
//        mRxManager.add(mModel.getDictionary(version).subscribe(getDictSubscription));
//    }
//
//    private void saveDictionary(DictionaryEntity dictionaryEntity) {
//        List<DictionaryEntity.DictTypeIdListBean> dictList;
//        String version = dictionaryEntity.getVersion();
//        DBHelper dbHelper = DealerApp.dbHelper;
//        if (dictionaryEntity.getDictTypeIdList().size() > 0) {
//            dictList = dictionaryEntity.getDictTypeIdList();
//            dbHelper.delete("Dictionary", null, null);
//            for (int i = 0; i < dictList.size(); i++) {
//                String dictTypeId = dictList.get(i).getDictTypeId();
//                String dictTypeName = dictList.get(i).getDictTypeName();
//                List<DictionaryEntity.DictTypeIdListBean.DictDataBean> dictDataBeanList = dictList.get(i).getDictData();
//                for (DictionaryEntity.DictTypeIdListBean.DictDataBean dictDataBean : dictDataBeanList) {
//                    ContentValues values = new ContentValues();
//                    values.put("dictTypeId", dictTypeId);
//                    values.put("dictTypeName", dictTypeName);
//                    values.put("dictId", dictDataBean.getDictId());
//                    values.put("dictName", dictDataBean.getDictName());
//                    values.put("version", version);
//                    dbHelper.insert("Dictionary", values);
//                }
//            }
//            //test db
////            Cursor cursor = dbHelper.rawQuery("select * from Dictionary",null);
////            if(cursor !=null){
////                if(cursor.moveToNext()){
////                    String content_id = cursor.getString(3);
////                }
////                cursor.close();
////            }
//        }
//    }
//
//    private void saveDictionaryRel(List<DictionaryEntity.DictRelationListBean> dictRelationList) {
//        DBHelper dbHelper = DealerApp.dbHelper;
//        dbHelper.delete("DictionaryRel", null, null);
//        for (int i = 0; i < dictRelationList.size(); i++) {
//            String relaTypeId = dictRelationList.get(i).getRelaTypeId();
//            String relaTypeName = dictRelationList.get(i).getRelaTypeName();
//            List<DictionaryEntity.DictRelationListBean.DictRelationBean> dictRelationBeanList = dictRelationList.get(i).getDictRelation();
//            for (DictionaryEntity.DictRelationListBean.DictRelationBean dictRelationBean : dictRelationBeanList) {
//                ContentValues values = new ContentValues();
//                values.put("relaTypeId", relaTypeId);
//                values.put("relaTypeName", relaTypeName);
//                values.put("dictId", dictRelationBean.getDictId());
//                values.put("dictName", dictRelationBean.getDictName());
//                values.put("relaId", dictRelationBean.getRelaId());
//                values.put("relaName", dictRelationBean.getRelaName());
//                dbHelper.insert("DictionaryRel", values);
//            }
//        }
//    }
//
//    private void saveOrderDictionary(List<DictionaryEntity.OrderDictTypeIdListBean> orderDictTypeIdListBeanList) {
//        DBHelper dbHelper = DealerApp.dbHelper;
//        dbHelper.delete("OrderDictionary", null, null);
//        for (int i = 0; i < orderDictTypeIdListBeanList.size(); i++) {
//            String dictTypeId = orderDictTypeIdListBeanList.get(i).getDictTypeId();
//            String dictTypeName = orderDictTypeIdListBeanList.get(i).getDictTypeName();
//            List<DictionaryEntity.OrderDictTypeIdListBean.DictDataBean> dictDataBeanList = orderDictTypeIdListBeanList.get(i).getDictData();
//            for (DictionaryEntity.OrderDictTypeIdListBean.DictDataBean dictDataBean : dictDataBeanList) {
//                ContentValues values = new ContentValues();
//                values.put("dictTypeId", dictTypeId);
//                values.put("dictTypeName", dictTypeName);
//                values.put("dictId", dictDataBean.getDictId());
//                values.put("dictName", dictDataBean.getDictName());
//                dbHelper.insert("OrderDictionary", values);
//            }
//        }
//    }
//
//    private void saveCarSeriesModelDict(List<DictionaryEntity.DicCarSeriesAndModelBean> dicCarSeriesAndModelBeanList) {
//        DBHelper dbHelper = DealerApp.dbHelper;
//        dbHelper.delete("CarSeriesModelDict", null, null);
//        for (int i = 0; i < dicCarSeriesAndModelBeanList.size(); i++) {
//            String seriesNameCn = dicCarSeriesAndModelBeanList.get(i).getSeriesNameCn();
//            String seriesId = dicCarSeriesAndModelBeanList.get(i).getSeriesId();
//            List<DictionaryEntity.DicCarSeriesAndModelBean.CarModelInfoBean> carModelInfoBeanList = dicCarSeriesAndModelBeanList.get(i).getCarModelInfo();
//            for (DictionaryEntity.DicCarSeriesAndModelBean.CarModelInfoBean carModelInfoBean : carModelInfoBeanList) {
//                ContentValues values = new ContentValues();
//                values.put("seriesNameCn", seriesNameCn);
//                values.put("seriesId", seriesId);
//                values.put("modelDescCn", carModelInfoBean.getModelDescCn());
//                values.put("modelId", carModelInfoBean.getModelId());
//                dbHelper.insert("CarSeriesModelDict", values);
//            }
//        }
//    }
}
