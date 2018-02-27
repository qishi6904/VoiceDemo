package com.svw.dealerapp.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;

import com.svw.dealerapp.R;

/**
 * Created by qinshi on 5/9/2017.
 */

public class LoadingDialog extends Dialog {
    public LoadingDialog(@NonNull Context context) {
        super(context, R.style.loading_dialog);
        setContentView(R.layout.dialog_loading);
        this.setCanceledOnTouchOutside(false);
    }

    @Override
    public void onBackPressed() {

    }
}
