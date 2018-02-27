package com.svw.dealerapp.ui.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.svw.dealerapp.R;

/**
 * 自定义的通用的信息输入View的Base类
 * Created by xupan on 15/07/2017.
 */

@Deprecated
public abstract class CustomItemViewBase<T, S> extends LinearLayout {

    protected Context mContext;
    protected LinearLayout mRootLayout;
    protected RelativeLayout mContentLayout;//相对布局，且id固定
    protected View mRootView;
    protected TextView mStarTv;//必填项
    protected TextView mTitleTv;//左侧文字
    protected View mBottomLine;//底部分隔线
    protected TextView mContentTextView;//右侧文字

    protected boolean mEnabled = true;//是否启用
    protected boolean mClickable = true;//是否可点（和是否可用是两回事）
    private boolean mMandatory = false;//是否必填（会影响到完整性验证）
    protected boolean mInputValid = false;//是否有输入

    protected InputFilter[] mInputFilters = new InputFilter[]{};
    protected OnDataChangedListener mOnDataChangedListener;
    private OnClickListener mBaseViewClickListener;

    public CustomItemViewBase(Context context) {
        super(context);
        mContext = context;
        initViews();
        setListeners();
    }

    public CustomItemViewBase(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initViews();
        setListeners();
    }

    protected void initViews() {
        View view = mRootView = View.inflate(mContext, getLayoutId(), this);
        mRootLayout = (LinearLayout) view.findViewById(R.id.custom_item_root_layout);
        mContentLayout = (RelativeLayout) view.findViewById(R.id.custom_item_content_layout);
        mStarTv = (TextView) view.findViewById(R.id.custom_item_star_tv);
        mTitleTv = (TextView) view.findViewById(R.id.custom_item_title_tv);
        mBottomLine = view.findViewById(R.id.custom_item_bottom_line);
        mContentTextView = (TextView) view.findViewById(R.id.custom_item_content_view);
    }

    protected abstract int getLayoutId();

    /**
     * 获取用户输入数据
     *
     * @return 具体的数据，类型自定
     */
    public abstract T getInputData();

    public boolean isInputValid() {
        return mInputValid;
    }

    /**
     * 设置需要显示的数据（预设值）
     *
     * @param data 需要显示的数据
     */
    public abstract void setData(S data);

    /**
     * 设置是否为必填项（默认为非必填)
     *
     * @param mandatory true:必填 false:非必填
     */
    public void setMandatory(boolean mandatory) {
        mMandatory = mandatory;
        mStarTv.setVisibility(mandatory ? VISIBLE : INVISIBLE);
    }

    /**
     * 重置控件到未初始化状态
     */
    public void clearData() {
        //留给子类实现。有的子类暂时没实现此方法，所以此方法暂时不是abstract
    }

    /**
     * 仅清空当前的选项或输入内容，控件仍可点
     */
    public void clearSelection() {

    }

    /**
     * 是否为必填项
     *
     * @return true:必填
     */
    public boolean isMandatory() {
        return mMandatory;
    }

    @Override
    public boolean isEnabled() {
        return mEnabled;
    }

    /**
     * 是否可见
     *
     * @return true:可见
     */
    public boolean isVisible() {
        return getVisibility() == VISIBLE;
    }

    /**
     * 是否显示底部分隔线
     */
    public void setShowBottomLine(boolean showBottomLine) {
        mBottomLine.setVisibility(showBottomLine ? VISIBLE : INVISIBLE);
    }

    public void setTitleText(String text) {
        mTitleTv.setText(text);
    }

    public void setTitleText(int resId) {
        mTitleTv.setText(resId);
    }

    public void setTextForContentView(String text) {
        if (mContentTextView != null) {
            mContentTextView.setText(text);
        }
    }

    public void setTextForContentView(int resId) {
        if (mContentTextView != null) {
            mContentTextView.setText(resId);
        }
    }

    public void setMaxLinesForContentView(int maxLines) {
        if (mContentTextView != null) {
            mContentTextView.setMaxLines(maxLines);
        }
    }

    public void setHintTextForContentView(String hintText) {
        if (mContentTextView != null) {
            mContentTextView.setHint(hintText);
        }
    }

    public void setHintTextForContentView(int resId) {
        if (mContentTextView != null) {
            mContentTextView.setHint(resId);
        }
    }

    public String getHintText() {
        String hintText = "";
        if (mContentTextView != null && !TextUtils.isEmpty(mContentTextView.getHint())) {
            hintText = mContentTextView.getHint().toString();
        }
        return hintText;
    }

    /**
     * 设置maxLength属性
     *
     * @param maxLength 最大输入长度
     */
    public void setMaxLengthForContentView(int maxLength) {
        if (mContentTextView != null) {
            InputFilter.LengthFilter lengthFilter = new InputFilter.LengthFilter(maxLength);
            mContentTextView.setFilters(new InputFilter[]{lengthFilter});
        }
    }

    public void setLayoutHeight(int height) {
        if (mContentLayout == null) {
            return;
        }
        LayoutParams params = (LayoutParams) mContentLayout.getLayoutParams();
        params.height = height;
        mContentLayout.setLayoutParams(params);
    }

    protected void setListeners() {
        mRootLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onRootLayoutClicked();
            }
        });
    }

    protected void onRootLayoutClicked() {
        if (mEnabled && mBaseViewClickListener != null) {
            mBaseViewClickListener.onClick(this);
        }
    }

    public void setOnBaseViewClickListener(OnClickListener listener) {
        mBaseViewClickListener = listener;
    }

    /**
     * 控件内数据变化监听器
     */
    public interface OnDataChangedListener {
        /**
         * 控件内容变化时的回调方法
         *
         * @param object 改变后的值。对于OptionsPicker，直接将object转型为String,值为新的code;
         *               对于EditText，值为新的输入内容String;
         *               对于RadioButton, 值为新的String型code值
         *               对于Checkbox, 值为新的Boolean值
         */
        void onDateChanged(Object object);
    }

    public void setOnDataChangedListener(OnDataChangedListener listener) {
        mOnDataChangedListener = listener;
    }

    public void setTextSize(int textSize) {
        mTitleTv.setTextSize(textSize);
        mContentTextView.setTextSize(textSize);
    }

    public void setStarTextSize(int textSize) {
        mStarTv.setTextSize(textSize);
    }

    public void setTitleTextColor(int color) {
        mTitleTv.setTextColor(color);
    }

    public void setHintColor(int hintColor) {
        mContentTextView.setHintTextColor(hintColor);
    }

    public void setLeftRightMargin(int left, int right) {
        RelativeLayout.LayoutParams params1 = (RelativeLayout.LayoutParams) mStarTv.getLayoutParams();
        params1.leftMargin = left;
        mStarTv.setLayoutParams(params1);
        RelativeLayout.LayoutParams params2 = (RelativeLayout.LayoutParams) mContentTextView.getLayoutParams();
        params2.rightMargin = right;
        mContentTextView.setLayoutParams(params2);
    }

    public void setBottomLineMargin(int left, int right) {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mBottomLine.getLayoutParams();
        params.leftMargin = left;
        params.rightMargin = right;
        mBottomLine.setLayoutParams(params);
    }

}
