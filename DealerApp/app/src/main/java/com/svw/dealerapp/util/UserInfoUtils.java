package com.svw.dealerapp.util;

import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;

import com.svw.dealerapp.BuildConfig;
import com.svw.dealerapp.DealerApp;
import com.svw.dealerapp.util.dbtools.DBHelper;


/**
 * Created by qinshi on 6/21/2017.
 */

public class UserInfoUtils {

    /**
     * 获取用户Id
     *
     * @return
     */
    public static String getUserId() {
        String userId = "";
        DBHelper dbHelper = new DBHelper(DealerApp.getContext());
        Cursor cursor = dbHelper.rawQuery("select * from " + DBHelper.TABLE_SM_USER_NAME, null);
        if (cursor.moveToFirst()) {
            userId = cursor.getString(cursor.getColumnIndex("mobile"));
            userId = AesUtils.decrypt(BuildConfig.AES_SECRET_KEY, userId);
        }
        cursor.close();
        return userId;
    }

    /**
     * 获取当前用户名
     * @return
     */
    public static String getUserName(){
        String userName = "";
        DBHelper dbHelper = new DBHelper(DealerApp.getContext());
        Cursor cursor = dbHelper.rawQuery("select * from " + DBHelper.TABLE_SM_USER_NAME, null);
        if (cursor.moveToFirst()) {
            userName = cursor.getString(cursor.getColumnIndex("name"));
            userName = AesUtils.decrypt(BuildConfig.AES_SECRET_KEY, userName);
        }
        cursor.close();
        return userName;
    }

    /**
     * 获取用户角色Id
     *
     * @return
     */
    public static String getUserRolesId() {
        String userRolesId = "";
        DBHelper dbHelper = new DBHelper(DealerApp.getContext());
        Cursor cursor = dbHelper.rawQuery("select * from " + DBHelper.TABLE_SM_USER_NAME, null);
        if (cursor.moveToFirst()) {
            userRolesId = cursor.getString(cursor.getColumnIndex("roleIds"));
        }
        cursor.close();
        return userRolesId;
    }

//    /**
//     * 是否是经理权限
//     *
//     * @return
//     */
//    public static boolean isManagerPermission() {
//        try {
//            String rolesIdStr = getUserRolesId();
//            if (!TextUtils.isEmpty(rolesIdStr)) {
//                String[] rolesIds = rolesIdStr.split(",");
//                if (null != rolesIds && rolesIds.length > 0) {
//                    for (int i = 0; i < rolesIds.length; i++) {
//                        if (rolesIds[i].startsWith("2") || rolesIds[i].startsWith("4")) {
//                            return true;
//                        }
//                    }
//                }
//            }
//            return false;
//        }catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }

    /**
     * 获取当前用户的orgId
     * @return
     */
    public static String getOrgId() {
        String orgId = "";
        DBHelper dbHelper = new DBHelper(DealerApp.getContext());
        Cursor cursor = dbHelper.rawQuery("select * from " + DBHelper.TABLE_SM_USER_NAME, null);
        if (cursor.moveToFirst()) {
            orgId = cursor.getString(cursor.getColumnIndex("orgId"));
            orgId = AesUtils.decrypt(BuildConfig.AES_SECRET_KEY, orgId);
        }
        cursor.close();
        return orgId;
    }

    /**
     * 获取用户头像url
     * @return
     */
    public static String getUserHeaderUrl(){
        String url = null;
        DBHelper dbHelper = DealerApp.dbHelper;
        Cursor cursor = dbHelper.rawQuery("select image from User", null);
        if (cursor != null) {
            if (cursor.moveToNext()) {
                url = AesUtils.decrypt(BuildConfig.AES_SECRET_KEY, cursor.getString(cursor.getColumnIndex("image")));
            }
            cursor.close();
        }
        return url;
    }

    /**
     * 获取用户组织名称
     * @return
     */
    public static String getUserOrg(){
        String org = null;
        DBHelper dbHelper = DealerApp.dbHelper;
        Cursor cursor = dbHelper.rawQuery("select orgName from User", null);
        if (cursor != null) {
            if (cursor.moveToNext()) {
                org = AesUtils.decrypt(BuildConfig.AES_SECRET_KEY, cursor.getString(cursor.getColumnIndex("orgName")));
            }
            cursor.close();
        }
        return org;
    }
}
