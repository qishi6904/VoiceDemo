package com.svw.dealerapp.ui.widget;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;

import com.svw.dealerapp.R;

/**
 * 集成了EditTextWithMicLayout的CustomItemView
 * Created by xupan on 24/01/2018.
 */

public class RdCustomItemViewForEditTextWithMic extends RdCustomItemViewBase<String, String> {
    private EditTextWithMicLayout mMicEditText;

    public RdCustomItemViewForEditTextWithMic(Context context) {
        super(context);
    }

    public RdCustomItemViewForEditTextWithMic(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.rd_custom_item_edittext_with_mic;
    }

    @Override
    protected void initViews() {
        super.initViews();
        mMicEditText = (EditTextWithMicLayout) findViewById(R.id.custom_item_edit_text_mic);
        mMicEditText.setOnTextChangeListener(new EditTextWithMicLayout.OnTextChangeListener() {
            @Override
            public void onAfterTextChange(CharSequence s) {
                if (mOnDataChangedListener != null) {
                    mOnDataChangedListener.onDateChanged(s);
                }
            }
        });
    }

    //调EditTextWithMicLayout的初始化方法而已
    public void initMicEditText(Activity context, Fragment fragment) {
        mMicEditText.initEditWithMic(context, fragment);
    }

    @Override
    public String getInputData() {
        return mMicEditText.getText();
    }

    @Override
    public void setData(String data) {
        mMicEditText.setText(data);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        mMicEditText.setEnabled(enabled);
    }

    @Override
    public void clearSelection() {
        super.clearSelection();
        setData("");
    }

    public void clickRecordButton() {
        mMicEditText.clickRecordButton();
    }

    public void setMaxTextNum(int num) {
        mMicEditText.setMaxTextNum(num);
    }

    public void setHint(int resId) {
        mMicEditText.setHint(resId);
    }

    public void stopNLS() {
        mMicEditText.stopNLS();
    }
}
