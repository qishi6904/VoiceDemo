package com.svw.dealerapp.ui.resource.adapter;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.svw.dealerapp.R;
import com.svw.dealerapp.entity.resource.FailedCustomerEntity;
import com.svw.dealerapp.entity.resource.OrderCustomerEntity;
import com.svw.dealerapp.global.Constants;
import com.svw.dealerapp.ui.adapter.LinearDragRecyclerViewAdapter;
import com.svw.dealerapp.ui.widget.DragFrontLinearLayout;
import com.svw.dealerapp.ui.widget.DragLayout;
import com.svw.dealerapp.util.DateUtils;
import com.svw.dealerapp.util.DensityUtil;
import com.svw.dealerapp.util.TalkingDataUtils;
import com.svw.dealerapp.util.UserInfoUtils;
import com.svw.dealerapp.util.dbtools.PrivilegeDBUtils;

import java.util.List;

/**
 * Created by qinshi on 5/5/2017.
 */
@Deprecated
public class FailedCustomerAdapter extends LinearDragRecyclerViewAdapter<FailedCustomerEntity.FailedCustomerInfoEntity> {

    private OnIconClickListener iconClickListener;
    private String currentUserId;

    private boolean isShowSalesName;

    public FailedCustomerAdapter(Context context, List<FailedCustomerEntity.FailedCustomerInfoEntity> dataList) {
        super(context, dataList);
        currentUserId = UserInfoUtils.getUserId();
        isShowSalesName = PrivilegeDBUtils.isCheck(Constants.P_SA_APP_CUSTOMER_LIST, Constants.E_CUSTOMER_LIST_OWNER_NAME);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_resource_failed_customer, null);
        final Holder holder = new Holder(view);
        holder.tvResponsible.setVisibility(View.GONE);

        setHolderListener(holder);

        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        Holder viewHolder = (Holder) holder;
        FailedCustomerEntity.FailedCustomerInfoEntity infoEntity = dataList.get(position);

        viewHolder.tvCustomerName.setText(infoEntity.getCustName());
        viewHolder.tvFailedReason.setText(context.getResources().getString(R.string.resource_deal_cus_failed_reason) + infoEntity.getFailureDesc());
        viewHolder.tvSource.setText(infoEntity.getSrcTypeName());
        viewHolder.tvAction.setText(context.getResources().getString(R.string.resource_deal_cus_failed_time) + DateUtils.dateFormat("yyyy-MM-dd", "yyyy-MM-dd'T'HH:mm:ss.SSSZ", infoEntity.getFailureDate()));
        viewHolder.tvDateTime.setText(DateUtils.dateFormat("MM.dd HH:mm", "yyyy-MM-dd'T'HH:mm:ss.SSSZ", infoEntity.getUpdateTime()));

        if(position == 0){
            viewHolder.vUpLine.setVisibility(View.VISIBLE);
        }else {
            viewHolder.vUpLine.setVisibility(View.GONE);
        }

        //如果当前用户不是该黄卡的owner不能点击打电话按钮
        if((TextUtils.isEmpty(infoEntity.getOppOwner()) || !infoEntity.getOppOwner().equals(currentUserId))){
            viewHolder.ivCallPhone.setImageResource(R.mipmap.ic_can_not_call);
        }else {
            viewHolder.ivCallPhone.setImageResource(R.mipmap.ic_yellow_card_item_call);
        }

