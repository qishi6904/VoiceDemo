package com.svw.dealerapp.ui.task.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.svw.dealerapp.R;
import com.svw.dealerapp.ui.adapter.CustomDialogAdapter;

/**
 * Created by qinshi on 5/26/2017.
 */

public class BenefitDialogAdapter implements CustomDialogAdapter {

    private TextView textView;

    @Override
    public View getDialogView(Context context) {
        View view = View.inflate(context, R.layout.dialog_benefit, null);
        textView = (TextView) view.findViewById(R.id.tv_content);
        return view;
    }

    /**
     * 设置对话框的内容
     * @param content
     */
    public void setTvContent(String content){
        textView.setText(content);
    }
}
