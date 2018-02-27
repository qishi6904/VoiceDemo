package com.svw.dealerapp.ui.dictionary;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.svw.dealerapp.DealerApp;
import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.dictionary.DictionaryEntity;
import com.svw.dealerapp.global.Constants;
import com.svw.dealerapp.mvpframe.base.BaseObserver;
import com.svw.dealerapp.util.dbtools.DBHelper;
import com.svw.dealerapp.util.dbtools.DBUtils;

import java.lang.ref.WeakReference;
import java.util.List;

import rx.Observer;

/**
 * Created by lijinkui on 2017/5/15.
 */

public class DictionaryPresenter extends DictionaryContract.Presenter {

    private DealSaveHandler dealSaveHandler;

    public DictionaryPresenter(DictionaryContract.Model model, DictionaryContract.View view) {
        this.mModel = model;
        this.mView = view;
        dealSaveHandler = new DealSaveHandler(this);
    }

    private BaseObserver<ResEntity<DictionaryEntity>> getDictSubscription = new BaseObserver<ResEntity<DictionaryEntity>>() {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            if(!TextUtils.isEmpty(DealerApp.ACCESS_TOKEN)){
                super.onError(e);
            }
            e.printStackTrace();
            mView.getDictFail();
        }

        @Override
        public void onNext(final ResEntity<DictionaryEntity> dictionaryEntityResEntity) {
            if (dictionaryEntityResEntity.getRetCode().equals("200")) {
                new Thread(){
                    @Override
                    public void run() {
                        DBHelper dbHelper = new DBHelper(DealerApp.getContext());
                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                        try {
                            DictionaryEntity dictionaryEntity = dictionaryEntityResEntity.getRetData();
                            if (dictionaryEntity != null && dictionaryEntity.getDictTypeIdList() != null && dictionaryEntity.getDictTypeIdList().size() > 0) {
                                saveDictionary(dictionaryEntity, dbHelper, db);
                            }
                            if (dictionaryEntity != null && dictionaryEntity.getDictRelationList() != null && dictionaryEntity.getDictRelationList().size() > 0) {
                                saveDictionaryRel(dictionaryEntity.getDictRelationList(), dbHelper, db);
                            }
                            if (dictionaryEntity != null && dictionaryEntity.getDicCarSeriesAndModel() != null && dictionaryEntity.getDicCarSeriesAndModel().size() > 0) {
                                saveCarSeriesModelDict(dictionaryEntity.getDicCarSeriesAndModel(), dbHelper, db);
                            }
                            if (dictionaryEntity != null && dictionaryEntity.getOrderDictTypeIdList() != null && dictionaryEntity.getOrderDictTypeIdList().size() > 0) {
                                saveOrderDictionary(dictionaryEntity.getOrderDictTypeIdList(), dbHelper, db);
                            }
                            if (dictionaryEntity != null && dictionaryEntity.getOpportunityStateList() != null && dictionaryEntity.getOpportunityStateList().size() > 0) {
                                saveOpportunityStateDict(dictionaryEntity.getOpportunityStateList(), dbHelper, db);
                            }
                            if (dictionaryEntity != null && dictionaryEntity.getCompetitorsSeriesList() != null && !dictionaryEntity.getCompetitorsSeriesList().isEmpty()) {
                                saveCompetitorsSeriesDict(dictionaryEntity.getCompetitorsSeriesList(), dbHelper, db);
                            }
                            DBUtils.setOptionsFromDB();
                            DBUtils.setOrderOptionsFromDB();
                            DBUtils.setCarSeriesFromDB();
                            DBUtils.getMapFromDictionaryRel();
                        }catch (Exception e) {
                            e.printStackTrace();
                        }finally {
                            db.close();
                        }
                    }
                }.start();
                dealSaveHandler.sendEmptyMessage(0);
            } else if (dictionaryEntityResEntity.getRetCode().equals("401") || dictionaryEntityResEntity.getRetCode().equals("404")) {
                mView.getDictFail();
            }
        }
    };


    @Override
    public void getDictionary(String version) {
        mRxManager.add(mModel.getDictionary(version).subscribe(getDictSubscription));
    }

    private void saveDictionary(DictionaryEntity dictionaryEntity, DBHelper dbHelper, SQLiteDatabase db) {
        List<DictionaryEntity.DictTypeIdListBean> dictList;
        String version = dictionaryEntity.getVersion();
        if (dictionaryEntity.getDictTypeIdList().size() > 0) {
            dictList = dictionaryEntity.getDictTypeIdList();
            dbHelper.delete("Dictionary", null, null);
            try {
                db.beginTransaction();
                for (int i = 0; i < dictList.size(); i++) {
                    String dictTypeId = dictList.get(i).getDictTypeId();
                    String dictTypeName = dictList.get(i).getDictTypeName();
                    List<DictionaryEntity.DictTypeIdListBean.DictDataBean> dictDataBeanList = dictList.get(i).getDictData();
                    if (null != dictDataBeanList) {
                        for (DictionaryEntity.DictTypeIdListBean.DictDataBean dictDataBean : dictDataBeanList) {
                            ContentValues values = new ContentValues();
                            values.put("dictTypeId", dictTypeId);
                            values.put("dictTypeName", dictTypeName);
                            values.put("dictId", dictDataBean.getDictId());
                            values.put("dictName", dictDataBean.getDictName());
                            values.put("version", version);
                            db.insert("Dictionary", null, values);
                        }
                    }
                }
                db.setTransactionSuccessful();
            }catch (Exception e) {
                e.printStackTrace();
            }finally {
                db.endTransaction();
            }
            //test db
//            Cursor cursor = dbHelper.rawQuery("select * from Dictionary",null);
//            if(cursor !=null){
//                if(cursor.moveToNext()){
//                    String content_id = cursor.getString(3);
//                }
//                cursor.close();
//            }
        }
    }

    private void saveDictionaryRel(List<DictionaryEntity.DictRelationListBean> dictRelationList, DBHelper dbHelper, SQLiteDatabase db) {
        dbHelper.delete("DictionaryRel", null, null);
        try {
            db.beginTransaction();
            for (int i = 0; i < dictRelationList.size(); i++) {
                String relaTypeId = dictRelationList.get(i).getRelaTypeId();
                String relaTypeName = dictRelationList.get(i).getRelaTypeName();
                List<DictionaryEntity.DictRelationListBean.DictRelationBean> dictRelationBeanList = dictRelationList.get(i).getDictRelation();
                if (null != dictRelationBeanList) {
                    for (DictionaryEntity.DictRelationListBean.DictRelationBean dictRelationBean : dictRelationBeanList) {
                        ContentValues values = new ContentValues();
                        values.put("relaTypeId", relaTypeId);
                        values.put("relaTypeName", relaTypeName);
                        values.put("dictId", dictRelationBean.getDictId());
                        values.put("dictName", dictRelationBean.getDictName());
                        values.put("relaId", dictRelationBean.getRelaId());
                        values.put("relaName", dictRelationBean.getRelaName());
                        db.insert("DictionaryRel", null, values);
                    }
                }
            }
            db.setTransactionSuccessful();
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }
    }

    private void saveOrderDictionary(List<DictionaryEntity.OrderDictTypeIdListBean> orderDictTypeIdListBeanList, DBHelper dbHelper, SQLiteDatabase db) {
        dbHelper.delete("OrderDictionary", null, null);
        try {
            db.beginTransaction();
            for (int i = 0; i < orderDictTypeIdListBeanList.size(); i++) {
                String dictTypeId = orderDictTypeIdListBeanList.get(i).getDictTypeId();
                String dictTypeName = orderDictTypeIdListBeanList.get(i).getDictTypeName();
                List<DictionaryEntity.OrderDictTypeIdListBean.DictDataBean> dictDataBeanList = orderDictTypeIdListBeanList.get(i).getDictData();
                if (null != dictDataBeanList) {
                    for (DictionaryEntity.OrderDictTypeIdListBean.DictDataBean dictDataBean : dictDataBeanList) {
                        ContentValues values = new ContentValues();
                        values.put("dictTypeId", dictTypeId);
                        values.put("dictTypeName", dictTypeName);
                        values.put("dictId", dictDataBean.getDictId());
                        values.put("dictName", dictDataBean.getDictName());
                        db.insert("OrderDictionary", null, values);
                    }
                }
            }
            db.setTransactionSuccessful();
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }
    }

    private void saveCarSeriesModelDict(List<DictionaryEntity.DicCarSeriesAndModelBean> dicCarSeriesAndModelBeanList, DBHelper dbHelper, SQLiteDatabase db) {
        dbHelper.delete("CarSeriesModelDict", null, null);
        try {
            db.beginTransaction();
            for (int i = 0; i < dicCarSeriesAndModelBeanList.size(); i++) {
                String seriesNameCn = dicCarSeriesAndModelBeanList.get(i).getSeriesNameCn();
                String seriesId = dicCarSeriesAndModelBeanList.get(i).getSeriesId();
                List<DictionaryEntity.DicCarSeriesAndModelBean.CarModelInfoBean> carModelInfoBeanList = dicCarSeriesAndModelBeanList.get(i).getCarModelInfo();
                if (null != carModelInfoBeanList) {
                    for (DictionaryEntity.DicCarSeriesAndModelBean.CarModelInfoBean carModelInfoBean : carModelInfoBeanList) {
                        ContentValues values = new ContentValues();
                        values.put("seriesNameCn", seriesNameCn);
                        values.put("seriesId", seriesId);
                        values.put("modelDescCn", carModelInfoBean.getModelDescCn());
                        values.put("modelId", carModelInfoBean.getModelId());
                        dbHelper.insert("CarSeriesModelDict", values);
                        db.insert("CarSeriesModelDict", null, values);
                    }
                }
            }
            db.setTransactionSuccessful();
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }
    }

    private void saveOpportunityStateDict(List<DictionaryEntity.OpportunityStateListBean> opportunityStateListBeanList, DBHelper dbHelper, SQLiteDatabase db) {
        dbHelper.delete("OpportunityStateDict", null, null);
        try {
            db.beginTransaction();
            for (DictionaryEntity.OpportunityStateListBean opportunityStateListBean : opportunityStateListBeanList) {
                ContentValues values = new ContentValues();
                values.put("contactMethodDict", opportunityStateListBean.getContactMethodDict());
                values.put("stateDictOld", opportunityStateListBean.getStateDictOld());
                values.put("resultDict", opportunityStateListBean.getResultDict());
                values.put("resultDictName", opportunityStateListBean.getResultDictName());
                values.put("stateWeight", Integer.parseInt(opportunityStateListBean.getStateWeight()));
                db.insert("OpportunityStateDict", null, values);
            }
            db.setTransactionSuccessful();
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }
    }

    private void saveCompetitorsSeriesDict(List<DictionaryEntity.CompetitorsSeriesListBean> list, DBHelper dbHelper, SQLiteDatabase db) {
        dbHelper.delete("CompetitorsSeriesDict", null, null);
        try {
            db.beginTransaction();
            for (DictionaryEntity.CompetitorsSeriesListBean competitorsSeriesListBean : list) {
                List<DictionaryEntity.CompetitorsSeriesListBean.CompetitorVOListBean> voList = competitorsSeriesListBean.getCompetitorVOList();
                if (null != voList) {
                    for (DictionaryEntity.CompetitorsSeriesListBean.CompetitorVOListBean bean : voList) {
                        ContentValues values = new ContentValues();
                        values.put("seriesNameEN", competitorsSeriesListBean.getSeriesNameEN());
                        values.put("seriesId", competitorsSeriesListBean.getSeriesId());
                        values.put("competitorId", bean.getCompetitorId());
                        values.put("competitorName", bean.getCompetitorName());
                        db.insert("CompetitorsSeriesDict", null, values);
                    }
                }
            }
            db.setTransactionSuccessful();
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }
    }

    private class DealSaveHandler extends Handler {
        WeakReference<DictionaryPresenter> weakReference;

        DealSaveHandler(DictionaryPresenter weakReference) {
            this.weakReference = new WeakReference<>(weakReference);
        }

        @Override
        public void handleMessage(Message msg) {
            mView.getDictSuccess();
        }
    }

}
