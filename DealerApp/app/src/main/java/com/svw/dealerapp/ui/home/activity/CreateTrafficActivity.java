package com.svw.dealerapp.ui.home.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.svw.dealerapp.R;
import com.svw.dealerapp.common.BaseActivity;
import com.svw.dealerapp.entity.home.ActivityEntity;
import com.svw.dealerapp.global.NewCustomerConstants;
import com.svw.dealerapp.ui.home.contract.CreateTrafficContract;
import com.svw.dealerapp.ui.home.model.CreateTrafficModel;
import com.svw.dealerapp.ui.home.presenter.CreateTrafficPresenter;
import com.svw.dealerapp.ui.newcustomer.fragment.CarTypeFragment;
import com.svw.dealerapp.ui.widget.CustomItemViewBase;
import com.svw.dealerapp.ui.widget.CustomItemViewForCheckbox;
import com.svw.dealerapp.ui.widget.CustomItemViewForEditText;
import com.svw.dealerapp.ui.widget.CustomItemViewForEditTextWithMic;
import com.svw.dealerapp.ui.widget.CustomItemViewForOptionsPicker;
import com.svw.dealerapp.ui.widget.CustomItemViewForRadioButton;
import com.svw.dealerapp.ui.widget.LoadingDialog;
import com.svw.dealerapp.util.CommonUtils;
import com.svw.dealerapp.util.PermissionUtils;
import com.svw.dealerapp.util.ScreenUtils;
import com.svw.dealerapp.util.StringUtils;
import com.svw.dealerapp.util.TalkingDataUtils;
import com.svw.dealerapp.util.ToastUtils;
import com.svw.dealerapp.util.UserInfoUtils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by qinshi on 6/5/2017.
 */
public class CreateTrafficActivity extends BaseActivity implements CreateTrafficContract.View {

    private static final int maxRemarkInputLength = 50;
    private ImageView ivBack;
    private Button btnSubmit;
    private CustomItemViewForEditText mNameView, mMobileView;
    private CustomItemViewForRadioButton mGenderView;
    private CustomItemViewForEditTextWithMic mRemarkView;
    private CustomItemViewForOptionsPicker extendPickerView, srcTypePickerView, mediaPickerView;
    private CustomItemViewForCheckbox mIsRecommendedView;
    private CustomItemViewForEditText mRecommendedNameView, mRecommendedMobileView;
    private LoadingDialog loadingDialog;
    private CreateTrafficPresenter presenter;
    private CarTypeFragment mCarTypeFragment;
    private RelativeLayout rlCheckRepeat;
    private boolean isFriend = false;
    protected CustomItemViewBase[] customItemViewBases;
    