        //设置来源背景
        if("11010".equals(infoEntity.getSrcTypeId())){          //到店
            viewHolder.tvSource.setBackgroundDrawable(
                    context.getResources().getDrawable(R.drawable.shape_yellow_list_customer_source_shop));
        }else if("11020".equals(infoEntity.getSrcTypeId())){    //电商
            viewHolder.tvSource.setBackgroundDrawable(
                    context.getResources().getDrawable(R.drawable.shape_yellow_list_customer_source_e_commerce));
        }else if("11030".equals(infoEntity.getSrcTypeId())){    //线索
            viewHolder.tvSource.setBackgroundDrawable(
                    context.getResources().getDrawable(R.drawable.shape_yellow_list_customer_source_leads));
        }else if("11040".equals(infoEntity.getSrcTypeId())){    //电话
            viewHolder.tvSource.setBackgroundDrawable(
                    context.getResources().getDrawable(R.drawable.shape_yellow_list_customer_source_phone));
        }else if("11050".equals(infoEntity.getSrcTypeId())){    //外拓
            viewHolder.tvSource.setBackgroundDrawable(
                    context.getResources().getDrawable(R.drawable.shape_yellow_list_customer_source_activities));
        }else if("11060".equals(infoEntity.getSrcTypeId())){    //朋友推荐
            viewHolder.tvSource.setBackgroundDrawable(
                    context.getResources().getDrawable(R.drawable.shape_yellow_list_customer_source_friend));
        }else if("11099".equals(infoEntity.getSrcTypeId())){    //其他
            viewHolder.tvSource.setBackgroundDrawable(
                    context.getResources().getDrawable(R.drawable.shape_yellow_list_customer_source_other));
        }else {                                                 //默认
            viewHolder.tvSource.setBackgroundColor(Color.TRANSPARENT);
        }

        //设置来源图标
        if("11010".equals(infoEntity.getSrcTypeId()) && !TextUtils.isEmpty(infoEntity.getImageUrl())) {
            viewHolder.sdvSrc.setVisibility(View.VISIBLE);
            Uri uri = Uri.parse(infoEntity.getImageUrl());
            DraweeController draweeController =
                    Fresco.newDraweeControllerBuilder()
                            .setUri(uri)
                            .setAutoPlayAnimations(false)
                            .build();
            viewHolder.sdvSrc.setController(draweeController);
            viewHolder.vDivider.setVisibility(View.VISIBLE);
        }else {
            viewHolder.sdvSrc.setVisibility(View.INVISIBLE);
            viewHolder.vDivider.setVisibility(View.INVISIBLE);
        }

        //设置激活按钮
        LinearLayout.LayoutParams backViewParams = new LinearLayout.LayoutParams(viewHolder.llBackView.getLayoutParams());
        if("11530".equals(infoEntity.getOppStatusId())){
            viewHolder.ivBackViewOne.setImageResource(R.mipmap.ic_yellow_card_active);
            viewHolder.llBackViewOne.setBackgroundColor(context.getResources().getColor(R.color.yellow_item_active_bg));
            viewHolder.tvBackViewOne.setText(context.getResources().getString(R.string.yellow_back_view_active));
            viewHolder.llBackViewTwo.setVisibility(View.GONE);
            viewHolder.llBackViewThree.setVisibility(View.GONE);
            backViewParams.width = DensityUtil.dp2px(context, 60);
        }else {
            viewHolder.ivBackViewOne.setImageResource(R.mipmap.ic_order_customer_cancel);
            viewHolder.llBackViewOne.setBackgroundColor(context.getResources().getColor(R.color.traffic_item_back_view_one_bg));
            viewHolder.tvBackViewOne.setText(context.getResources().getString(R.string.yellow_back_view_vip));
            viewHolder.llBackViewTwo.setVisibility(View.VISIBLE);
            viewHolder.llBackViewThree.setVisibility(View.VISIBLE);
            backViewParams.width = DensityUtil.dp2px(context, 182);
        }
        viewHolder.dragLayout.setRange(backViewParams.width);
        viewHolder.llBackView.setLayoutParams(backViewParams);

        //设置是否重点客户
        if("0".equals(infoEntity.getIsKeyuser())){
            viewHolder.vVipIndicator.setVisibility(View.VISIBLE);
        }else {
            viewHolder.vVipIndicator.setVisibility(View.INVISIBLE);
        }

        if(isShowSalesName){
            viewHolder.tvSalesName.setVisibility(View.VISIBLE);
            viewHolder.tvSalesName.setText(infoEntity.getOppOwnerName());
        }else {
            viewHolder.tvSalesName.setVisibility(View.GONE);
        }

