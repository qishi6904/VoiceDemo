package com.svw.dealerapp.ui.task.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.svw.dealerapp.R;
import com.svw.dealerapp.entity.task.TaskFollowUpEntity;
import com.svw.dealerapp.ui.adapter.LinearDragRecyclerViewAdapter;
import com.svw.dealerapp.ui.widget.DragFrontLinearLayout;
import com.svw.dealerapp.ui.widget.DragLayout;
import com.svw.dealerapp.util.DateUtils;
import com.svw.dealerapp.util.TalkingDataUtils;

import java.util.List;

/**
 * Created by qinshi on 5/5/2017.
 */

public class TaskFollowUpAdapter extends LinearDragRecyclerViewAdapter<TaskFollowUpEntity.TaskFollowInfoEntity> {

    private OnIconClickListener iconClickListener;

    public TaskFollowUpAdapter(Context context, List<TaskFollowUpEntity.TaskFollowInfoEntity> dataList) {
        super(context, dataList);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_resource_yellow_card, null);
        final TaskFollowUpHolder holder = new TaskFollowUpHolder(view);

        holder.tvSource.setTextColor(context.getResources().getColor(R.color.resource_main_text));
        holder.tvSource.setBackgroundDrawable(
                context.getResources().getDrawable(R.drawable.shape_task_follow_action_bg));
        holder.ivAction.setImageResource(R.mipmap.ic_task_follow_up_schedul);

        setHolderListener(holder);

        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        TaskFollowUpHolder followUpHolder = (TaskFollowUpHolder) holder;
        TaskFollowUpEntity.TaskFollowInfoEntity infoEntity = dataList.get(position);

        if(position == 0){
            followUpHolder.vUpLine.setVisibility(View.VISIBLE);
        }else {
            followUpHolder.vUpLine.setVisibility(View.GONE);
        }

        followUpHolder.tvCustomerName.setText(infoEntity.getCustName());
//        followUpHolder.tvCarType.setText(infoEntity.getCarModelId() + "　" +
//                infoEntity.getOutsideColorNameCn() + "　" + infoEntity.getInsideColorNameCn());
        if(TextUtils.isEmpty(infoEntity.getSeriesName())){
            followUpHolder.tvCarType.setText(context.getResources().getString(R.string.yellow_car_series_empty));
        }else {
            followUpHolder.tvCarType.setText(infoEntity.getSeriesName());
        }
        followUpHolder.tvSource.setText(infoEntity.getAppmTypeName());
        if(!TextUtils.isEmpty(infoEntity.getDelayDays())) {
            followUpHolder.tvDelayDay.setText(context.getResources().getString(R.string.task_e_commerce_delay) +
                    infoEntity.getDelayDays() + context.getResources().getString(R.string.task_e_commerce_day));
        }else {
            followUpHolder.tvDelayDay.setText("");
        }
        if(TextUtils.isEmpty(infoEntity.getScheduleDesc())){
            followUpHolder.tvAction.setText(context.getResources().getString(R.string.task_tab_follow_up_plan_empty));
        }else {
            followUpHolder.tvAction.setText(infoEntity.getScheduleDesc());
        }
        followUpHolder.tvDateTime.setText(DateUtils.dateFormat("MM.dd HH:mm", "yyyy-MM-dd'T'HH:mm:ss.SSSZ", infoEntity.getCreateTime()));

        if(TextUtils.isEmpty(infoEntity.getAppmTypeName())){
            followUpHolder.tvSource.setVisibility(View.INVISIBLE);
            followUpHolder.vDivider.setVisibility(View.INVISIBLE);
        }else {
            followUpHolder.tvSource.setVisibility(View.VISIBLE);
            followUpHolder.vDivider.setVisibility(View.VISIBLE);
        }

        //设置是否重点客户
        if("0".equals(infoEntity.getIsKeyuser())){
            followUpHolder.vVipIndicator.setVisibility(View.VISIBLE);
            followUpHolder.ivBackViewOne.setImageResource(R.mipmap.ic_yellow_card_item_vip_selected);
        }else {
            followUpHolder.vVipIndicator.setVisibility(View.INVISIBLE);
            followUpHolder.ivBackViewOne.setImageResource(R.mipmap.ic_yellow_card_item_vip_unselected);
        }

