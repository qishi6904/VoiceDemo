package com.svw.dealerapp.util.dbtools;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.svw.dealerapp.util.JLog;

import java.util.List;

/**
 * 数据库Helper类
 * Created by xupan on 2017/05/09.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final String TAG = "DBHelper";
    private static final int DB_VERSION = 5;
    private static final String DB_NAME = "dealerapp.db";
    public static final String TABLE_USER_NAME = "User";
    public static final String TABLE_DICTIONARY_NAME = "Dictionary";
    public static final String TABLE_DICTIONARY_REL_NAME = "DictionaryRel";
    public static final String TABLE_ORDER_DICTIONARY_NAME = "OrderDictionary";
    public static final String TABLE_CAR_SERIES_MODEL_DICT_NAME = "CarSeriesModelDict";
    public static final String TABLE_OPPORTUNITY_STATE_DICT_NAME = "OpportunityStateDict";
    public static final String TABLE_COMPETITORS_SERIES_DICT_NAME = "CompetitorsSeriesDict";
    public static final String TABLE_ENV_NAME = "Env";
    public static final String TABLE_SM_TOKEN_NAME = "SMToken";
    public static final String TABLE_SM_USER_NAME = "SMUser";
    public static final String TABLE_SM_USER_PRIVILEGE_NAME = "SMUserPrivilege";

    //create db table
    public String create_sql_user = "CREATE TABLE IF NOT EXISTS " + TABLE_USER_NAME +
            " (id INTEGER PRIMARY KEY, accessToken TEXT, tokenType TEXT, refreshToken TEXT, expiresIn TEXT, scope TEXT, roleIds TEXT, userId TEXT, orgId TEXT, name TEXT, gender TEXT, image TEXT, dutyId TEXT, mobile TEXT, telephone TEXT, wechat TEXT, email TEXT, status TEXT, createUser TEXT, createTime TEXT, updateTime TEXT, remark TEXT, orgName TEXT, orgAddress TEXT, orgProvinceId TEXT, orgCityId TEXT, webToken TEXT)";
    public String create_sql_dictionary = "CREATE TABLE IF NOT EXISTS " + TABLE_DICTIONARY_NAME +
            " (id INTEGER PRIMARY KEY, dictTypeId TEXT, dictTypeName TEXT, dictId TEXT, dictName TEXT, version TEXT)";
    public String create_sql_dictionary_rel = "CREATE TABLE IF NOT EXISTS " + TABLE_DICTIONARY_REL_NAME +
            " (id INTEGER PRIMARY KEY, relaTypeId TEXT, relaTypeName TEXT, dictId TEXT, dictName TEXT, relaId TEXT, relaName TEXT)";
    public String create_sql_dictionary_car_series_model = "CREATE TABLE IF NOT EXISTS " + TABLE_CAR_SERIES_MODEL_DICT_NAME +
            " (id INTEGER PRIMARY KEY, seriesNameCn TEXT, seriesId TEXT, modelDescCn TEXT, modelId TEXT)";
    public String create_sql_dictionary_order = "CREATE TABLE IF NOT EXISTS " + TABLE_ORDER_DICTIONARY_NAME +
            " (id INTEGER PRIMARY KEY, dictTypeId TEXT, dictTypeName TEXT, dictId TEXT, dictName TEXT)";
    public String create_sql_dictionary_opportunity_state = "CREATE TABLE IF NOT EXISTS " + TABLE_OPPORTUNITY_STATE_DICT_NAME +
            " (id INTEGER PRIMARY KEY, contactMethodDict TEXT, stateDictOld TEXT, resultDict TEXT, resultDictName TEXT, stateWeight INTEGER)";
    public String create_sql_dictionary_competitors_series = "CREATE TABLE IF NOT EXISTS " + TABLE_COMPETITORS_SERIES_DICT_NAME +
            " (id INTEGER PRIMARY KEY, seriesNameEN TEXT, seriesId TEXT, competitorId TEXT, competitorName TEXT)";
    public String create_sql_env = "CREATE TABLE IF NOT EXISTS " + TABLE_ENV_NAME +
            " (id INTEGER PRIMARY KEY, name TEXT, status TEXT)";
    public String create_sql_sm_token = "CREATE TABLE IF NOT EXISTS " + TABLE_SM_TOKEN_NAME +
            " (id INTEGER PRIMARY KEY, accessToken TEXT, expireAt TEXT, refreshToken TEXT, refreshExpireAt TEXT, accountId TEXT, clientId TEXT, scope TEXT, source TEXT, accessSubToken TEXT)";
    public String create_sql_sm_user = "CREATE TABLE IF NOT EXISTS " + TABLE_SM_USER_NAME +
            " (id INTEGER PRIMARY KEY, accountId TEXT, account TEXT, displayName TEXT, name TEXT, status TEXT, email TEXT, mobile TEXT, avatarUrl TEXT, currentCategory TEXT, currentRootCategory TEXT, activate TEXT, districtId TEXT, orgId TEXT, orgName TEXT, depId TEXT, depName TEXT, shopId TEXT)";
    public String create_sql_sm_user_privilege = "CREATE TABLE IF NOT EXISTS " + TABLE_SM_USER_PRIVILEGE_NAME +
            " (id INTEGER PRIMARY KEY, " + "accountId TEXT, " + "uri TEXT, " + "isCheckUri TEXT, " + "resourceId TEXT, " + "isCheckResource TEXT)";

    //drop db table
    public String drop_sql_user = "DROP TABLE IF EXISTS " + TABLE_USER_NAME;
    public String drop_sql_dictionary = "DROP TABLE IF EXISTS " + TABLE_DICTIONARY_NAME;
    public String drop_sql_dictionary_rel = "DROP TABLE IF EXISTS " + TABLE_DICTIONARY_NAME;
    public String drop_sql_dictionary_car_series_model = "DROP TABLE IF EXISTS " + TABLE_CAR_SERIES_MODEL_DICT_NAME;
    public String drop_sql_dictionary_order = "DROP TABLE IF EXISTS " + TABLE_ORDER_DICTIONARY_NAME;
    public String drop_sql_dictionary_opportunity_state = "DROP TABLE IF EXISTS " + TABLE_OPPORTUNITY_STATE_DICT_NAME;
    public String drop_sql_dictionary_competitors_series = "DROP TABLE IF EXISTS " + TABLE_COMPETITORS_SERIES_DICT_NAME;
    public String drop_sql_env = "DROP TABLE IF EXISTS " + TABLE_ENV_NAME;
    public String drop_sql_sm_token = "DROP TABLE IF EXISTS " + TABLE_SM_TOKEN_NAME;
    public String drop_sql_sm_user = "DROP TABLE IF EXISTS " + TABLE_SM_USER_NAME;
    public String drop_sql_sm_user_privilege = "DROP TABLE IF EXISTS " + TABLE_SM_USER_PRIVILEGE_NAME;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(create_sql_user);
        db.execSQL(create_sql_dictionary);
        db.execSQL(create_sql_dictionary_rel);
        db.execSQL(create_sql_dictionary_order);
        db.execSQL(create_sql_dictionary_car_series_model);
        db.execSQL(create_sql_dictionary_opportunity_state);
        db.execSQL(create_sql_dictionary_competitors_series);
        db.execSQL(create_sql_env);
        db.execSQL(create_sql_sm_token);
        db.execSQL(create_sql_sm_user);
        db.execSQL(create_sql_sm_user_privilege);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(drop_sql_user);
        db.execSQL(drop_sql_dictionary);
        db.execSQL(drop_sql_dictionary_rel);
        db.execSQL(drop_sql_dictionary_order);
        db.execSQL(drop_sql_dictionary_car_series_model);
        db.execSQL(drop_sql_dictionary_opportunity_state);
        db.execSQL(drop_sql_dictionary_competitors_series);
        db.execSQL(drop_sql_env);
        db.execSQL(drop_sql_sm_token);
        db.execSQL(drop_sql_sm_user);
        db.execSQL(drop_sql_sm_user_privilege);
        onCreate(db);
    }

    public void createSingleTable(SQLiteDatabase db, String sqlName){
        db.execSQL(sqlName);
    }

    public void dropSingleTable(SQLiteDatabase db, String sqlName){
        db.execSQL(sqlName);
    }

    /**
     * @return result of insertion.
     */
    public boolean insert(String tableName, ContentValues contentValues) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        long result = -1;
        try {
            result = db.insert(tableName, null, contentValues);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        JLog.d(TAG, "insert result: " + result);
        return result != -1;
    }

    /**
     * @return result of insertion.
     */
    public boolean insert(String tableName, List<ContentValues> contentValuesList) {
        if (contentValuesList == null || contentValuesList.isEmpty()) {
            return false;
        }
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        long result = -1;
        try {
            for (ContentValues contentValues : contentValuesList) {
                result = db.insert(tableName, null, contentValues);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        JLog.d(TAG, "insert result: " + (result != -1));
        return result != -1;
    }

    /**
     * @return rows affected.
     */
    public int update(String table, ContentValues values, String whereClause, String[] whereArgs) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        int result = -1;
        try {
            result = db.update(table, values, whereClause, whereArgs);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        return result;
    }

    /**
     * @return rows affected.
     */
    public int delete(String table, String whereClause, String[] whereArgs) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        int result = -1;
        try {
            result = db.delete(table, whereClause, whereArgs);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        return result;
    }

    public Cursor rawQuery(String sql, String[] selectionArgs) {
        SQLiteDatabase db = getReadableDatabase();
        db.beginTransaction();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(sql, selectionArgs);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        return cursor;
    }

}