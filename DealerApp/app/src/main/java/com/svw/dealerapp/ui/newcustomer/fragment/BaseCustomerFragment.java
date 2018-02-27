package com.svw.dealerapp.ui.newcustomer.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.svw.dealerapp.common.BaseFragment;
import com.svw.dealerapp.ui.widget.CustomItemViewBase;
import com.svw.dealerapp.util.JLog;

import java.io.Serializable;

/**
 * 黄卡和订单模块Fragment的基类(主要是新建黄卡第一二步和黄卡详情页的Fragment).
 * Created by xupan on 10/06/2018.
 */

@Deprecated
public abstract class BaseCustomerFragment<T> extends BaseFragment {

    private static final String TAG = "BaseCustomerFragment";
    protected Serializable mEntity;
    protected CustomItemViewBase[] mAllBaseView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mEntity = bundle.getSerializable("entity");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        initViews(view);
        initBaseViews();
        initOptions();
        initPickerViews();
        setListeners();
        renderView();
        return view;
    }

    protected abstract int getLayoutId();

    protected abstract void initViews(View view);

    /**
     * 将Fragment中用到的CustomItemViewBase全部添加到mAllBaseView数组中
     */
    protected void initBaseViews() {

    }

    protected void initOptions() {

    }

    protected void initPickerViews() {

    }

    protected void setListeners() {
        if (mAllBaseView == null) {
            return;
        }
        CustomItemViewBase.OnDataChangedListener listener = new CustomItemViewBase.OnDataChangedListener() {
            @Override
            public void onDateChanged(Object object) {
                onDataChanged(object);
                JLog.d(TAG, "onDateChanged: " + object);
            }
        };
        for (CustomItemViewBase view : mAllBaseView) {
            //可以修改为所有view都设置监听，但只有mandatory和enabled才触发listener的onDataChanged方法
            //以免后续动态调整view的mandatory属性后，此处未设置监听且后续也无法再设置
            if (view.isMandatory() && view.isEnabled()) {
                view.setOnDataChangedListener(listener);
            }
        }
    }

    protected abstract void renderView();

    /**
     * 获取UI上各字段内容
     */
    public T getParameters() {
        return null;
    }

    /**
     * 检查Fragment中各输入数据是否合法
     */
    public boolean checkDataCorrectness() {
        return true;
    }

    /**
     * 所有输入数据是否完整
     *
     * @return true:完整 false:不完整
     */
    public boolean checkDataValidation() {
        if (mAllBaseView == null) {
            JLog.d(TAG, "mAllBaseView == null");
            return true;
        }
        JLog.d(TAG, "mAllBaseView length: " + mAllBaseView.length);
        for (CustomItemViewBase view : mAllBaseView) {
            //只判断必填且可见项
            if (view.isMandatory() && view.isVisible()) {
                if (view.getInputData() == null || "".equals(view.getInputData()))
                    return false;
            }
        }
        return true;
    }

    protected void enableBaseViews(boolean enabled) {
        if (mAllBaseView == null) {
            return;
        }
        for (CustomItemViewBase view : mAllBaseView) {
            view.setEnabled(enabled);
            if (!enabled) {
                view.setOnDataChangedListener(null);
            }
        }
    }

    /**
     * 当监听到用户输入改变时所调用
     *
     * @param object 改变后的数据
     */
    protected void onDataChanged(Object object) {

    }

}
