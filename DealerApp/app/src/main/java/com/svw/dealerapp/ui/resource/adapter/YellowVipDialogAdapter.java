package com.svw.dealerapp.ui.resource.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.svw.dealerapp.R;
import com.svw.dealerapp.ui.adapter.CustomDialogAdapter;
import com.svw.dealerapp.util.DensityUtil;

/**
 * Created by qinshi on 5/15/2017.
 */

public class YellowVipDialogAdapter implements CustomDialogAdapter {

    private TextView textView;

    @Override
    public View getDialogView(Context context) {
        textView = new TextView(context);
        textView.setTextSize(16);
        textView.setTextColor(context.getResources().getColor(R.color.resource_main_text));
        textView.setText(context.getResources().getString(R.string.yellow_vip_dialog_tip));
        textView.setGravity(Gravity.CENTER);
        textView.setPadding(DensityUtil.dp2px(context, 26),
                0,
                DensityUtil.dp2px(context, 26),
                DensityUtil.dp2px(context, 36));
        return textView;
    }

    public void setContentText(String text){
        textView.setText(text);
    }
}
