package com.svw.dealerapp.ui.login;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.svw.dealerapp.R;
import com.svw.dealerapp.ui.adapter.CustomDialogAdapter;

/**
 * Created by xupan on 11/05/2017.
 */

public class SimpleMessageDialogAdapter implements CustomDialogAdapter {

    private TextView mMessageTv;

    @Override
    public View getDialogView(Context context) {
        View view = View.inflate(context, R.layout.dialog_login_result, null);
        mMessageTv = (TextView) view.findViewById(R.id.dialog_login_result_msg_tv);
        return view;
    }

    public void setDialogMessageText(String text) {
        mMessageTv.setText(text);
    }

    public void setTextViewPadding(int left, int top, int right, int bottom){
        mMessageTv.setPadding(left,top,right,bottom);
    }
}
