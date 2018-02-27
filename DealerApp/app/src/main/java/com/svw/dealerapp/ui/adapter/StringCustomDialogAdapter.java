package com.svw.dealerapp.ui.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.svw.dealerapp.R;
import com.svw.dealerapp.ui.widget.CustomDialog;
import com.svw.dealerapp.util.DensityUtil;

/**
 * Created by qinshi on 6/2/2017.
 */

public class StringCustomDialogAdapter implements CustomDialogAdapter {

    private TextView textView;
    private Context context;

    @Override
    public View getDialogView(Context context) {
        this.context = context;
        textView = new TextView(context);
        textView.setTextSize(16);
        textView.setTextColor(context.getResources().getColor(R.color.resource_main_text));

        textView.setGravity(Gravity.CENTER);
        textView.setPadding(0, 0, 0, DensityUtil.dp2px(context, 32));
        textView.setLineSpacing(DensityUtil.dp2px(context, 24), 0);
        return textView;
    }

    public void setContent(String content){
        textView.setText(content);
    }
}
