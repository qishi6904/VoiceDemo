package com.example.qinshi.voicedemo;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by qinshi on 2/27/2018.
 */

public class LoadingDialog extends Dialog {

    private TextView textView;
    private Button button;
    private OnFinishClickListener onFinishClickListener;

    public LoadingDialog(@NonNull Context context) {
        super(context, R.style.custom_dialog);
        setContentView(R.layout.view_customer_dialog);

        textView = (TextView) findViewById(R.id.tv_text);
        button = (Button) findViewById(R.id.btn);
        this.setCanceledOnTouchOutside(false);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != onFinishClickListener) {
                    onFinishClickListener.onClick();
                }
            }
        });
    }

    public void setText(String text) {
        textView.setText(text);
    }

    @Override
    public void onBackPressed() {

    }

    public interface OnFinishClickListener {
        void onClick();
    }

    public void setOnFinishClickListener(OnFinishClickListener listener) {
        this.onFinishClickListener = listener;
    }

    public void setButtonVisibility(boolean isVisibility) {
        if(isVisibility) {
            button.setVisibility(View.VISIBLE);
        }else {
            button.setVisibility(View.GONE);
        }
    }
}
