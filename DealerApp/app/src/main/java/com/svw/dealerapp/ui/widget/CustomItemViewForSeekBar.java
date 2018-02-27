package com.svw.dealerapp.ui.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.jaygoo.widget.RangeSeekBar;
import com.svw.dealerapp.R;

import java.util.Locale;

/**
 * 供里程和购车预算SeekBar使用的控件
 * Created by xupan on 16/07/2017.
 */

public class CustomItemViewForSeekBar extends CustomItemViewBase {

    private RangeSeekBar mMileageSeekBar, mBudgetSeekBar;
    private boolean mShowMileageSeekBar;
    private int mCurrentMileage;
    private int mBudgetMin, mBudgetMax;

    public CustomItemViewForSeekBar(Context context) {
        super(context);
    }

    public CustomItemViewForSeekBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.custom_item_seekbar;
    }

    @Override
    public String getInputData() {
        if (mShowMileageSeekBar) {
            return String.valueOf(mCurrentMileage);
        } else {
            // TODO: 16/07/2017  
        }
        return null;
    }

    @Override
    public void setData(Object data) {

    }

    @Override
    protected void initViews() {
        super.initViews();
        mMileageSeekBar = (RangeSeekBar) mRootView.findViewById(R.id.new_customer_step_2_mileage_seekbar);
        mBudgetSeekBar = (RangeSeekBar) mRootView.findViewById(R.id.new_customer_step_2_intention_budget_seekbar);
    }

    @Override
    protected void setListeners() {
        super.setListeners();
        mMileageSeekBar.setOnRangeChangedListener(new RangeSeekBar.OnRangeChangedListener() {
            @Override
            public void onRangeChanged(RangeSeekBar view, float min, float max, boolean isFromUser) {
                mContentTextView.setText(String.format(Locale.CHINA, "%dkm", (int) min));
                mCurrentMileage = (int) min;
            }
        });
        mBudgetSeekBar.setOnRangeChangedListener(new RangeSeekBar.OnRangeChangedListener() {
            @Override
            public void onRangeChanged(RangeSeekBar view, float min, float max, boolean isFromUser) {
//                JLog.d(TAG, "min: " + min + "| max: " + max);
                mBudgetMin = (int) min * 10000;
                mBudgetMax = (int) max * 10000;
            }
        });
    }

    public void setMileageSeekBar(boolean showMileageSeekBar) {
        mShowMileageSeekBar = showMileageSeekBar;
        mMileageSeekBar.setVisibility(showMileageSeekBar ? VISIBLE : GONE);
        mBudgetSeekBar.setVisibility(showMileageSeekBar ? GONE : VISIBLE);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        mEnabled = enabled;
    }
}
