package com.svw.dealerapp.ui.task.adapter;

import android.content.Context;
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
import com.svw.dealerapp.entity.task.TaskECommerceEntity.TaskECommerceInfoEntity;
import com.svw.dealerapp.ui.adapter.LinearDragRecyclerViewAdapter;
import com.svw.dealerapp.ui.widget.DragFrontLinearLayout;
import com.svw.dealerapp.ui.widget.DragLayout;
import com.svw.dealerapp.util.DateUtils;
import com.svw.dealerapp.util.TalkingDataUtils;

import java.util.List;

/**
 * Created by qinshi on 5/5/2017.
 */

public class TaskECommerceAdapter extends LinearDragRecyclerViewAdapter<TaskECommerceInfoEntity> {

    private OnIconClickListener iconClickListener;

    public TaskECommerceAdapter(Context context, List<TaskECommerceInfoEntity> dataList) {
        super(context, dataList);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_task_e_commerce, null);
        final TaskECommerceHolder holder = new TaskECommerceHolder(view);

        setHolderListener(holder);

        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        TaskECommerceHolder eCommerceHolder = (TaskECommerceHolder) holder;
        TaskECommerceInfoEntity infoEntity = dataList.get(position);

        if(position == 0){
            eCommerceHolder.vUpLine.setVisibility(View.VISIBLE);
        }else {
            eCommerceHolder.vUpLine.setVisibility(View.GONE);
        }

        eCommerceHolder.tvCustomerName.setText(infoEntity.getCustName());
        if(TextUtils.isEmpty(infoEntity.getSeriesName())){
            eCommerceHolder.tvCarType.setText(context.getResources().getString(R.string.yellow_car_series_empty));
        }else {
            eCommerceHolder.tvCarType.setText(infoEntity.getSeriesName());
        }
        if(!TextUtils.isEmpty(infoEntity.getDelayDays())) {
            eCommerceHolder.tvDelayDay.setText(context.getResources().getString(R.string.task_e_commerce_delay) +
                    infoEntity.getDelayDays() + context.getResources().getString(R.string.task_e_commerce_day));
        }else {
            eCommerceHolder.tvDelayDay.setText("");
        }
        eCommerceHolder.tvAction.setText(infoEntity.getScheduleDesc());
        eCommerceHolder.tvDateTime.setText(DateUtils.dateFormat("MM.dd HH:mm", "yyyy-MM-dd'T'HH:mm:ss.SSSZ", infoEntity.getCreateTime()));

        //设置来源图标
//        Uri uri = Uri.parse("http://imgsrc.baidu.com/baike/pic/item/aa64034f78f0f73602aa31780855b319ebc41376.jpg");
        Uri uri = Uri.parse(infoEntity.getImageUrl());
        DraweeController draweeController =
                Fresco.newDraweeControllerBuilder()
                        .setUri(uri)
                        .setAutoPlayAnimations(false)
                        .build();
        eCommerceHolder.sdvSourceIcon.setController(draweeController);

        //设置是否重点客户
        if("0".equals(infoEntity.getIsKeyuser())){
            eCommerceHolder.vVipIndicator.setVisibility(View.VISIBLE);
            eCommerceHolder.ivBackViewOne.setImageResource(R.mipmap.ic_yellow_card_item_vip_selected);
        }else {
            eCommerceHolder.vVipIndicator.setVisibility(View.INVISIBLE);
            eCommerceHolder.ivBackViewOne.setImageResource(R.mipmap.ic_yellow_card_item_vip_unselected);
        }

        //设置用户级别
        if(!TextUtils.isEmpty(infoEntity.getOppLevel())) {
            if ("12020".equals(infoEntity.getOppLevel())) {
                eCommerceHolder.ivCustomerRank.setImageResource(R.mipmap.ic_yellow_card_customer_rank_a);
            } else if ("12030".equals(infoEntity.getOppLevel())) {
                eCommerceHolder.ivCustomerRank.setImageResource(R.mipmap.ic_yellow_card_customer_rank_b);
            } else if ("12040".equals(infoEntity.getOppLevel())) {
                eCommerceHolder.ivCustomerRank.setImageResource(R.mipmap.ic_yellow_card_customer_rank_c);
            } else if ("12050".equals(infoEntity.getOppLevel())) {
                eCommerceHolder.ivCustomerRank.setImageResource(R.mipmap.ic_yellow_card_customer_rank_n);
            } else if ("12010".equals(infoEntity.getOppLevel())) {
                eCommerceHolder.ivCustomerRank.setImageResource(R.mipmap.ic_yellow_card_customer_rank_h);
            }else {
                eCommerceHolder.ivCustomerRank.setImageResource(R.mipmap.ic_yellow_card_customer_rank_n);
            }
        }else {
            eCommerceHolder.ivCustomerRank.setImageResource(R.mipmap.ic_yellow_card_customer_rank_n);
        }

