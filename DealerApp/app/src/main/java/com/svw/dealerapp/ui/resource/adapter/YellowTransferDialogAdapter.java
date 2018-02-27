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

public class YellowTransferDialogAdapter implements CustomDialogAdapter {

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

    /**
     * 设置要转移的用户姓名和要转移到的销售的姓名
     * @param customerName
     * @param salesName
     */
    public void setNames(String customerName, String salesName){
        if(null != textView) {
            textView.setText(context.getResources().getString(R.string.resource_yellow_transfer_dialog_tip_one)
                    + customerName + context.getResources().getString(R.string.resource_yellow_transfer_dialog_tip_two)
                    + " " + salesName + " " + context.getResources().getString(R.string.resource_yellow_transfer_dialog_tip_three));
        }
    }
}
