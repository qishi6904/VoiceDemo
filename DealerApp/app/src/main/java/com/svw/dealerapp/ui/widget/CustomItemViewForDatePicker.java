package com.svw.dealerapp.ui.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.bigkoo.pickerview.TimePickerView;
import com.svw.dealerapp.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 通用的日期Picker输入行
 * Created by xupan on 17/07/2017.
 */

@Deprecated
public class CustomItemViewForDatePicker extends CustomItemViewBase<String, String> implements TimePickerView.OnTimeSelectListener {

    private static final String TAG = "CustomItemViewForDatePicker";
    private TimePickerView mPicker;
    private String mDisplayStr;//用于在UI(即ContentTextView)上显示的string
    private String mParameterStr;//用于传参的日期string
    private String mDateFormatStrForDisplay;//用于在UI上显示的dateFormat
    private String mDateFormatStrForParameter;//用于传参的dateFormat

    public CustomItemViewForDatePicker(Context context) {
        super(context);
    }

    public CustomItemViewForDatePicker(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.custom_item_picker;
    }

    @Override
    public String getInputData() {
        return mParameterStr;
    }

    @Override
    public void clearSelection() {
        super.clearSelection();
        mContentTextView.setText("");
        mParameterStr = "";
        mDisplayStr = "";
    }

    @Override
    public void setData(String dataStr) {
        if (TextUtils.isEmpty(dataStr)) {
            return;
        }
        SimpleDateFormat sdfForParameter = new SimpleDateFormat(mDateFormatStrForParameter);
        SimpleDateFormat sdfForDisplay = new SimpleDateFormat(mDateFormatStrForDisplay);
        Date date = null;
        try {
            date = sdfForParameter.parse(dataStr);
            mDisplayStr = sdfForDisplay.format(date);
            mParameterStr = sdfForParameter.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        mPicker.setDate(calendar);
        mContentTextView.setText(mDisplayStr);
    }

    private void setDefaultDateFormat() {
        if (TextUtils.isEmpty(mDateFormatStrForDisplay) || TextUtils.isEmpty(mDateFormatStrForParameter)) {
            setDateFormatStr("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        }
    }

    /**
     * 必须调用此方法或者其重载方法才能显示Picker
     *
     * @param type
     */
    public void initPicker(boolean[] type) {
        mPicker = new TimePickerView.Builder(mContext, this).setType(type).build();
        setDefaultDateFormat();
    }

    /**
     * Picker初始化方法
     *
     * @param type  年月日时分秒是否显示
     * @param start 可选范围开始日期
     * @param end   可选范围结束日期
     */
    public void initPicker(boolean[] type, Calendar start, Calendar end) {
        mPicker = new TimePickerView.Builder(mContext, this).setType(type).setRangDate(start, end).build();
        setDefaultDateFormat();
    }

    /**
     * Picker初始化方法
     *
     * @param type       年月日时分秒是否显示
     * @param start      可选范围开始日期
     * @param end        可选范围结束日期
     * @param defaultCal 默认显示日期
     */
    public void initPicker(boolean[] type, Calendar start, Calendar end, Calendar defaultCal) {
        initPicker(type, start, end);
        mPicker.setDate(defaultCal);
        SimpleDateFormat sdfForDisplay = new SimpleDateFormat(mDateFormatStrForDisplay, Locale.CHINA);
        Date date = defaultCal.getTime();
        String result = sdfForDisplay.format(date);
        setTextForContentView(result);
        SimpleDateFormat sdfForPara = new SimpleDateFormat(mDateFormatStrForParameter, Locale.CHINA);
        mParameterStr = sdfForPara.format(date);
    }

    /**
     * 时间格式，默认是显示和传参的格式一致。
     * 但如果传参格式与显示时间格式不同，则需要再调用setDateFormatStrForParameter
     *
     * @param dateFormatStr 全长为"yyyy-MM-dd'T'HH:mm:ss.SSSZ"，根据需求截取使用.
     */
    public void setDateFormatStr(String dateFormatStr) {
        mDateFormatStrForParameter = mDateFormatStrForDisplay = dateFormatStr;
    }

    public void setDateFormatStrForParameter(String dateFormatStrForParameter) {
        mDateFormatStrForParameter = dateFormatStrForParameter;
    }

    @Override
    public void onTimeSelect(Date date, View v) {
        SimpleDateFormat sdfForDisplay = new SimpleDateFormat(mDateFormatStrForDisplay);
        mParameterStr = mDisplayStr = sdfForDisplay.format(date);
        setTextForContentView(mDisplayStr);
        //当用于传参的格式不同于显示的格式时，需重新用传参的格式来格式化控件当前显示的日期
        if (!mDateFormatStrForParameter.equals(mDateFormatStrForDisplay)) {
            SimpleDateFormat sdfForParameter = new SimpleDateFormat(mDateFormatStrForParameter);
            mParameterStr = sdfForParameter.format(date);
        }
        if (mOnDataChangedListener != null) {
            mOnDataChangedListener.onDateChanged(mDisplayStr);
        }
//        JLog.d(TAG, "mDisplayStr: " + mDisplayStr);
//        JLog.d(TAG, "mParameterStr: " + mParameterStr);
    }

    @Override
    protected void onRootLayoutClicked() {
        super.onRootLayoutClicked();
        if (mEnabled && mPicker != null) {
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
}