        setListener(viewHolder, infoEntity, position);
    }

    private void setListener(final Holder holder, final FailedCustomerEntity.FailedCustomerInfoEntity infoEntity, final int position){
        holder.ivCallPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != iconClickListener){
                    iconClickListener.onPhoneClick(v, infoEntity);
                }
            }
        });
        holder.llBackViewOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != iconClickListener){
                    iconClickListener.onActiveClick(v, infoEntity, position);
                }
            }
        });
        holder.llBackViewTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != iconClickListener){
                    iconClickListener.onFollowUpClick(v, infoEntity, position);
                }
            }
        });
        holder.llBackViewThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != iconClickListener){
                    iconClickListener.onTransferClick(v, infoEntity, position);
                }
            }
        });
        holder.llFrontView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != iconClickListener){
                    TalkingDataUtils.onEvent(context, "点击进入潜客详情", "资源订单客户");
                    iconClickListener.onFollowUpClick(v, infoEntity, position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return null == dataList ? 0 : dataList.size();
    }

    private class Holder extends DragLinearRecyclerViewHolder {

        private View vVipIndicator;
        private TextView tvCustomerName;
        private TextView tvSource;
        private SimpleDraweeView sdvSrc;
        private ImageView ivCallPhone;
        private TextView tvFailedReason;
        private TextView tvAction;
        private TextView tvResponsible;
        private TextView tvDateTime;
        private TextView tvSalesName;
        private LinearLayout llBackViewOne;
        private ImageView ivBackViewOne;
        private TextView tvBackViewOne;
        private LinearLayout llBackViewTwo;
        private LinearLayout llBackViewThree;
        private View vUpLine;
        private LinearLayout llFrontView;
        private LinearLayout llBackView;
        private View vDivider;

        public Holder(View itemView) {
            super(itemView);
            assignViews(itemView);
        }

        private void assignViews(View view) {
            dragLayout = (DragLayout) view.findViewById(R.id.drag_layout);
            vVipIndicator = view.findViewById(R.id.v_vip_indicator);
            frontLayout = (DragFrontLinearLayout) view.findViewById(R.id.ll_front_view);
            tvCustomerName = (TextView) view.findViewById(R.id.tv_customer_name);
            tvSource = (TextView) view.findViewById(R.id.tv_source);
            ivCallPhone = (ImageView) view.findViewById(R.id.iv_call_phone);
            sdvSrc = (SimpleDraweeView) view.findViewById(R.id.sdv_order_src);
            tvFailedReason = (TextView) view.findViewById(R.id.tv_car_type);
            tvAction = (TextView) view.findViewById(R.id.tv_action);
            tvResponsible = (TextView) view.findViewById(R.id.tv_delay_day);
            tvDateTime = (TextView) view.findViewById(R.id.tv_date_time);
            tvSalesName = (TextView) view.findViewById(R.id.tv_sales_name);
            llBackViewOne = (LinearLayout) view.findViewById(R.id.ll_back_view_one);
            ivBackViewOne = (ImageView) view.findViewById(R.id.iv_back_view_one);
            llBackViewTwo = (LinearLayout) view.findViewById(R.id.ll_back_view_two);
            llBackViewThree = (LinearLayout) view.findViewById(R.id.ll_back_view_three);
            vUpLine = view.findViewById(R.id.v_divider_up);
            llFrontView = (LinearLayout) view.findViewById(R.id.ll_front_view);
            llBackView = (LinearLayout) itemView.findViewById(R.id.ll_back_view);
            tvBackViewOne = (TextView) itemView.findViewById(R.id.tv_back_view_one);
            vDivider = itemView.findViewById(R.id.v_vertical_divider);
        }
    }

    public interface OnIconClickListener{

        void onPhoneClick(View view, FailedCustomerEntity.FailedCustomerInfoEntity entity);

        void onActiveClick(View view, FailedCustomerEntity.FailedCustomerInfoEntity entity, int position);

        void onFollowUpClick(View view, FailedCustomerEntity.FailedCustomerInfoEntity entity, int position);

        void onTransferClick(View view, FailedCustomerEntity.FailedCustomerInfoEntity entity, int position);

    }

    public void setOnIconClickListener(OnIconClickListener onIconClickListener){
        this.iconClickListener = onIconClickListener;
    }
}
