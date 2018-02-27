package com.svw.dealerapp.util;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.svw.dealerapp.DealerApp;

/**
 * Created by qinshi on 7/3/2017.
 */

public class ManifestUtils {

    /**
     * 获取 application 标签中的 meta-data 的值
     * @param key
     * @return
     */
    public static String getMetaDataValue(String key){
        try {
            ApplicationInfo appInfo = DealerApp.getContext().getPackageManager()
                    .getApplicationInfo(DealerApp.getContext().getPackageName(),
                            PackageManager.GET_META_DATA);
            String value = appInfo.metaData.getString(key);
            return value;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