        setListener(eCommerceHolder, infoEntity, position);
    }

    private void setListener(final TaskECommerceHolder eCommerceHolder, final TaskECommerceInfoEntity infoEntity, final int position){
        eCommerceHolder.ivCallPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != iconClickListener){
                    iconClickListener.onPhoneClick(v, infoEntity);
                }
            }
        });
        eCommerceHolder.llBackViewOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != iconClickListener){
                    iconClickListener.onVipClick(v, infoEntity, position);
                }
            }
        });
        eCommerceHolder.llBackViewTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != iconClickListener){
                    iconClickListener.onFollowUpClick(v, infoEntity, position);
                }
                eCommerceHolder.dragLayout.closeDelay(600);
            }
        });
        eCommerceHolder.llBackViewThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != iconClickListener){
                    iconClickListener.onTransferClick(v, infoEntity, position);
                }
            }
        });
        eCommerceHolder.sdvSourceIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != iconClickListener){
                    iconClickListener.onBenefitClick(v, infoEntity, position);
                }
            }
        });
        eCommerceHolder.llFrontView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != iconClickListener){
                    TalkingDataUtils.onEvent(context, "点击进入潜客详情", "电商任务");
                    iconClickListener.onFollowUpClick(v, infoEntity, position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return null == dataList ? 0 : dataList.size();
    }

    private class TaskECommerceHolder extends DragLinearRecyclerViewHolder {

        private View vVipIndicator;
        private TextView tvCustomerName;
        private ImageView ivCustomerRank;
        private ImageView ivCallPhone;
        private SimpleDraweeView sdvSourceIcon;
        private ImageView ivBenefitIcon;
        private TextView tvCarType;
        private TextView tvAction;
        private TextView tvDelayDay;
        private TextView tvDateTime;
        private LinearLayout llBackViewOne;
        private ImageView ivBackViewOne;
        private LinearLayout llBackViewTwo;
        private LinearLayout llBackViewThree;
        private View vUpLine;
        private LinearLayout llFrontView;

        public TaskECommerceHolder(View itemView) {
            super(itemView);
            assignViews(itemView);
        }

        private void assignViews(View view) {
            dragLayout = (DragLayout) view.findViewById(R.id.drag_layout);
            vVipIndicator = view.findViewById(R.id.v_vip_indicator);
            frontLayout = (DragFrontLinearLayout) view.findViewById(R.id.ll_front_view);
            tvCustomerName = (TextView) view.findViewById(R.id.tv_customer_name);
            ivCustomerRank = (ImageView) view.findViewById(R.id.iv_customer_rank);
            ivCallPhone = (ImageView) view.findViewById(R.id.iv_call_phone);
            tvCarType = (TextView) view.findViewById(R.id.tv_car_type);
            sdvSourceIcon = (SimpleDraweeView) view.findViewById(R.id.sdv_source_icon);
            ivBenefitIcon = (ImageView) view.findViewById(R.id.iv_benefit_icon);
            tvAction = (TextView) view.findViewById(R.id.tv_action);
            tvDelayDay = (TextView) view.findViewById(R.id.tv_delay_day);
            tvDateTime = (TextView) view.findViewById(R.id.tv_date_time);
            llBackViewOne = (LinearLayout) view.findViewById(R.id.ll_back_view_one);
            ivBackViewOne = (ImageView) view.findViewById(R.id.iv_back_view_one);
            llBackViewTwo = (LinearLayout) view.findViewById(R.id.ll_back_view_two);
            llBackViewThree = (LinearLayout) view.findViewById(R.id.ll_back_view_three);
            vUpLine = itemView.findViewById(R.id.v_divider_up);
            llFrontView = (LinearLayout) view.findViewById(R.id.ll_front_view);
        }
    }

    public interface OnIconClickListener{

        void onPhoneClick(View view, TaskECommerceInfoEntity entity);

        void onVipClick(View view, TaskECommerceInfoEntity entity, int position);

        void onFollowUpClick(View view, TaskECommerceInfoEntity entity, int position);

        void onTransferClick(View view, TaskECommerceInfoEntity entity, int position);

        void onBenefitClick(View view, TaskECommerceInfoEntity entity, int position);
    }

    public void setOnIconClickListener(OnIconClickListener onIconClickListener){
        this.iconClickListener = onIconClickListener;
    }
}
