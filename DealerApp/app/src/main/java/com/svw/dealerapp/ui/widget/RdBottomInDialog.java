package com.svw.dealerapp.ui.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Display;
import android.view.WindowManager;

import com.svw.dealerapp.R;
import com.svw.dealerapp.util.ScreenUtils;

/**
 * Created by qinshi on 1/25/2018.
 */

public class RdBottomInDialog extends Dialog {

    private Context context;

    public RdBottomInDialog(@NonNull Context context) {
        super(context, R.style.custom_dialog);
        this.context = context;
    }

    @Override
    public void show() {
        super.show();

        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.width = ScreenUtils.getScreenWidth(context); //设置宽度
        getWindow().setAttributes(lp);
    }
}
