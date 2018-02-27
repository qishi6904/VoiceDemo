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
import com.svw.dealerapp.entity.resource.DealCustomerEntity;
import com.svw.dealerapp.entity.resource.OrderCustomerEntity;
import com.svw.dealerapp.global.Constants;
import com.svw.dealerapp.ui.adapter.LinearDragRecyclerViewAdapter;
import com.svw.dealerapp.ui.widget.DragFrontLinearLayout;
import com.svw.dealerapp.ui.widget.DragLayout;
import com.svw.dealerapp.util.DateUtils;
import com.svw.dealerapp.util.TalkingDataUtils;
import com.svw.dealerapp.util.UserInfoUtils;
import com.svw.dealerapp.util.dbtools.PrivilegeDBUtils;

import java.util.List;

/**
 * Created by qinshi on 5/5/2017.
 */

@Deprecated
public class DealCustomerAdapter extends LinearDragRecyclerViewAdapter<DealCustomerEntity.DealCustomerInfoEntity> {

    private OnIconClickListener iconClickListener;
    private String currentUserId;

    private boolean isShowSalesName;

    public DealCustomerAdapter(Context context, List<DealCustomerEntity.DealCustomerInfoEntity> dataList) {
        super(context, dataList);
        currentUserId = UserInfoUtils.getUserId();
        isShowSalesName = PrivilegeDBUtils.isCheck(Constants.P_SA_APP_CUSTOMER_LIST, Constants.E_CUSTOMER_LIST_OWNER_NAME);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_resource_deal_customer, null);
        final Holder holder = new Holder(view);
        holder.tvResponsible.setVisibility(View.GONE);
        holder.sdvSrc.setVisibility(View.GONE);

        //禁止左滑
        LinearLayout.LayoutParams backViewParams = new LinearLayout.LayoutParams(holder.llBackView.getLayoutParams());
        backViewParams.width = 0;
        holder.dragLayout.setRange(backViewParams.width);
        holder.llBackView.setLayoutParams(backViewParams);

        setHolderListener(holder);

        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        Holder viewHolder = (Holder) holder;
        DealCustomerEntity.DealCustomerInfoEntity infoEntity = dataList.get(position);

        viewHolder.tvCustomerName.setText(infoEntity.getCustName());
        if(TextUtils.isEmpty(infoEntity.getSeriesName())){
            viewHolder.tvCarType.setText(context.getResources().getString(R.string.yellow_car_series_empty));
        }else {
            viewHolder.tvCarType.setText(infoEntity.getSeriesName());
        }
        viewHolder.tvSource.setText(infoEntity.getSrcTypeName());
        if(TextUtils.isEmpty(infoEntity.getRealDeliveryDate())){
            viewHolder.tvAction.setText(context.getResources().getString(R.string.resource_deal_cus_delivery_time_empty));
        }else {
            viewHolder.tvAction.setText(context.getResources().getString(R.string.resource_deal_cus_delivery_time) +
                    DateUtils.dateFormat("yyyy-MM-dd", "yyyy-MM-dd'T'HH:mm:ss.SSSZ", infoEntity.getRealDeliveryDate()));
        }
        viewHolder.tvDateTime.setText(DateUtils.dateFormat("MM.dd HH:mm", "yyyy-MM-dd'T'HH:mm:ss.SSSZ", infoEntity.getUpdateTime()));

        //如果当前用户不是该黄卡的owner不能点击打电话按钮
        if((TextUtils.isEmpty(infoEntity.getSalesConsultant()) || !infoEntity.getSalesConsultant().equals(currentUserId))){
            viewHolder.ivCallPhone.setImageResource(R.mipmap.ic_can_not_call);
        }else {
            viewHolder.ivCallPhone.setImageResource(R.mipmap.ic_yellow_card_item_call);
        }

        if(position == 0){
            viewHolder.vUpLine.setVisibility(View.VISIBLE);
        }else {
            viewHolder.vUpLine.setVisibility(View.GONE);
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

        //设置是否重点客户
        if("0".equals(infoEntity.getIsKeyuser())){
            viewHolder.vVipIndicator.setVisibility(View.VISIBLE);
        }else {
            viewHolder.vVipIndicator.setVisibility(View.INVISIBLE);
        }

        if(isShowSalesName){
            viewHolder.tvSalesName.setVisibility(View.VISIBLE);
            viewHolder.tvSalesName.setText(infoEntity.getSalesConsultantName());

        }else {
            viewHolder.tvSalesName.setVisibility(View.GONE);
        }

        setListener(viewHolder, infoEntity, position);
    }

    private void setListener(final Holder holder, final DealCustomerEntity.DealCustomerInfoEntity infoEntity, final int position){
        holder.ivCallPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != iconClickListener){
                    TalkingDataUtils.onEvent(context, "打电话", "资源交车客户");
                    iconClickListener.onPhoneClick(v, infoEntity);
                }
            }
        });
        holder.llFrontView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != iconClickListener){
                    TalkingDataUtils.onEvent(context, "点击进入潜客详情", "资源交车客户");
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
        private TextView tvCarType;
        private TextView tvAction;
        private TextView tvResponsible;
        private TextView tvDateTime;
        private TextView tvSalesName;
        private LinearLayout llBackViewOne;
        private ImageView ivBackViewOne;
        private LinearLayout llBackViewTwo;
        private LinearLayout llBackViewThree;
        private View vUpLine;
        private LinearLayout llFrontView;
        private LinearLayout llBackView;
        private TextView tvBackViewOne;

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
            tvCarType = (TextView) view.findViewById(R.id.tv_car_type);
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
        }
    }

    public interface OnIconClickListener{

        void onPhoneClick(View view, DealCustomerEntity.DealCustomerInfoEntity entity);

        void onFollowUpClick(View view, DealCustomerEntity.DealCustomerInfoEntity entity, int position);
    }

    public void setOnIconClickListener(OnIconClickListener onIconClickListener){
        this.iconClickListener = onIconClickListener;
    }
}
