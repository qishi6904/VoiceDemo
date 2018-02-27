package com.svw.dealerapp.ui.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.svw.dealerapp.R;

/**
 * 通用的CheckBox信息录入行
 * Created by xupan on 19/01/2018.
 */

public class RdCustomItemViewForCheckbox extends RdCustomItemViewBase<Boolean, Boolean> {

    private CheckBox mCheckBox;

    public RdCustomItemViewForCheckbox(Context context) {
        super(context);
    }

    public RdCustomItemViewForCheckbox(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.rd_custom_item_checkbox;
    }

    @Override
    public Boolean getInputData() {
        return mCheckBox.isChecked();
    }

    @Override
    public void setData(Boolean checked) {
        mCheckBox.setChecked(checked);
        refresh();
    }

    @Override
    protected void initViews() {
        super.initViews();
        mCheckBox = (CheckBox) findViewById(R.id.custom_item_checkbox);
        mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (mOnDataChangedListener != null) {
                    mOnDataChangedListener.onDateChanged(isChecked);
                }
            }
        });
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        mEnabled = enabled;
        refresh();
    }

    private void refresh() {
        mCheckBox.setVisibility(mEnabled ? VISIBLE : GONE);
        mContentTextView.setVisibility(mEnabled ? GONE : VISIBLE);
        mContentTextView.setText(mCheckBox.isChecked() ? "是" : "否");
    }
}
