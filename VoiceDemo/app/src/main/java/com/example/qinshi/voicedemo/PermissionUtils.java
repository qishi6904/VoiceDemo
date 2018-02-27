package com.example.qinshi.voicedemo;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

/**
 * Created by qinshi on 5/31/2017.
 */

public class PermissionUtils {

    public static final int REQUEST_CAMERA_PERMISSION = 1001;
    public static final int REQUEST_STORAGE_PERMISSION = 1002;
    public static final int REQUEST_AUDIO_RECORD_PERMISSION = 1003;

    /**
     * 申请权限
     * @param activity
     * @param permission
     * @param requestCode
     * @param grantedListener
     * @param fragment  如果是在fragment中请求，需把fragment传进来，
     *                  否则fragment中的onRequestPermissionsResult回调访法不作用
     */
    public static void requestPermission(Activity activity, String[] permission,
                                         int requestCode, OnGrantedListener grantedListener, Fragment fragment) {
        //如果当前申请的权限没有授权
        if (ContextCompat.checkSelfPermission(activity, permission[0]) != PackageManager.PERMISSION_GRANTED) {
            if(null == fragment) {
                ActivityCompat.requestPermissions(activity, permission, requestCode);
            }else {
                fragment.requestPermissions(permission, requestCode);
            }
        } else {    //已经授权了就走这条分支
            if(null != grantedListener){
                grantedListener.onGranted();
            }
        }
    }

    public interface OnGrantedListener{
        void onGranted();
    }
}
