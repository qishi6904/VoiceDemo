package com.svw.dealerapp.ui.widget;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.svw.dealerapp.R;

import java.util.List;

/**
 * 通用的包含两个选项的RadioGroup
 * Created by xupan on 19/01/2018.
 */

public class RdCustomItemViewForRadioButton extends RdCustomItemViewBase<String, String> {

    private RadioGroup mRadioGroup;
    private RadioButton mRadioButton0, mRadioButton1;

    private List<String> mCodeList, mValueList;
    private int mCheckedIndex = -1;

    public RdCustomItemViewForRadioButton(Context context) {
        super(context);
    }

    public RdCustomItemViewForRadioButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.rd_custom_item_two_item_radiobutton;
    }

    @Override
    public String getInputData() {
        if (mCheckedIndex != -1) {
            return mCodeList.get(mCheckedIndex);
        } else {
            return "";
        }
    }

    @Override
    public void setData(String code) {
        int index = mCodeList.indexOf(code);
        if (index == -1) {
            return;
        }
        mCheckedIndex = index;
        if (mEnabled) {
            switch (index) {
                case 0:
                    mRadioButton0.setChecked(true);
                    break;
                case 1:
                    mRadioButton1.setChecked(true);
                    break;
                default:
                    break;
            }
        } else {
            mContentTextView.setText(mValueList.get(index));
        }
    }

    @Override
    protected void initViews() {
        super.initViews();
        mRadioGroup = (RadioGroup) findViewById(R.id.custom_item_radio_group);
        mRadioButton0 = (RadioButton) findViewById(R.id.custom_item_radio_button_0);
        mRadioButton1 = (RadioButton) findViewById(R.id.custom_item_radio_button_1);
    }

    /**
     * 必须调用此方法，RadioButton才有文字
     *
     * @param codeList
     * @param valueList
     */
    public void initLists(List<String> codeList, List<String> valueList) {
        if (codeList == null || codeList.isEmpty() || valueList == null || valueList.size() != 2) {
            return;
        }
        mCodeList = codeList;
        mValueList = valueList;
        setRadioButtonText(valueList.get(0), valueList.get(1));
    }

    public void setRadioButtonText(String text1, String text2) {
        mRadioButton0.setText(text1);
        mRadioButton1.setText(text2);
    }

    public void setRadioButtonText(int res1, int res2) {
        mRadioButton0.setText(res1);
        mRadioButton1.setText(res2);
    }

    @Override
    protected void setListeners() {
        super.setListeners();
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.custom_item_radio_button_0) {
                    mCheckedIndex = 0;
                } else if (checkedId == R.id.custom_item_radio_button_1) {
                    mCheckedIndex = 1;
                }
                if (mOnDataChangedListener != null) {
                    mOnDataChangedListener.onDateChanged(mCheckedIndex);
                }
            }
        });
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        mEnabled = enabled;
        mContentTextView.setVisibility(enabled ? GONE : VISIBLE);
        mRadioGroup.setVisibility(enabled ? VISIBLE : GONE);
        if (!enabled && mCheckedIndex != -1) {
            mContentTextView.setText(mValueList.get(mCheckedIndex));
        }
    }
}

