package com.svw.dealerapp.ui.widget;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.svw.dealerapp.R;
import com.svw.dealerapp.util.CommonUtils;
import com.svw.dealerapp.util.NLSRTUtils;

/**
 * Created by lijinkui on 2017/7/20.
 */

public class EditTextWithMicLayout extends LinearLayout {

    private Activity mContext;
    private Fragment currentFragment = null;
    private RelativeLayout rlEditWithMic;
    private EditText etDesc;
    private TextView tvNum;
    private ImageView ivNLSButton;
    private NLSRTUtils nlsRTUtils;
    private OnTextChangeListener textChangeListener;

    public EditTextWithMicLayout(Context context) {
        super(context);
        initView(context);
    }

    public EditTextWithMicLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public void initEditWithMic(Activity context, Fragment fragment) {
        mContext = context;
        currentFragment = fragment;
        nlsRTUtils = new NLSRTUtils(mContext, etDesc, ivNLSButton, currentFragment);
        nlsRTUtils.setConfig();
    }

    private void initView(Context context) {
        View view = View.inflate(context, R.layout.edittext_with_mic, this);
        assignViews(view);

        etDesc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String editabeStr = s.toString();
                //不允许第一个字符输入空格
                if (editabeStr.startsWith(" ")) {
                    String result = editabeStr.substring(1, editabeStr.length());
                    etDesc.setText(result);
                    return;
                }
                if (null != textChangeListener) {
                    textChangeListener.onAfterTextChange(s);
                }
            }
        });
    }

    private void assignViews(View view) {
        ivNLSButton = (ImageView) view.findViewById(R.id.nlsButton);
        tvNum = (TextView) view.findViewById(R.id.tv_text_number);
        etDesc = (EditText) view.findViewById(R.id.et_desc);
        rlEditWithMic = (RelativeLayout) view.findViewById(R.id.rl_et_with_mic);
    }

    public void stopNLS() {
        nlsRTUtils.stopNLS();
    }

    public void setMaxTextNum(int num) {
        tvNum.setText("0/" + num + "");
        etDesc.setFilters(new InputFilter[]{new InputFilter.LengthFilter(num)});
        CommonUtils.limitEditTextInput(etDesc, tvNum, num);
    }

    public String getTextContent() {
        return etDesc.getText().toString();
    }

    public void clickRecordButton() {
        nlsRTUtils.clickRecordButton();
    }

    public void setEnabled(boolean status) {
        etDesc.setEnabled(status);
        nlsRTUtils.setEnabled(status);
    }

    public void setText(String text) {
        etDesc.setText(text);
    }

    public String getText() {
        return etDesc.getText().toString().trim();
    }

    public void setHint(String text) {
        etDesc.setHint(text);
    }

    public void setHint(int resId) {
        etDesc.setHint(resId);
    }

    public void setOnTextChangeListener(OnTextChangeListener textChangeListener) {
        this.textChangeListener = textChangeListener;
    }

    public interface OnTextChangeListener {
        void onAfterTextChange(CharSequence s);
    }

}
