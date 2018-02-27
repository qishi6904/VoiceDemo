package com.svw.dealerapp.ui.widget;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.svw.dealerapp.R;
import com.svw.dealerapp.util.ToastUtils;

/**
 * Created by qinshi on 1/12/2018.
 */

public class RdResourceItemView extends LinearLayout implements View.OnClickListener {

    private Context context;

    private ImageView ivVip;
    private TextView tvDay;
    private TextView tvTime;
    private TextView tvAction;
    private TextView tvCustomerName;
    private ImageView ivCustomerRank;
    private TextView tvCustomerSrc;
    private TextView tvApproveStatus;
    private TextView tvOrderOtd;
    private TextView tvFailReason;
    private TextView tvMobileNumber;
    private TextView tvCarType;
    private TextView tvIsDrive;
    private ImageView ivMsg;
    private ImageView ivPhone;
    private TextView tvDate;
    private TextView tvDateTitle;
    private TextView tvConsultant;
    private LinearLayout llConsultant;
    private LinearLayout llRightDate;

    private String mobileNumber;

    public RdResourceItemView(Context context) {
        super(context);
        this.context = context;
        initView(context);
    }

    public RdResourceItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView(context);
    }

    private void initView (Context context){
        View view = View.inflate(context, R.layout.rd_item_resource_common, this);
        assignViews(view);
    }

    private void assignViews(View view) {
        ivVip = (ImageView) view.findViewById(R.id.iv_vip);
        tvDay = (TextView) view.findViewById(R.id.tv_day);
        tvTime = (TextView) view.findViewById(R.id.tv_time);
        tvAction = (TextView) view.findViewById(R.id.tv_action);
        tvCustomerName = (TextView) view.findViewById(R.id.tv_customer_name);
        ivCustomerRank = (ImageView) view.findViewById(R.id.iv_customer_rank);
        tvCustomerSrc = (TextView) view.findViewById(R.id.tv_customer_src);
        tvApproveStatus = (TextView) view.findViewById(R.id.tv_customer_approve);
        tvOrderOtd = (TextView) view.findViewById(R.id.tv_order_otd);
        tvFailReason = (TextView) view.findViewById(R.id.tv_fail_reason);
        tvMobileNumber = (TextView) view.findViewById(R.id.tv_mobile_number);
        tvCarType = (TextView) view.findViewById(R.id.tv_car_type);
        tvIsDrive = (TextView) view.findViewById(R.id.tv_is_drive);
        ivMsg = (ImageView) view.findViewById(R.id.iv_msg);
        ivPhone = (ImageView) view.findViewById(R.id.iv_phone);
        tvDate = (TextView) view.findViewById(R.id.tv_date);
        tvDateTitle = (TextView) view.findViewById(R.id.tv_date_title);
        llConsultant = (LinearLayout) view.findViewById(R.id.ll_consultant);
        tvConsultant = (TextView) view.findViewById(R.id.tv_consultant);
        llRightDate = (LinearLayout) view.findViewById(R.id.ll_right_date);

        ivMsg.setOnClickListener(this);
        ivPhone.setOnClickListener(this);
    }

    public void showVip(boolean isVip) {
        if(isVip) {
            ivVip.setVisibility(VISIBLE);
        }else {
            ivVip.setVisibility(INVISIBLE);
        }
    }

    public void showOTD(boolean showOTD) {
        if(showOTD) {
            tvOrderOtd.setVisibility(View.VISIBLE);
        }else {
            tvOrderOtd.setVisibility(View.GONE);
        }
    }

    public void showCustomerRank(boolean showCustomerRank) {
        if(showCustomerRank) {
            ivCustomerRank.setVisibility(View.VISIBLE);
        }else {
            ivCustomerRank.setVisibility(View.GONE);
        }
    }

    public void showCustomerSrc(boolean showCustomerSrc) {
        if(showCustomerSrc) {
            tvCustomerSrc.setVisibility(View.VISIBLE);
        }else {
            tvCustomerSrc.setVisibility(View.GONE);
        }
    }

    public void showFailReason(boolean showFailReason) {
        if(showFailReason){
            tvFailReason.setVisibility(View.VISIBLE);
        }else {
            tvFailReason.setVisibility(View.GONE);
        }
    }

    public void showApproveStatus(boolean showApproveStatus) {
        if(showApproveStatus) {
            tvApproveStatus.setVisibility(View.VISIBLE);
        }else {
            tvApproveStatus.setVisibility(View.GONE);
        }
    }

    public void showRightDate(boolean showRightDate) {
        if(showRightDate) {
            llRightDate.setVisibility(View.VISIBLE);
        }else {
            llRightDate.setVisibility(View.GONE);
        }

    }

    public void setDay(String day) {
        tvDay.setText(day);
    }

    public void setTime(String time) {
        tvTime.setText(time);
    }

    public void setAction(String action) {
        tvAction.setText(action);
    }

    public void setCustomerName(String customerName) {
        tvCustomerName.setText(customerName);
    }

    /**
     * 设置用户级别
     * @param customerRank
     */
    public void setCustomerRank(String customerRank) {
        if (!TextUtils.isEmpty(customerRank)) {
            if ("12020".equals(customerRank)) {
                ivCustomerRank.setImageResource(R.mipmap.ic_yellow_card_customer_rank_a);
            } else if ("12030".equals(customerRank)) {
                ivCustomerRank.setImageResource(R.mipmap.ic_yellow_card_customer_rank_b);
            } else if ("12040".equals(customerRank)) {
                ivCustomerRank.setImageResource(R.mipmap.ic_yellow_card_customer_rank_c);
            } else if ("12050".equals(customerRank)) {
                ivCustomerRank.setImageResource(R.mipmap.ic_yellow_card_customer_rank_n);
            } else if ("12010".equals(customerRank)) {
                ivCustomerRank.setImageResource(R.mipmap.ic_yellow_card_customer_rank_h);
            } else {
                ivCustomerRank.setImageResource(R.mipmap.ic_yellow_card_customer_rank_n);
            }
        } else {
            ivCustomerRank.setImageResource(R.mipmap.ic_yellow_card_customer_rank_n);
        }
    }

    public void setCustomerSrc(String customerSrc) {
        tvCustomerSrc.setText(customerSrc);
    }

    public void setOrderOtd(String orderOtd) {
        tvOrderOtd.setText(orderOtd);
    }

    public void setFailReason(String failReason) {
        tvFailReason.setText(failReason);
    }

    public void setMobileNumber(String mobileNumber) {
        if(!TextUtils.isEmpty(mobileNumber)) {
            this.mobileNumber = mobileNumber;
            tvMobileNumber.setText(mobileNumber);
        }else {
            tvMobileNumber.setText(context.getString(R.string.yellow_card_empty));
        }
    }

    public void setSeriesType(String carType) {
        if(!TextUtils.isEmpty(carType)) {
            tvCarType.setText(carType);
        }else {
            tvCarType.setText(context.getString(R.string.yellow_card_empty));
        }
    }

    public void setIsDrive(boolean isDrive) {
        if(isDrive) {
            tvIsDrive.setVisibility(View.VISIBLE);
            tvIsDrive.setText(context.getResources().getString(R.string.yellow_card_has_drive));
        }else {
            tvIsDrive.setVisibility(View.GONE);
        }
    }

    public void setDate(String date) {
        tvDate.setText(date);
    }

    public void setOwner(boolean isOwner, String ownerName) {
        if(isOwner) {
            ivMsg.setVisibility(View.VISIBLE);
            ivPhone.setVisibility(View.VISIBLE);
            llConsultant.setVisibility(View.INVISIBLE);
        }else {
            ivMsg.setVisibility(View.INVISIBLE);
            ivPhone.setVisibility(View.INVISIBLE);
            llConsultant.setVisibility(View.VISIBLE);
            if(!TextUtils.isEmpty(ownerName)) {
                tvConsultant.setText(ownerName);
            }
        }
    }

    public void setDateTitle(String dateTitle) {
        tvDateTitle.setText(dateTitle);
    }

    public void setCustomerNameLayoutParams(LinearLayout.LayoutParams layoutParams) {
        tvCustomerName.setLayoutParams(layoutParams);
    }

    public void setCarTypeLayoutParams(LinearLayout.LayoutParams layoutParams) {
        tvCarType.setLayoutParams(layoutParams);
    }

    public void setCustomerNameMaxWidth(int maxWidth){
        tvCustomerName.setMaxWidth(maxWidth);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_msg:
                if (!TextUtils.isEmpty(mobileNumber)) {
                    Intent intent = new Intent(Intent.ACTION_SENDTO);
                    Uri data = Uri.parse("smsto:" + mobileNumber);
                    intent.setData(data);
                    context.startActivity(intent);
                } else {
                    ToastUtils.showToast(getResources().getString(R.string.yellow_phone_number_empty));
                }
                break;
            case R.id.iv_phone:
                if (!TextUtils.isEmpty(mobileNumber)) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    Uri data = Uri.parse("tel:" + mobileNumber);
                    intent.setData(data);
                    context.startActivity(intent);
                } else {
                    ToastUtils.showToast(getResources().getString(R.string.yellow_phone_number_empty));
                }
                break;
        }
    }
}
