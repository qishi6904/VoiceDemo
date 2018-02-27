package com.svw.dealerapp.ui.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.svw.dealerapp.R;
import com.svw.dealerapp.entity.newcustomer.CarTypesEntity;
import com.svw.dealerapp.ui.login.SimpleMessageDialogAdapter;
import com.svw.dealerapp.util.JLog;

/**
 * 意向车型输入控件
 * Created by xupan on 17/07/2017.
 */

public class CustomItemViewForCarType extends CustomItemViewBase<String, String> {

    private static final String TAG = "CustomItemViewForCarType";
    private RelativeLayout mOuterRl;
    private EditText mCarTypeEt;
    private TextView mCarTypeNameTv;
    private TextView mItemStarTv;
    private TextView mItemTitleTv;
    private CarTypeCallback mCarTypeCallback;
    private boolean mShowFailureDialog = true;

    public CustomItemViewForCarType(Context context) {
        super(context);
    }

    public CustomItemViewForCarType(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.custom_item_car_type;
    }

    @Override
    public String getInputData() {
        return mCarTypeEt.getText().toString();
    }

    @Override
    protected void initViews() {
        super.initViews();
        mCarTypeEt = (EditText) mRootView.findViewById(R.id.new_customer_step_2_intention_car_type_et);
        mCarTypeNameTv = (TextView) mRootView.findViewById(R.id.new_customer_step_2_intention_car_type_tv);
        mItemStarTv = (TextView) mRootView.findViewById(R.id.custom_item_star_tv);
        mItemTitleTv = (TextView) mRootView.findViewById(R.id.custom_item_title_tv);
        mOuterRl = (RelativeLayout) mRootView.findViewById(R.id.rl_outer);
        mCarTypeEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                JLog.d(TAG, "s: " + s);
                String result = s.toString().trim();
                if (result.length() == 6) {
                    queryCarType(result);
                } else if (result.length() < 6) {
                    mInputValid = false;
                    mCarTypeNameTv.setVisibility(View.GONE);
                }
                if (mOnDataChangedListener != null) {
                    mOnDataChangedListener.onDateChanged(s);
                }
            }
        });
    }

    public void onQueryCarTypeSuccess(CarTypesEntity entity) {
        mInputValid = true;
        mCarTypeNameTv.setVisibility(View.VISIBLE);
        mCarTypeNameTv.setText(entity.getModelDescCn());
//        mCurrentOuterColor = "";
//        mCurrentInnerColor = "";
//        initCarTypeColorOptions();
//        initCarTypePickers();
//        renderPickerSelection();
    }

    public void onQueryCarTypeFailure(String msg) {
        if (!mShowFailureDialog) {
            return;
        }
        SimpleMessageDialogAdapter dialogAdapter = new SimpleMessageDialogAdapter();
        final CustomDialog carTypeDialog = new CustomDialog(getContext(), dialogAdapter);
        dialogAdapter.setDialogMessageText(msg);
        carTypeDialog.setBtnCancelText(getContext().getString(R.string.dialog_cancel));
        carTypeDialog.setBtnConfirmText(getContext().getString(R.string.customer_detail_car_type_try_again));
        carTypeDialog.setOnBtnClickListener(new CustomDialog.OnBtnClickListener() {
            @Override
            public void onCancelBtnClick() {
                carTypeDialog.dismiss();
            }

            @Override
            public void onConfirmBtnClick() {
                carTypeDialog.dismiss();
            }
        });
        carTypeDialog.show();
    }

    private void queryCarType(String carTypeId) {
        if (mCarTypeCallback != null) {
            mCarTypeCallback.queryCarType(carTypeId);
        }
    }

    /**
     * 设置意向车型查询的回调
     *
     * @param callback
     */
    public void setCallback(CarTypeCallback callback) {
        mCarTypeCallback = callback;
    }

    public interface CarTypeCallback {
        /**
         * 发起网络请求查询意向车型
         *
         * @param carTypeId 意向车型Id
         */
        void queryCarType(String carTypeId);
    }

    @Override
    public void setData(String data) {
        mCarTypeEt.setText(data);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        mEnabled = enabled;
        mCarTypeEt.setEnabled(enabled);
    }

    /**
     * 是否显示查询车型失败的dialog
     *
     * @param show
     */
    public void showFailureDialog(boolean show) {
        mShowFailureDialog = show;
    }

    public void setTextSize(int textSize) {
        mItemTitleTv.setTextSize(textSize);
        mCarTypeEt.setTextSize(textSize);
    }

    public void setTitleTextColor(int color) {
        mItemTitleTv.setTextColor(color);
    }

    public void setStarTextSize(int textSize) {
        mItemStarTv.setTextSize(textSize);
    }


    public void setHintColor(int hintColor) {
        mCarTypeEt.setHintTextColor(hintColor);
    }

    public void setPadding(int paddingRight) {
        mOuterRl.setPadding(0, 0, paddingRight, 0);
    }

}
