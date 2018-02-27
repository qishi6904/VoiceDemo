package com.svw.dealerapp.ui.newcustomer.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.jaygoo.widget.RangeSeekBar;
import com.svw.dealerapp.R;
import com.svw.dealerapp.entity.newcustomer.OpportunityDetailEntity;
import com.svw.dealerapp.entity.newcustomer.OpportunityUpdateReqEntity;
import com.svw.dealerapp.ui.widget.InterceptTouchCoverView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 现用车型Fragment
 * Created by xupan on 09/06/2017.
 */

public class NewCustomerCurrentCarFragment extends BaseCustomerFragment implements View.OnClickListener {

    private static final String TAG = "NewCustomerCurrentCarFragment";
    private EditText mCurrentCarTypeEt;
    private TableRow mBoughtYearTr;
    private TextView mBoughtYearTv;
    private RangeSeekBar mMileageSeekerbar;
    private RangeSeekBar mMileageGraySeekerbar;
    private TextView mMileageTv;
    private TimePickerView mBoughtYearPicker;
    private RelativeLayout rlRootView;
    private InterceptTouchCoverView itvCoverView;

    private String mBoughtYearString;
    private int mCurrentMileage;

    private boolean hasCheckCoverView = false;

    public static NewCustomerCurrentCarFragment newInstance(OpportunityDetailEntity entity) {
        NewCustomerCurrentCarFragment fragment = new NewCustomerCurrentCarFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("entity", entity);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_new_customer_step_2_info;
    }

    protected void initViews(View view) {
        mBoughtYearTr = (TableRow) view.findViewById(R.id.new_customer_step_2_info_bought_year_tr);
        mBoughtYearTv = (TextView) view.findViewById(R.id.new_customer_step_2_info_bought_year_tv);
        mCurrentCarTypeEt = (EditText) view.findViewById(R.id.new_customer_step_2_info_current_car_type_et);
        mMileageSeekerbar = (RangeSeekBar) view.findViewById(R.id.new_customer_step_2_mileage_seekbar);
        mMileageGraySeekerbar = (RangeSeekBar) view.findViewById(R.id.new_customer_step_2_mileage_gray_seekbar);
        mMileageTv = (TextView) view.findViewById(R.id.new_yellow_card_step_2_mileage_tv);
        rlRootView = (RelativeLayout) view.findViewById(R.id.rl_root_view);
        itvCoverView = (InterceptTouchCoverView) view.findViewById(R.id.itv_cover_view);

        mMileageSeekerbar.setOnRangeChangedListener(new RangeSeekBar.OnRangeChangedListener() {
            @Override
            public void onRangeChanged(RangeSeekBar view, float min, float max, boolean isFromUser) {
                mMileageTv.setText(String.format(Locale.CHINA, "%dkm", (int) min));
                mCurrentMileage = (int) min;
            }
        });

        // 设置根视图的布局监听，如果当前用户是不该黄卡的owner，获取根视图的高设置给遮盖层，显示遮盖层，并拦截消费掉事件
        rlRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (null == mEntity) {
                    return;
                }
                if (!hasCheckCoverView) {
                    hasCheckCoverView = true;
                    OpportunityDetailEntity entity = (OpportunityDetailEntity) mEntity;
                    if (!entity.isYellowCardOwner() || entity.isSleepStatus() || entity.isFailedStatus()) {   // 当前用户不是该黄卡的owner或该黄卡处在休眠/战败状态
                        if (View.VISIBLE != itvCoverView.getVisibility()) {
                            itvCoverView.setVisibility(View.VISIBLE);
                            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(itvCoverView.getLayoutParams());
                            params.height = rlRootView.getHeight();
                            itvCoverView.setLayoutParams(params);
                        }
                    }
                }
            }
        });
    }

    protected void initPickerViews() {
        int endYear = Calendar.getInstance().get(Calendar.YEAR);
        int startYear = endYear - 15;//跟ios保持一致，开始年份为结束年份往前15年
        mBoughtYearPicker = new TimePickerView.Builder(getContext(), new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
                mBoughtYearString = simpleDateFormat.format(date);
                mBoughtYearTv.setText(mBoughtYearString);
            }
        }).setType(new boolean[]{true, false, false, false, false, false})
                .setRange(startYear, endYear).build();
    }

    protected void setListeners() {
        mBoughtYearTr.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.new_customer_step_2_info_bought_year_tr:
                mBoughtYearPicker.show();
                break;
        }
    }

    public OpportunityUpdateReqEntity getParameters() {
        OpportunityUpdateReqEntity entity = new OpportunityUpdateReqEntity();
        entity.setCurrentModel(mCurrentCarTypeEt.getText().toString().trim());//现用车型
        entity.setModelYear(mBoughtYearString);//购车年份
        entity.setCurrentMileage(String.valueOf(mCurrentMileage));//当前里程
        return entity;
    }

    public boolean checkDataValidation() {
        return true;
    }

    @Override
    protected void renderView() {
        if (mEntity == null) {
            return;
        }
        OpportunityDetailEntity entity = (OpportunityDetailEntity) mEntity;
        mCurrentCarTypeEt.setText(entity.getCurrentModel());
        String mileage = entity.getCurrentMileage();
        if (!TextUtils.isEmpty(mileage)) {
            // 当前用户不是该黄卡的owner或该黄卡处在休眠/战败状态
            if (!entity.isYellowCardOwner() || entity.isSleepStatus() || entity.isFailedStatus()) {
                mMileageGraySeekerbar.setVisibility(View.VISIBLE);
                mMileageSeekerbar.setVisibility(View.GONE);
                if (!TextUtils.isEmpty(mileage)) {
                    mMileageGraySeekerbar.setValue(Float.valueOf(mileage));
                    mCurrentMileage = Integer.valueOf(mileage);
                }
            } else {
                mMileageSeekerbar.setValue(Float.valueOf(mileage));
                mCurrentMileage = Integer.valueOf(mileage);
            }
            mMileageTv.setText(String.format(Locale.CHINA, "%dkm", Integer.valueOf(mileage)));
        }
        String boughtYear = entity.getModelYear();
        //校验合法的购车年份长度
        if (!TextUtils.isEmpty(boughtYear) && boughtYear.length() >= 4) {
            mBoughtYearTv.setText(boughtYear);
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, Integer.valueOf(boughtYear.substring(0, 4)));
            mBoughtYearPicker.setDate(calendar);
            mBoughtYearString = boughtYear;
        }
    }

}
