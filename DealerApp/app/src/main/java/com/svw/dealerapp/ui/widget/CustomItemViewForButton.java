package com.svw.dealerapp.ui.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.TextView;

import com.svw.dealerapp.R;

/**
 * Created by xupan on 19/09/2017.
 */

@Deprecated
public class CustomItemViewForButton extends CustomItemViewBase<Boolean, Boolean> {

    private Context mContext;
    private Button mButton;
    private TextView mOptionalTv;
    private Boolean mData;

    public CustomItemViewForButton(Context context) {
        super(context);
        mContext = context;
    }

    public CustomItemViewForButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    @Override
    protected void initViews() {
        super.initViews();
        mButton = (Button) findViewById(R.id.custom_item_button);
        mOptionalTv = (TextView) findViewById(R.id.custom_item_optional_text);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.custom_item_button;
    }

    @Override
    public Boolean getInputData() {
        return mData;
    }

    @Override
    public void setData(Boolean data) {
        mData = data;
        if (mOnDataChangedListener != null) {
            mOnDataChangedListener.onDateChanged(data);
        }
    }

    public void setClickListener(OnClickListener listener) {
        mButton.setOnClickListener(listener);
    }

    public void setOptionalTrueText(String text) {
        mOptionalTv.setText(text);
        mOptionalTv.setTextColor(mContext.getResources().getColor(android.R.color.black));
    }

    public void setOptionalFalseText(String text) {
        mOptionalTv.setText(text);
        mOptionalTv.setTextColor(mContext.getResources().getColor(R.color.new_customer_mandatory));
    }

}
