package com.svw.dealerapp.util;

import android.text.TextUtils;

import java.io.File;

/**
 * Created by qinshi on 6/14/2017.
 */

public class FileUtils {

    /**
     * 删除文件
     * @param path
     */
    public static void deleteFile(String path){
        if(TextUtils.isEmpty(path)){
            return;
        }
        File file = new File(path);
        if(file.exists()){
            file.delete();
        }
    }

    /**
     * 获取文件大小
     * @param path
     * @return
     */
    public static long getFileSize(String path){
        File file = new File(path);
        if(file.exists() && file.isFile()){
            return file.length();
        }
        return 0;
    }
}
