package com.svw.dealerapp.ui.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.svw.dealerapp.R;
import com.svw.dealerapp.common.BaseActivity;
import com.svw.dealerapp.common.BaseFragment;
import com.svw.dealerapp.util.CommonUtils;

/**
 * 通用Header
 * Created by xupan on 14/07/2017.
 */

public class CustomSectionViewGroup extends LinearLayout {

    private RelativeLayout mHeaderLayout;
    private TextView mHeaderTitleTv, mRightTv;
    private ImageView mRightIv;
    private ImageView mLeftIv;
    private LinearLayout mBodyContainer;
    private Context mActivity;
    private OnExpandOrCollapseListener mOnExpandOrCollapseListener;

    public CustomSectionViewGroup(Context baseActivity) {
        super(baseActivity);
        mActivity = baseActivity;
        initViews(baseActivity);
        setListeners();
    }

    public CustomSectionViewGroup(Context baseActivity, @Nullable AttributeSet attrs) {
        super(baseActivity, attrs);
        mActivity = baseActivity;
        initViews(baseActivity);
        setListeners();
    }

    private void initViews(Context context) {
        View view = View.inflate(context, R.layout.custom_section_view_group, this);
        mHeaderLayout = (RelativeLayout) view.findViewById(R.id.custom_section_view_header_layout);
        mHeaderTitleTv = (TextView) view.findViewById(R.id.custom_section_view_header_title_tv);
        mRightIv = (ImageView) view.findViewById(R.id.custom_section_view_header_collapse_iv);
        mRightTv = (TextView) view.findViewById(R.id.custom_section_view_header_right_tv);
        mLeftIv = (ImageView) view.findViewById(R.id.custom_section_view_header_label_iv);
        mBodyContainer = (LinearLayout) view.findViewWithTag("customer_section_view_group_body");
    }

    private void setListeners() {
        mHeaderLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBodyContainer.getVisibility() == VISIBLE) {
                    //此时进行收缩
                    mBodyContainer.setVisibility(GONE);
//                    mRightIv.setImageResource(R.mipmap.new_yellow_card_expand);
                    mLeftIv.setImageResource(R.mipmap.rd_ic_blue_arrow_down);
                    if (mOnExpandOrCollapseListener != null) {
                        mOnExpandOrCollapseListener.onExpandOrCollapse(CustomSectionViewGroup.this, false);
                    }
                } else if (mBodyContainer.getVisibility() == GONE) {
                    //此时进行展开
                    mBodyContainer.setVisibility(VISIBLE);
//                    mRightIv.setImageResource(R.mipmap.new_yellow_card_collapse);
                    mLeftIv.setImageResource(R.mipmap.rd_ic_blue_arrow_up);
                    if (mOnExpandOrCollapseListener != null) {
                        mOnExpandOrCollapseListener.onExpandOrCollapse(CustomSectionViewGroup.this, true);
                    }
                }
            }
        });
    }

    public void setOnExpandOrCollapseListener(OnExpandOrCollapseListener listener) {
        mOnExpandOrCollapseListener = listener;
    }

    public interface OnExpandOrCollapseListener {
        void onExpandOrCollapse(CustomSectionViewGroup viewGroup, boolean expand);
    }

    public boolean isExpanded() {
        return mBodyContainer.getVisibility() == VISIBLE;
    }

    public void disableCollapse() {
        mHeaderLayout.setOnClickListener(null);
        mRightIv.setVisibility(GONE);
    }

    public void setCollapsed(boolean collapsed) {
        if (collapsed) {
            mBodyContainer.setVisibility(GONE);
            mRightIv.setImageResource(R.mipmap.new_yellow_card_expand);
        } else {
            mBodyContainer.setVisibility(VISIBLE);
            mRightIv.setImageResource(R.mipmap.new_yellow_card_collapse);
        }
    }

    public void addFragment(BaseFragment fragment) {
        int viewId;
        //如果还没有id，则分配一个id，如果已经有id,则使用现有的id;
        if (mBodyContainer.getId() == NO_ID) {
            viewId = CommonUtils.generateViewId();
            mBodyContainer.setId(viewId);
        } else {
            viewId = mBodyContainer.getId();
        }
        if (mActivity instanceof BaseActivity) {
            ((BaseActivity) mActivity).getSupportFragmentManager().beginTransaction().add(viewId, fragment).commit();
        }
    }

    public void removeFragment() {
        mBodyContainer.removeAllViews();
    }

    public void showFragment(BaseFragment fragment, boolean show) {
        FragmentTransaction fragmentTransaction = ((BaseActivity) mActivity).getSupportFragmentManager().beginTransaction();
        if (show) {
            fragmentTransaction.show(fragment).commit();
        } else {
            fragmentTransaction.hide(fragment).commit();
        }
    }

//    public void addChild(View view) {
//        mBodyContainer.addView(view);
//    }

    public void setTitleText(int resId) {
        mHeaderTitleTv.setText(resId);
    }

    public void setTitleText(String text) {
        mHeaderTitleTv.setText(text);
    }

    public void showCollapseIcon(boolean show) {
        mRightIv.setVisibility(show ? VISIBLE : GONE);
    }

    public void showRightText(boolean show) {
        mRightTv.setVisibility(show ? VISIBLE : GONE);
    }

    public void setRightTextOnClickListener(OnClickListener listener) {
        mRightTv.setOnClickListener(listener);
    }

    public void setRightTextEnabled(boolean enabled) {
        mRightTv.setEnabled(enabled);
    }

    public boolean isRightTextEnabled() {
        return mRightTv.isEnabled();
    }
}
