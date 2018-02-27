package com.svw.dealerapp.ui.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.svw.dealerapp.R;

/**
 * Created by xupan on 2018/1/12.
 */

public class RdHomeHeaderLayout extends LinearLayout {

    private ImageView ivHeaderIcon;
    private TextView tvHeaderNumber;
    private TextView tvHeaderText;
    private View vDivider;
    private TextView tvCircleTip;

    private LinearLayout mRootLayout;
    private TextView mBigNumberTv, mTipNumberTv, mContentTv;
    private ImageView mContentIv;

    public RdHomeHeaderLayout(Context context) {
        super(context);
        initView(context);
    }

    public RdHomeHeaderLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        View view = View.inflate(context, R.layout.rd_item_home_header, this);
//        assignViews(view);
        assignViews2(view);
    }

    private void assignViews(View view) {
        ivHeaderIcon = (ImageView) view.findViewById(R.id.iv_header_icon);
        tvHeaderNumber = (TextView) view.findViewById(R.id.tv_header_number);
        tvHeaderText = (TextView) view.findViewById(R.id.tv_header_text);
        vDivider = view.findViewById(R.id.v_divider);
        tvCircleTip = (TextView) view.findViewById(R.id.tv_delay_num);
    }

    private void assignViews2(View view) {
        mRootLayout = (LinearLayout) view.findViewById(R.id.rd_item_home_header_root_ll);
        mBigNumberTv = (TextView) view.findViewById(R.id.rd_item_home_header_number_tv);
        mTipNumberTv = (TextView) view.findViewById(R.id.rd_item_home_header_tip_tv);
        mContentIv = (ImageView) view.findViewById(R.id.rd_item_home_header_icon_iv);
        mContentTv = (TextView) view.findViewById(R.id.rd_item_home_header_content_tv);
    }

    public void init(int resId, String text) {
        mContentIv.setImageResource(resId);
        mContentTv.setText(text);
    }

    public void setBigNumber(String number) {
        mBigNumberTv.setText(number);
    }

    public void showTipNumber(boolean show) {
        mTipNumberTv.setVisibility(show ? VISIBLE : INVISIBLE);
    }

    public void setTipNumber(String tipNumber) {
        mTipNumberTv.setText(tipNumber);
    }

    public void setIvHeaderIcon(int resId) {
        ivHeaderIcon.setImageResource(resId);
    }

    public void setTvHeaderNumber(String number) {
        tvHeaderNumber.setText(number);
    }

    public String getTVHeaderNumber() {
        return tvHeaderNumber.getText().toString();
    }

    public void setTvHeaderText(String text) {
        tvHeaderText.setText(text);
    }

    public void hideDivider() {
        vDivider.setVisibility(View.GONE);
    }

    public void hideCircleTip() {
        tvCircleTip.setVisibility(View.INVISIBLE);
    }

    public void setCircleTip(String circleTip) {
        tvCircleTip.setVisibility(View.VISIBLE);
        tvCircleTip.setText(circleTip);
    }
}
