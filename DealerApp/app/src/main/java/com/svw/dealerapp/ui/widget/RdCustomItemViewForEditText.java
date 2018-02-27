package com.svw.dealerapp.ui.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;

import com.svw.dealerapp.R;

/**
 * 通用的新潜客内容输入行
 * Created by xupan on 18/01/2018.
 */
public class RdCustomItemViewForEditText extends RdCustomItemViewBase<String, String> {

    private static final String TAG = "RdCustomItemViewForEditText";
    private EditText mBigEt;
    private boolean mFlag;

    public RdCustomItemViewForEditText(Context context) {
        super(context);
    }

    public RdCustomItemViewForEditText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.rd_custom_item_edittext;
    }

    protected void initViews() {
        super.initViews();
        mBigEt = (EditText) findViewById(R.id.custom_item_content_view_big);
    }

    @Override
    public String getInputData() {
        return mFlag ? mBigEt.getEditableText().toString() : mContentTextView.getEditableText().toString();
    }

    @Override
    public void setData(String data) {
        if (mFlag) {
            mBigEt.setText(data);
        } else {
            setTextForContentView(data);
        }
    }

    @Override
    public void clearData() {
        super.clearData();
        setData("");
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        mEnabled = enabled;
        mContentTextView.setEnabled(enabled);
        mBigEt.setEnabled(enabled);
        if (!enabled) {
            mBigEt.setMaxLines(99);//显示全部内容，99行应该够了
        } else {
//            mBigEt.setMaxLines(2);
        }
    }

    @Override
    public void setHintTextForContentView(int resId) {
        if (!mFlag) {
            mContentTextView.setHint(resId);
        } else {
            mBigEt.setHint(resId);
        }
    }

    @Override
    public void setHintTextForContentView(String hintText) {
        if (!mFlag) {
            mContentTextView.setHint(hintText);
        } else {
            mBigEt.setHint(hintText);
        }
    }

    @Override
    protected void setListeners() {
        super.setListeners();
        setOnTextChangedListener();
    }

    private void setOnTextChangedListener() {
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mOnDataChangedListener != null) {
                    mOnDataChangedListener.onDateChanged(s.toString());
                }
            }
        };
        if (mFlag) {
            mBigEt.addTextChangedListener(textWatcher);
        } else {
            mContentTextView.addTextChangedListener(textWatcher);
        }
    }

    /**
     * 设置地址输入栏标志，然后可以输入多行，且disabled后能显示全部内容
     *
     * @param flag true:设置为地址输入栏 false:设置为普通的输入栏
     */
    public void setFlag(boolean flag) {
        mFlag = flag;
        mBigEt.setVisibility(flag ? VISIBLE : GONE);
        mContentTextView.setVisibility(flag ? GONE : VISIBLE);
        setOnTextChangedListener();
    }

    public void limitContentToNumber() {
        mContentTextView.setInputType(InputType.TYPE_CLASS_NUMBER);
    }

    public void setOnEditTextFocusChangedListener(OnFocusChangeListener listener) {
        if (mFlag) {
            mBigEt.setOnFocusChangeListener(listener);
        } else {
            mContentTextView.setOnFocusChangeListener(listener);
        }
    }

}