        //设置用户级别
        if(!TextUtils.isEmpty(infoEntity.getOppLevel())) {
            if ("12020".equals(infoEntity.getOppLevel())) {
                followUpHolder.ivCustomerRank.setImageResource(R.mipmap.ic_yellow_card_customer_rank_a);
            } else if ("12030".equals(infoEntity.getOppLevel())) {
                followUpHolder.ivCustomerRank.setImageResource(R.mipmap.ic_yellow_card_customer_rank_b);
            } else if ("12040".equals(infoEntity.getOppLevel())) {
                followUpHolder.ivCustomerRank.setImageResource(R.mipmap.ic_yellow_card_customer_rank_c);
            } else if ("12050".equals(infoEntity.getOppLevel())) {
                followUpHolder.ivCustomerRank.setImageResource(R.mipmap.ic_yellow_card_customer_rank_n);
            } else if ("12010".equals(infoEntity.getOppLevel())) {
                followUpHolder.ivCustomerRank.setImageResource(R.mipmap.ic_yellow_card_customer_rank_h);
            }else {
                followUpHolder.ivCustomerRank.setImageResource(R.mipmap.ic_yellow_card_customer_rank_n);
            }
        }else {
            followUpHolder.ivCustomerRank.setImageResource(R.mipmap.ic_yellow_card_customer_rank_n);
        }

        setListener(followUpHolder, infoEntity, position);
    }

    private void setListener(final TaskFollowUpHolder yellowHolder, final TaskFollowUpEntity.TaskFollowInfoEntity infoEntity, final int position){
        yellowHolder.ivCallPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != iconClickListener){
                    iconClickListener.onPhoneClick(v, infoEntity);
                }
            }
        });
        yellowHolder.llBackViewOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != iconClickListener){
                    iconClickListener.onVipClick(v, infoEntity, position);
                }
            }
        });
        yellowHolder.llBackViewTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != iconClickListener){
                    iconClickListener.onFollowUpClick(v, infoEntity, position);
                }
                yellowHolder.dragLayout.closeDelay(600);
            }
        });
        yellowHolder.llBackViewThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != iconClickListener){
                    iconClickListener.onTransferClick(v, infoEntity, position);
                }
            }
        });
        yellowHolder.llFrontView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != iconClickListener){
                    iconClickListener.onFollowUpClick(v, infoEntity, position);
                    TalkingDataUtils.onEvent(context, "点击进入潜客详情", "跟进任务");
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return null == dataList ? 0 : dataList.size();
    }

    private class TaskFollowUpHolder extends DragLinearRecyclerViewHolder {

        private View vVipIndicator;
        private TextView tvCustomerName;
        private ImageView ivCustomerRank;
        private TextView tvSource;
        private ImageView ivCallPhone;
        private TextView tvCarType;
        private TextView tvAction;
        private TextView tvDelayDay;
        private TextView tvDateTime;
        private LinearLayout llBackViewOne;
        private ImageView ivBackViewOne;
        private LinearLayout llBackViewTwo;
        private LinearLayout llBackViewThree;
        private ImageView ivAction;
        private View vUpLine;
        private LinearLayout llFrontView;
        private View vDivider;

        public TaskFollowUpHolder(View itemView) {
            super(itemView);
            assignViews(itemView);
        }

        private void assignViews(View view) {
            dragLayout = (DragLayout) view.findViewById(R.id.drag_layout);
            vVipIndicator = view.findViewById(R.id.v_vip_indicator);
            frontLayout = (DragFrontLinearLayout) view.findViewById(R.id.ll_front_view);
            tvCustomerName = (TextView) view.findViewById(R.id.tv_customer_name);
            ivCustomerRank = (ImageView) view.findViewById(R.id.iv_customer_rank);
            tvSource = (TextView) view.findViewById(R.id.tv_source);
            ivCallPhone = (ImageView) view.findViewById(R.id.iv_call_phone);
            tvCarType = (TextView) view.findViewById(R.id.tv_car_type);
            tvAction = (TextView) view.findViewById(R.id.tv_action);
            tvDelayDay = (TextView) view.findViewById(R.id.tv_delay_day);
            tvDateTime = (TextView) view.findViewById(R.id.tv_date_time);
            llBackViewOne = (LinearLayout) view.findViewById(R.id.ll_back_view_one);
            ivBackViewOne = (ImageView) view.findViewById(R.id.iv_back_view_one);
            llBackViewTwo = (LinearLayout) view.findViewById(R.id.ll_back_view_two);
            llBackViewThree = (LinearLayout) view.findViewById(R.id.ll_back_view_three);
            ivAction = (ImageView) view.findViewById(R.id.iv_action);
            vUpLine = itemView.findViewById(R.id.v_divider_up);
            llFrontView = (LinearLayout) view.findViewById(R.id.ll_front_view);
            vDivider = view.findViewById(R.id.v_vertical_divider);
        }
    }

    public interface OnIconClickListener{

        void onPhoneClick(View view, TaskFollowUpEntity.TaskFollowInfoEntity entity);

        void onVipClick(View view, TaskFollowUpEntity.TaskFollowInfoEntity entity, int position);

        void onFollowUpClick(View view, TaskFollowUpEntity.TaskFollowInfoEntity entity, int position);

        void onTransferClick(View view, TaskFollowUpEntity.TaskFollowInfoEntity entity, int position);
    }

    public void setOnIconClickListener(OnIconClickListener onIconClickListener){
        this.iconClickListener = onIconClickListener;
    }
}
