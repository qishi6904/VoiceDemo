package com.svw.dealerapp.util.dbtools;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.svw.dealerapp.DealerApp;
import com.svw.dealerapp.entity.login.SMUserPrivilegeByAppIdEntity;
import com.svw.dealerapp.entity.login.SMUserPrivilegeDBEntity;
import com.svw.dealerapp.util.UserInfoUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qinshi on 1/26/2018.
 */

public class PrivilegeDBUtils {

    public static final String FIELD_ACCOUNT_ID = "accountId";
    public static final String FIELD_URI = "uri";
    public static final String FIELD_IS_CHECK_URI = "isCheckUri";
    public static final String FIELD_RESOURCE_ID = "resourceId";
    public static final String FIELD_IS_CHECK_RESOURCE_ID = "isCheckResource";

    private static final String TABLE_SM_USER_PRIVILEGE_NAME = "SMUserPrivilege";

    /**
     * 清表
     */
    public static void clearPrivilegeTable() {
        DBHelper dbHelper = new DBHelper(DealerApp.getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        dropTable(db);
//        createTable(db);
        dbHelper.dropSingleTable(db, dbHelper.drop_sql_sm_user_privilege);
        dbHelper.createSingleTable(db, dbHelper.create_sql_sm_user_privilege);
        db.close();
    }

//    public static void createTable(SQLiteDatabase db){
//        String sql_sm_user_privilege = "CREATE TABLE IF NOT EXISTS " + TABLE_SM_USER_PRIVILEGE_NAME +
//                " (id INTEGER PRIMARY KEY, " + FIELD_ACCOUNT_ID + " TEXT, " + FIELD_NAME + " TEXT, " + FIELD_IS_CHECK_NAME +
//                " TEXT, " + FIELD_APP_ID + " TEXT, " + FIELD_IS_CHECK_APP_ID + " TEXT, " + FIELD_URI +
//                " TEXT, " + FIELD_IS_CHECK_URI + " TEXT, " + FIELD_RESOURCE_ID + " TEXT, " + FIELD_IS_CHECK_RESOURCE_ID + " TEXT)";
//        db.execSQL(sql_sm_user_privilege);
//    }
//
//    public static void dropTable(SQLiteDatabase db){
//        String sql_sm_user_privilege = "DROP TABLE IF EXISTS " + TABLE_SM_USER_PRIVILEGE_NAME;
//        db.execSQL(sql_sm_user_privilege);
//    }

    /**
     * 存储数据
     *
     * @param originalList
     * @return
     */
    public static boolean savePrivilegeData(List<SMUserPrivilegeByAppIdEntity> originalList) {
        List<SMUserPrivilegeDBEntity> dataList = transformData(originalList);
        DBHelper dbHelper = new DBHelper(DealerApp.getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            for (int i = 0; i < dataList.size(); i++) {
                ContentValues values = new ContentValues();
                values.put("accountId", dataList.get(i).getAccountId());
                values.put("uri", dataList.get(i).getUri());
                values.put("isCheckUri", dataList.get(i).getIsCheckUri());
                values.put("resourceId", dataList.get(i).getResourceId());
                values.put("isCheckResource", dataList.get(i).getIsCheckResource());
                db.insert(TABLE_SM_USER_PRIVILEGE_NAME, null, values);
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            db.endTransaction();
            db.close();
        }
        return true;
    }

    /**
     * 数据转换，拉平数据
     *
     * @param privilegeList
     * @return
     */
    private static List<SMUserPrivilegeDBEntity> transformData(List<SMUserPrivilegeByAppIdEntity> privilegeList) {
        List<SMUserPrivilegeDBEntity> dbEntities = new ArrayList<>();
        if (null != privilegeList && privilegeList.size() > 0) {
            String userId = UserInfoUtils.getUserId();
            for (int i = 0; i < privilegeList.size(); i++) {

                SMUserPrivilegeByAppIdEntity.SMUserPrivilegeNodeEntity privilegePageNode = privilegeList.get(i).getNode();

                String uri = privilegePageNode.getUri();
                String isCheckUri = String.valueOf((privilegePageNode.getIsCheck()));

                List<SMUserPrivilegeByAppIdEntity> privilegePageData = privilegeList.get(i).getData();
                if (null != privilegePageData && privilegePageData.size() > 0) {
                    for (int j = 0; j < privilegePageData.size(); j++) {
                        SMUserPrivilegeByAppIdEntity.SMUserPrivilegeNodeEntity privilegeRSNode = privilegePageData.get(j).getNode();
                        SMUserPrivilegeDBEntity entity = new SMUserPrivilegeDBEntity();
                        entity.setAccountId(userId);
                        entity.setUri(uri);
                        entity.setIsCheckUri(isCheckUri);
                        entity.setResourceId(privilegeRSNode.getResourceId());
                        entity.setIsCheckResource(String.valueOf((privilegeRSNode.getIsCheck())));
                        dbEntities.add(entity);
                    }
                } else {
                    SMUserPrivilegeDBEntity entity = new SMUserPrivilegeDBEntity();
                    entity.setAccountId(userId);
                    entity.setUri(uri);
                    entity.setIsCheckUri(isCheckUri);
                    dbEntities.add(entity);
                }
            }
        }
        return dbEntities;
    }

    /**
     * 从DB中查询数据
     *
     * @param columns
     * @param selection
     * @param selectionArgs
     * @return
     */
    public static List<SMUserPrivilegeDBEntity> getSMUserPrivilegeDataFromDB(String[] columns, String selection, String[] selectionArgs) {
        DBHelper dbHelper = new DBHelper(DealerApp.getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        List<SMUserPrivilegeDBEntity> dataList = new ArrayList<>();

        Cursor cursor = db.query(TABLE_SM_USER_PRIVILEGE_NAME, null, selection, selectionArgs, null, null, null);
        while (cursor.moveToNext()) {
            SMUserPrivilegeDBEntity entity = new SMUserPrivilegeDBEntity();
            entity.setAccountId(cursor.getString(cursor.getColumnIndex(FIELD_ACCOUNT_ID)));
            entity.setUri(cursor.getString(cursor.getColumnIndex(FIELD_URI)));
            entity.setIsCheckUri(cursor.getString(cursor.getColumnIndex(FIELD_IS_CHECK_URI)));
            entity.setResourceId(cursor.getString(cursor.getColumnIndex(FIELD_RESOURCE_ID)));
            entity.setIsCheckResource(cursor.getString(cursor.getColumnIndex(FIELD_IS_CHECK_RESOURCE_ID)));
            dataList.add(entity);
        }

        cursor.close();
        db.close();
        return dataList;
    }

    /**
     * 判断某个页面或元素是否有权限
     *
     * @param pageId
     * @param resourceId
     * @return
     */
    public static boolean isCheck(String pageId, String resourceId) {
        DBHelper helper = new DBHelper(DealerApp.getContext());
        Cursor cursor = null;
        if(!TextUtils.isEmpty(resourceId)) {
            cursor = helper.rawQuery("select isCheckUri, isCheckResource from SMUserPrivilege where uri=? and resourceId=?", new String[]{pageId, resourceId});
        }else {
            cursor = helper.rawQuery("select isCheckUri, isCheckResource from SMUserPrivilege where uri=?", new String[]{pageId});
        }
        if (null != cursor && cursor.moveToNext()) {
            String isCheckUri = cursor.getString(cursor.getColumnIndex("isCheckUri"));
            String isCheckResource = cursor.getString(cursor.getColumnIndex("isCheckResource"));
            if(!TextUtils.isEmpty(resourceId)) {
                return "true".equals(isCheckUri) && "true".equals(isCheckResource);
            }else {
                return "true".equals(isCheckUri);
            }
        }
        cursor.close();
        helper.close();
        return false;
    }
}
