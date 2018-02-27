package com.svw.dealerapp.util.dbtools;

import android.database.Cursor;
import android.text.TextUtils;

import com.svw.dealerapp.DealerApp;
import com.svw.dealerapp.global.NewCustomerConstants;
import com.svw.dealerapp.global.NewOrderConstants;
import com.svw.dealerapp.util.dbtools.DBHelper;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by xupan on 08/08/2017.
 */

public class DBUtils {

    public static void setOptionsFromDB() {
        DBHelper dbHelper = DealerApp.dbHelper;
        Cursor cursor = dbHelper.rawQuery("select * from Dictionary", null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                if ("100".equals(cursor.getString(cursor.getColumnIndex("dictTypeId")))) {
                    NewCustomerConstants.ageMap.put(cursor.getString(cursor.getColumnIndex("dictId")), cursor.getString(cursor.getColumnIndex("dictName")));
                } else if ("110".equals(cursor.getString(cursor.getColumnIndex("dictTypeId")))) {
                    NewCustomerConstants.sourceMap.put(cursor.getString(cursor.getColumnIndex("dictId")), cursor.getString(cursor.getColumnIndex("dictName")));
                } else if ("130".equals(cursor.getString(cursor.getColumnIndex("dictTypeId")))) {
                    NewCustomerConstants.purposeMap.put(cursor.getString(cursor.getColumnIndex("dictId")), cursor.getString(cursor.getColumnIndex("dictName")));
                } else if ("135".equals(cursor.getString(cursor.getColumnIndex("dictTypeId")))) {
                    NewCustomerConstants.propertyMap.put(cursor.getString(cursor.getColumnIndex("dictId")), cursor.getString(cursor.getColumnIndex("dictName")));
                } else if ("120".equals(cursor.getString(cursor.getColumnIndex("dictTypeId")))) {
                    NewCustomerConstants.levelMap.put(cursor.getString(cursor.getColumnIndex("dictId")), cursor.getString(cursor.getColumnIndex("dictName")));
                } else if ("140".equals(cursor.getString(cursor.getColumnIndex("dictTypeId")))) {
                    NewCustomerConstants.requirementsMap.put(cursor.getString(cursor.getColumnIndex("dictId")), cursor.getString(cursor.getColumnIndex("dictName")));
                } else if ("145".equals(cursor.getString(cursor.getColumnIndex("dictTypeId")))) {
                    NewCustomerConstants.methodsMap.put(cursor.getString(cursor.getColumnIndex("dictId")), cursor.getString(cursor.getColumnIndex("dictName")));
                } else if ("115".equals(cursor.getString(cursor.getColumnIndex("dictTypeId")))) {
                    NewCustomerConstants.ycStatusMap.put(cursor.getString(cursor.getColumnIndex("dictId")), cursor.getString(cursor.getColumnIndex("dictName")));
                } else if ("125".equals(cursor.getString(cursor.getColumnIndex("dictTypeId")))) {
                    NewCustomerConstants.payMethodMap.put(cursor.getString(cursor.getColumnIndex("dictId")), cursor.getString(cursor.getColumnIndex("dictName")));
                } else if ("150".equals(cursor.getString(cursor.getColumnIndex("dictTypeId")))) {
                    NewCustomerConstants.contactMethodMap.put(cursor.getString(cursor.getColumnIndex("dictId")), cursor.getString(cursor.getColumnIndex("dictName")));
                } else if ("155".equals(cursor.getString(cursor.getColumnIndex("dictTypeId")))) {
                    NewCustomerConstants.contactResultMultiChoicesMap.put(cursor.getString(cursor.getColumnIndex("dictId")), cursor.getString(cursor.getColumnIndex("dictName")));
                } else if ("170".equals(cursor.getString(cursor.getColumnIndex("dictTypeId")))) {
                    NewCustomerConstants.appointmentTypeMap.put(cursor.getString(cursor.getColumnIndex("dictId")), cursor.getString(cursor.getColumnIndex("dictName")));
                } else if ("185".equals(cursor.getString(cursor.getColumnIndex("dictTypeId")))) {
                    NewCustomerConstants.approvalStatusMap.put(cursor.getString(cursor.getColumnIndex("dictId")), cursor.getString(cursor.getColumnIndex("dictName")));
                } else if ("105".equals(cursor.getString(cursor.getColumnIndex("dictTypeId")))) {
                    NewCustomerConstants.trafficStatusMap.put(cursor.getString(cursor.getColumnIndex("dictId")), cursor.getString(cursor.getColumnIndex("dictName")));
                } else if ("200".equals(cursor.getString(cursor.getColumnIndex("dictTypeId")))) {
                    NewCustomerConstants.followupMethodMap.put(cursor.getString(cursor.getColumnIndex("dictId")), cursor.getString(cursor.getColumnIndex("dictName")));
                }
            }
            cursor.close();
        }
    }

    public static void setOrderOptionsFromDB() {
        DBHelper dbHelper = DealerApp.dbHelper;
        Cursor cursor = dbHelper.rawQuery("select * from OrderDictionary", null);
        while (cursor != null && cursor.moveToNext()) {
            if ("110".equals(cursor.getString(cursor.getColumnIndex("dictTypeId")))) {
                NewOrderConstants.idTypeMap.put(cursor.getString(cursor.getColumnIndex("dictId")), cursor.getString(cursor.getColumnIndex("dictName")));
            } else if ("105".equals(cursor.getString(cursor.getColumnIndex("dictTypeId")))) {
                NewOrderConstants.customerTypeMap.put(cursor.getString(cursor.getColumnIndex("dictId")), cursor.getString(cursor.getColumnIndex("dictName")));
            } else if ("120".equals(cursor.getString(cursor.getColumnIndex("dictTypeId")))) {
                NewOrderConstants.industryMap.put(cursor.getString(cursor.getColumnIndex("dictId")), cursor.getString(cursor.getColumnIndex("dictName")));
            } else if ("115".equals(cursor.getString(cursor.getColumnIndex("dictTypeId")))) {
                NewOrderConstants.occupationMap.put(cursor.getString(cursor.getColumnIndex("dictId")), cursor.getString(cursor.getColumnIndex("dictName")));
            } else if ("125".equals(cursor.getString(cursor.getColumnIndex("dictTypeId")))) {
                NewOrderConstants.companyPropertyMap.put(cursor.getString(cursor.getColumnIndex("dictId")), cursor.getString(cursor.getColumnIndex("dictName")));
            }
        }
        if (cursor != null) cursor.close();
    }

    public static void setCarSeriesFromDB() {
        DBHelper dbHelper = DealerApp.dbHelper;
        Cursor cursor = dbHelper.rawQuery("select distinct seriesNameCn, seriesId from CarSeriesModelDict", null);
        while (cursor != null && cursor.moveToNext()) {
            NewCustomerConstants.carSeriesMap.put(cursor.getString(cursor.getColumnIndex("seriesId")), cursor.getString(cursor.getColumnIndex("seriesNameCn")));
        }
        if (cursor != null) cursor.close();
    }

    /**
     * 根据联络方式和潜客状态，获取跟进结果的可选项
     * contactMethodDict：联络方式
     * stateDictOld：潜客状态
     * flag：默认值传0
     */
    public static Map<String, String> getFollowUpResultMap(String contactMethodDict, String stateDictOld, int flag) {
        Map<String, String> resultMap = new LinkedHashMap<>();
        if (null == contactMethodDict || null == stateDictOld) {
            return resultMap;
        }
        DBHelper dbHelper = DealerApp.dbHelper;
        Cursor cursor = null;
        if (flag == 0) {
            cursor = dbHelper.rawQuery("select * from OpportunityStateDict where contactMethodDict=? and stateDictOld=? and stateWeight>=0 order by stateWeight desc", new String[]{contactMethodDict, stateDictOld});
        } else if (flag < 0) {
            cursor = dbHelper.rawQuery("select * from OpportunityStateDict where contactMethodDict=? and stateDictOld=? and stateWeight<0 order by stateWeight desc", new String[]{contactMethodDict, stateDictOld});
        }
        if (cursor != null) {
            while (cursor.moveToNext()) {
                resultMap.put(cursor.getString(cursor.getColumnIndex("resultDict")), cursor.getString(cursor.getColumnIndex("resultDictName")));
            }
            cursor.close();
        }
        return resultMap;
    }

    public static Map<String, String> getCompetitorsSeriesMap(String seriesId) {
        Map<String, String> resultMap = new LinkedHashMap<>();
        if (TextUtils.isEmpty(seriesId)) {
            return resultMap;
        }
        DBHelper dbHelper = DealerApp.dbHelper;
        Cursor cursor = dbHelper.rawQuery("SELECT * FROM CompetitorsSeriesDict WHERE seriesId=?", new String[]{seriesId});
        if (cursor != null) {
            while (cursor.moveToNext()) {
                resultMap.put(cursor.getString(cursor.getColumnIndex("competitorId")), cursor.getString(cursor.getColumnIndex("competitorName")));
            }
            cursor.close();
        }
        return resultMap;
    }

    /**
     * 根据dictId查询DictionaryRel中的数据
     *
     * @return
     */
    public static void getMapFromDictionaryRel() {
        DBHelper dbHelper = DealerApp.dbHelper;
        Cursor cursor = dbHelper.rawQuery("select relaId, relaName, dictId from DictionaryRel", new String[]{});
        if (cursor != null) {
            while (cursor.moveToNext()) {
                if ("11040".equals(cursor.getString(cursor.getColumnIndex("dictId")))) {
                    NewCustomerConstants.phoneMediaMap.put(cursor.getString(cursor.getColumnIndex("relaId")), cursor.getString(cursor.getColumnIndex("relaName")));
                }
            }
            cursor.close();
        }
    }

    /**
     * 获取当前环境标识
     *
     * @return
     */
    public static String getCurrentEnvLabel() {
        String result = "";
        Cursor cursor = DealerApp.dbHelper.rawQuery("select * from Env where status=?", new String[]{"1"});
        if (cursor != null) {
            if (cursor.moveToNext()) {
                result = cursor.getString(cursor.getColumnIndex("name")).toString();
            }
            cursor.close();
        }
        return result;
    }

    /**
     * 获取当前环境id
     *
     * @return
     */
    public static int getCurrentEnvId() {
        int num = 0;
        Cursor cursor = DealerApp.dbHelper.rawQuery("select * from Env where status=?", new String[]{"1"});
        if (cursor != null) {
            if (cursor.moveToNext()) {
                String envName = cursor.getString(cursor.getColumnIndex("name")).toString();
                num = Integer.parseInt(envName.substring(3, 4));
            }
            cursor.close();
        }
        return num;
    }
}
