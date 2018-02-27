package com.svw.dealerapp.ui.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.bigkoo.pickerview.OptionsPickerView;
import com.svw.dealerapp.R;

import java.util.List;

/**
 * 通用的下拉选项Picker输入行
 * Created by xupan on 15/07/2017.
 */

@Deprecated
public class CustomItemViewForOptionsPicker extends CustomItemViewBase<String, String> {

    private OptionsPickerView mPicker;

    private List<String> mCodeList, mValueList;
    private String mCurrentCode;

    public CustomItemViewForOptionsPicker(Context context) {
        super(context);
    }

    public CustomItemViewForOptionsPicker(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.custom_item_picker;
    }

    protected void initViews() {
        super.initViews();
    }

    @Override
    public String getInputData() {
        return mCurrentCode;
    }

    /**
     * 设置当前显示选项（通常用于显示预设值）
     *
     * @param code 当前显示的选项的code
     */
    @Override
    public void setData(String code) {
        if (TextUtils.isEmpty(code) || mCodeList == null) {
            return;
        }
        int index = mCodeList.indexOf(code);
        if (index != -1) {
            mCurrentCode = code;
            mPicker.setSelectOptions(index);
            setTextForContentView(mValueList.get(index));
        }
    }

    /**
     * 设置静态展示数据，不可被点击，无右箭头(无需下拉选项时使用)
     *
     * @param code  用来传参的值
     * @param value UI上显示的文字
     */
    public void setStaticData(String code, String value) {
        if (TextUtils.isEmpty(code)) {
            return;
        }
        mCurrentCode = code;
        setTextForContentView(value);
        setEnabled(false);
    }

    /**
     * 设置静态展示数据(无需下拉选项时使用)
     *
     * @param code    用来传参的值
     * @param value   UI上显示的文字
     * @param enabled 是否enable
     */
    public void setStaticData(String code, String value, boolean enabled) {
        if (TextUtils.isEmpty(code)) {
            return;
        }
        mCurrentCode = code;
        setTextForContentView(value);
        setEnabled(enabled);
    }

    /**
     * 清除输入的数据且将Picker置为null
     */
    @Override
    public void clearData() {
        super.clearData();
        mCurrentCode = "";
        mContentTextView.setText("");
        mPicker = null;
    }

    /**
     * 清除当前的输入项
     */
    public void clearSelection() {
        super.clearSelection();
        mCurrentCode = "";
        mContentTextView.setText("");
        if (mPicker != null) {
            mPicker.setSelectOptions(0);
        }
    }

    /**
     * 必须调用此方法，下拉框才有选项
     *
     * @param codeList  用来传值的codeList
     * @param valueList 用来在UI上显示的valueList
     */
    public void initLists(List<String> codeList, List<String> valueList) {
        mCodeList = codeList;
        mValueList = valueList;
        initPicker();
    }

    private void initPicker() {
        mPicker = new OptionsPickerView.Builder(mContext, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                mCurrentCode = mCodeList.get(options1);
                mContentTextView.setText(mValueList.get(options1));
                if (mOnDataChangedListener != null) {
                    mOnDataChangedListener.onDateChanged(mCurrentCode);
                }
            }
        }).build();
        mPicker.setNPicker(mValueList, null, null);
        mContentTextView.setText("");
    }

    @Override
    protected void onRootLayoutClicked() {
        super.onRootLayoutClicked();
        if (mEnabled && mClickable && mPicker != null && mValueList != null && !mValueList.isEmpty()) {
            mPicker.show();
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        mEnabled = enabled;
        mContentTextView.setEnabled(enabled);
        if (!enabled) {
            mContentTextView.setCompoundDrawables(null, null, null, null);
        }
    }

    public void setClickable(boolean clickable) {
        super.setClickable(clickable);
        mClickable = clickable;
    }
}
