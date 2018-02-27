package com.svw.dealerapp.util;

import android.content.res.Resources;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import com.svw.dealerapp.DealerApp;

/**
 * Created by qinshi on 2017/5/2 0025.
 */

public class ToastUtils {

    private static Toast toast;
    private static int textview_id;

    public static void showToast(String msg) {
        if (null != toast) {
            toast.cancel();
        }
        toast = Toast.makeText(DealerApp.getContext(), msg, Toast.LENGTH_LONG);
        if (textview_id == 0)
            textview_id = Resources.getSystem().getIdentifier("message", "id", "android");
        ((TextView) toast.getView().findViewById(textview_id)).setGravity(Gravity.CENTER);
        toast.show();
    }

    public static void destroy() {
        if (null != toast) {
            toast.cancel();
            toast = null;
        }
    }
}