    private Rect rect = new Rect();
    private ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_traffic);
        assignViews();

        presenter = new CreateTrafficPresenter(this, new CreateTrafficModel());

        ivBack.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        rlCheckRepeat.setOnClickListener(this);

        mNameView.setOnDataChangedListener(new CustomItemViewBase.OnDataChangedListener() {
            @Override
            public void onDateChanged(Object object) {
                setBtnColorByCheckText();
            }
        });
        mMobileView.setOnDataChangedListener(new CustomItemViewBase.OnDataChangedListener() {
            @Override
            public void onDateChanged(Object object) {
                setBtnColorByCheckText();
            }
        });

        srcTypePickerView.setOnDataChangedListener(new CustomItemViewBase.OnDataChangedListener() {
            @Override
            public void onDateChanged(Object object) {
                String srcTypeId = (String) object;

                //如果是外拓
                if (!TextUtils.isEmpty(srcTypeId) && srcTypeId.equals("11050")) {
                    extendPickerView.setVisibility(View.VISIBLE);
                } else {
                    extendPickerView.setVisibility(View.GONE);
                }

                //如查是来电
                if (!TextUtils.isEmpty(srcTypeId) && srcTypeId.equals("11040")) {
                    mediaPickerView.setVisibility(View.VISIBLE);
                } else {
                    mediaPickerView.setVisibility(View.GONE);
                    mediaPickerView.clearSelection();
                }

                setBtnColorByCheckText();
            }
        });

        mediaPickerView.setOnDataChangedListener(new CustomItemViewBase.OnDataChangedListener() {
            @Override
            public void onDateChanged(Object object) {
                setBtnColorByCheckText();
            }
        });

        extendPickerView.setOnDataChangedListener(new CustomItemViewBase.OnDataChangedListener() {
            @Override
            public void onDateChanged(Object object) {
                setBtnColorByCheckText();
            }
        });


        mIsRecommendedView.setOnDataChangedListener(new CustomItemViewBase.OnDataChangedListener() {
            @Override
            public void onDateChanged(Object object) {
                isFriend = (Boolean) object;
                mRecommendedNameView.setVisibility(isFriend ? View.VISIBLE : View.GONE);
                mRecommendedMobileView.setVisibility(isFriend ? View.VISIBLE : View.GONE);
                setBtnColorByCheckText();
            }
        });
        mRecommendedNameView.setOnDataChangedListener(new CustomItemViewBase.OnDataChangedListener() {
            @Override
            public void onDateChanged(Object object) {
                setBtnColorByCheckText();
            }
        });
        mRecommendedMobileView.setOnDataChangedListener(new CustomItemViewBase.OnDataChangedListener() {
            @Override
            public void onDateChanged(Object object) {
                setBtnColorByCheckText();
            }
        });
        mRemarkView.setOnDataChangedListener(new CustomItemViewBase.OnDataChangedListener() {
            @Override
            public void onDateChanged(Object object) {
                setBtnColorByCheckText();
            }
        });

        mGenderView.setData("0");
        setSrcTypePickerData();
        presenter.getActivityList(this);

    }

    private void assignViews() {
        ivBack = (ImageView) findViewById(R.id.iv_back);
        btnSubmit = (Button) findViewById(R.id.btn_submit);
        rlCheckRepeat = (RelativeLayout) findViewById(R.id.rl_check_repeat);
        scrollView = (ScrollView) findViewById(R.id.sv_scroll_view);

        mNameView = (CustomItemViewForEditText) findViewById(R.id.et_name);
        mNameView.setMandatory(true);
        mNameView.setTitleText(R.string.new_customer_name);
        mNameView.setHintTextForContentView(R.string.new_customer_name_hint);
        mNameView.setMaxLengthForContentView(20);
        mNameView.requestFocus();
        mGenderView = (CustomItemViewForRadioButton) findViewById(R.id.ret_gender);
        mGenderView.setMandatory(true);
        mGenderView.setTitleText(R.string.new_customer_gender);
        CommonUtils.initOptionsView(NewCustomerConstants.genderMap, mGenderView);
        mMobileView = (CustomItemViewForEditText) findViewById(R.id.et_phone);
        mMobileView.setMandatory(true);
        mMobileView.setTitleText(R.string.new_customer_mobile);
        mMobileView.setHintTextForContentView(R.string.new_customer_mobile_hint);
        mMobileView.limitContentToNumber();
        mMobileView.setMaxLengthForContentView(11);

        //推荐人
        mIsRecommendedView = (CustomItemViewForCheckbox) findViewById(R.id.cb_recommended_view);
        mIsRecommendedView.setTitleText(R.string.new_customer_recommended_by_friend);
        mRecommendedNameView = (CustomItemViewForEditText) findViewById(R.id.et_recommended_name_view);
        mRecommendedNameView.setMandatory(true);
        mRecommendedNameView.setTitleText(R.string.new_customer_jipan_customer);
        mRecommendedNameView.setHintTextForContentView(R.string.new_customer_jipan_customer_hint);
        mRecommendedNameView.setMaxLengthForContentView(20);
        mRecommendedMobileView = (CustomItemViewForEditText) findViewById(R.id.et_recommended_mobile_view);
        mRecommendedMobileView.setMandatory(true);
        mRecommendedMobileView.limitContentToNumber();
        mRecommendedMobileView.setMaxLengthForContentView(11);
        mRecommendedMobileView.setTitleText(R.string.new_customer_jipan_customer_contact);
        mRecommendedMobileView.setHintTextForContentView(R.string.new_customer_jipan_customer_contact_hint);

        //客流来源
        srcTypePickerView = (CustomItemViewForOptionsPicker) findViewById(R.id.srcType_picker_view);
        srcTypePickerView.setMandatory(true);
        srcTypePickerView.setTitleText(getResources().getString(R.string.home_create_traffic_source_type));
        srcTypePickerView.setHintTextForContentView(getResources().getString(R.string.home_create_traffic_source_type_hint));

        //媒体
        mediaPickerView =  (CustomItemViewForOptionsPicker) findViewById(R.id.media_picker_view);
        mediaPickerView.setMandatory(true);
        mediaPickerView.setTitleText(getResources().getString(R.string.home_create_traffic_media));
        mediaPickerView.setHintTextForContentView(getResources().getString(R.string.home_create_traffic_media_hint));
        CommonUtils.initOptionsView(NewCustomerConstants.phoneMediaMap, mediaPickerView);

        //外拓
        extendPickerView = (CustomItemViewForOptionsPicker) findViewById(R.id.extend_picker_view);
        extendPickerView.setMandatory(true);
        extendPickerView.setTitleText(getResources().getString(R.string.home_create_traffic_info_activity));
        extendPickerView.setHintTextForContentView(getResources().getString(R.string.home_create_traffic_info_select_activity));
//        extendPickerView.setTextSize(14);
//        extendPickerView.setStarTextSize(18);
//        extendPickerView.setHintColor(Color.parseColor("#999999"));
//        extendPickerView.setTitleTextColor(getResources().getColor(R.color.resource_main_text));
//        extendPickerView.setLeftRightMargin(DensityUtil.dp2px(this, 20),DensityUtil.dp2px(this, 20));
//        extendPickerView.setBottomLineMargin(0, 0);

        //车系车型
        mCarTypeFragment = CarTypeFragment.newInstance(null);
        mCarTypeFragment.setViewMandatory(true, false, false);
//        mCarTypeFragment.setResetSubViewAttri(true,1);
        getSupportFragmentManager().beginTransaction().add(R.id.car_series_model_view, mCarTypeFragment).commit();

        //备注
        mRemarkView = (CustomItemViewForEditTextWithMic) findViewById(R.id.et_with_mic);
        mRemarkView.setMandatory(true);
        mRemarkView.setTitleText(R.string.new_customer_remark);
        mRemarkView.setMaxTextNum(maxRemarkInputLength);
        mRemarkView.initMicEditText(this, null);

        customItemViewBases = new CustomItemViewBase[]{mNameView, mGenderView, mMobileView,
                mRecommendedNameView, mRecommendedMobileView,
                srcTypePickerView, extendPickerView, mRemarkView};

        if(Build.VERSION.SDK_INT >= 21) {
            mRemarkView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    if (mRemarkView.hasFocus()) {
                        getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
                        if (rect.bottom < ScreenUtils.getScreenHeight(CreateTrafficActivity.this)) {
                            int paddingBottom = ScreenUtils.getScreenHeight(CreateTrafficActivity.this) - (rect.bottom - rect.top) -
                                    getResources().getDimensionPixelOffset(R.dimen.activity_title_bar_height) - ScreenUtils.getStatusBarHeight(CreateTrafficActivity.this);
                            if (scrollView.getPaddingBottom() != paddingBottom) {
                                scrollView.setPadding(0, 0, 0, paddingBottom);
                                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                                mRemarkView.requestFocus();
                            }
                        } else {
                            if (scrollView.getPaddingBottom() != 0) {
                                scrollView.setPadding(0, 0, 0, 0);
                            }
                        }
                    }
                }
            });
        }
    }

    private void setExtendPickerData(List<ActivityEntity.ActivityInfoBean> activityInfoList) {
        Map<String, String> map = new LinkedHashMap<>();
        for (ActivityEntity.ActivityInfoBean activityInfoBean : activityInfoList) {
            map.put(activityInfoBean.getActivityId(), activityInfoBean.getActivitySubject());
        }
        CommonUtils.initOptionsView(map, extendPickerView);
    }

    private void setSrcTypePickerData() {
        CommonUtils.initOptionsView(NewCustomerConstants.srcTypeMap, srcTypePickerView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_submit:
                if (!checkDataValidation(true)) {
                    return;
                }
                if (!remindPhoneIsValid(mMobileView.getInputData(), getResources().getString(R.string.home_create_traffic_phone_error_tip))) {
                    return;
                }
                if (isFriend && !remindPhoneIsValid(mRecommendedMobileView.getInputData(), getResources().getString(R.string.home_create_traffic_rec_mobile_error_tip))) {
                    return;
                }
                Map<String, Object> params = new HashMap<>();
                params.put("custName", mNameView.getInputData());
                params.put("custGender", mGenderView.getInputData());
                params.put("custMobile", mMobileView.getInputData());
                params.put("custDescription", mRemarkView.getInputData());
                params.put("seriesId", mCarTypeFragment.getSeriesId());
                params.put("carModelId", mCarTypeFragment.getModelId());
                params.put("srcTypeId", srcTypePickerView.getInputData());
                params.put("activityId", extendPickerView.getInputData());
                if(!TextUtils.isEmpty(mediaPickerView.getInputData())) {
                    params.put("channelId", mediaPickerView.getInputData());
                }
                if (isFriend) {
                    params.put("isFriend", "0");
                    params.put("recomName", mRecommendedNameView.getInputData());
                    params.put("recomMobile", mRecommendedMobileView.getInputData());
                } else {
                    params.put("isFriend", "1");
                }
                TalkingDataUtils.onEvent(this, "提交客流", "创建客流");
                presenter.createTraffic(this, params);
                break;
            case R.id.rl_check_repeat:
                String mobile = mMobileView.getInputData().trim();
                if (TextUtils.isEmpty(mobile)) {
                    ToastUtils.showToast(getResources().getString(R.string.home_create_traffic_phone_empty_tip));
                    return;
                }
                if (!StringUtils.isMatchPattern("^1[34578]\\d{9}$", mobile)) {
                    ToastUtils.showToast(getResources().getString(R.string.home_create_traffic_phone_error_tip));
                    return;
                }
                Map<String, Object> options = new HashMap<>();
                options.put("leadsId","");
                options.put("mobile",mobile);
                options.put("orgId",UserInfoUtils.getOrgId());
                presenter.checkRepeat(this, options);
                break;
        }
    }

    /**
     * mobile phone check
     */
    public boolean remindPhoneIsValid(String phone, String msg) {
        if (!StringUtils.isMatchPattern("^1[34578]\\d{9}$", phone)) {
            ToastUtils.showToast(msg);
            return false;
        }
        return true;
    }

    @Override
    public void createTrafficSuccess() {
        ToastUtils.showToast(getResources().getString(R.string.home_create_traffic_success));
        finish();
    }

    @Override
    public void getActivityListSuccess(List<ActivityEntity.ActivityInfoBean> result) {
        if (result != null && result.size() > 0) {
            setExtendPickerData(result);
        }
    }

    @Override
    public void getActivityListFail(String message) {
        ToastUtils.showToast(message);
    }

    @Override
    public void checkRepeatSuccess(int repeatNum) {
        if (repeatNum == 0) {
            ToastUtils.showToast(getResources().getString(R.string.home_create_traffic_check_repeat_no_repeat));
        } else {
            ToastUtils.showToast(getResources().getString(R.string.home_create_traffic_check_repeat_tip_1) + repeatNum +
                    getResources().getString(R.string.home_create_traffic_check_repeat_tip_2));
        }

    }

    @Override
    public void checkRepeatFailed() {
        ToastUtils.showToast(getResources().getString(R.string.home_create_traffic_check_repeat_failed));
    }

    @Override
    public void showGetActivityListTimeout() {
        ToastUtils.showToast(getResources().getString(R.string.home_create_traffic_get_activity_time_out));
    }

    @Override
    public void showCheckRepeatTimeout() {
        ToastUtils.showToast(getResources().getString(R.string.home_create_traffic_check_repeat_time_out));
    }

    @Override
    public void showServerErrorToast() {
        ToastUtils.showToast(getResources().getString(R.string.home_create_traffic_fail));
    }

    @Override
    public void showTimeOutToast() {
        ToastUtils.showToast(getResources().getString(R.string.home_create_traffic_timeout));
    }

    @Override
    public void showNetWorkErrorToast() {
        ToastUtils.showToast(getResources().getString(R.string.server_error));
    }

    @Override
    public void showLoadingDialog() {
        if (null == loadingDialog) {
            loadingDialog = new LoadingDialog(this);
        }
        loadingDialog.show();
    }

    @Override
    public void hideLoadingDialog() {
        if (null != loadingDialog) {
            loadingDialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != presenter) {
            presenter.onDestroy();
        }
        mRemarkView.stopNLS();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PermissionUtils.REQUEST_AUDIO_RECORD_PERMISSION:
                if (Manifest.permission.RECORD_AUDIO.equals(permissions[0]) && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mRemarkView.clickRecordButton();
                }
                break;
        }
    }

    private void setBtnColorByCheckText() {
        setSubmitBtnColor();
    }

    /**
     * 根据输入情况显示提交按钮颜色
     */
    public void setSubmitBtnColor() {
        if (checkDataValidation(false)) {
            btnSubmit.setBackgroundColor(getResources().getColor(R.color.new_yellow_card_dark_blue));
        } else {
            btnSubmit.setBackgroundColor(getResources().getColor(R.color.gray_999));
        }
    }

    private boolean checkDataValidation(boolean isRemind) {
        for (CustomItemViewBase view : customItemViewBases) {
            //只判断必填且可见项
            if (view.isMandatory() && view.isVisible()) {
                if (view.getInputData() == null || "".equals(view.getInputData())) {
                    if (isRemind) {
                        if (view == mRemarkView) {
                            ToastUtils.showToast(getResources().getString(R.string.home_create_traffic_remark_empty_tip));
                        } else {
                            ToastUtils.showToast(view.getHintText());
                        }
                    }
                    return false;
                }
            }
        }
        if("11040".equals(srcTypePickerView.getInputData())){
            if(TextUtils.isEmpty(mediaPickerView.getInputData())){
                if(isRemind) {
                    ToastUtils.showToast(getResources().getString(R.string.home_create_traffic_media_hint));
                }
                return false;
            }
        }
        if (TextUtils.isEmpty(mCarTypeFragment.getSeriesId())) {
            if (isRemind) {
                ToastUtils.showToast(getResources().getString(R.string.home_create_traffic_rec_car_series_empty_tip));
            }
            return false;
        }
        return true;
    }

}
