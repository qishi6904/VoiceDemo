package com.example.qinshi.voicedemo;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by qinshi on 2/27/2018.
 */

public class CustomInputView extends LinearLayout{

    private TextView tvKey;
    private EditText etValue;

    public CustomInputView(Context context) {
        super(context);
        initView(context);
    }

    public CustomInputView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        View view = View.inflate(context, R.layout.view_customer_input, this);
        tvKey = (TextView) view.findViewById(R.id.tv_key);
        etValue = (EditText) view.findViewById(R.id.et_value);
        tvKey.setFocusableInTouchMode(true);
        tvKey.requestFocus();
    }

    public void setKeyText(String text){
        tvKey.setText(text);
    }

    public void setValue(String text) {
        etValue.setText(text);
    }

    public void setHintValue(String text) {
        etValue.setHint(text);
    }
}
