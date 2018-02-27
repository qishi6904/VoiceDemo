package com.svw.dealerapp.ui.task.adapter;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.svw.dealerapp.R;
import com.svw.dealerapp.entity.task.TaskLeadsEntity;
import com.svw.dealerapp.ui.adapter.RelativeDragRecyclerViewAdapter;
import com.svw.dealerapp.ui.widget.DragFrontRelativeLayout;
import com.svw.dealerapp.ui.widget.DragLayout;
import com.svw.dealerapp.util.DateUtils;
import com.svw.dealerapp.util.DensityUtil;

import java.util.List;

/**
 * Created by qinshi on 8/27/2017.
 */

public class TaskLeadsAdapter extends RelativeDragRecyclerViewAdapter<TaskLeadsEntity.TaskLeadsInfoEntity> {

    private OnBackViewClickListener onBackViewClickListener;

    public TaskLeadsAdapter(Context context, List dataList) {
        super(context, dataList);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_task_leads, null);
        final TaskLeadsHolder holder = new TaskLeadsHolder(view);

        setHolderListener(holder);

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        TaskLeadsHolder viewHolder = (TaskLeadsHolder) holder;
        TaskLeadsEntity.TaskLeadsInfoEntity infoEntity = dataList.get(position);

        //折分描述
//        infoEntity.splitDescriptions();

        if(position == 0){
            viewHolder.vDividerUp.setVisibility(View.VISIBLE);
        }else {
            viewHolder.vDividerUp.setVisibility(View.GONE);
        }

//        if("11010".equals(infoEntity.getSrcTypeId())){       //如果是到店
//            viewHolder.rlYCLayout.setVisibility(View.GONE);
//            viewHolder.rlTrafficLayout.setVisibility(View.VISIBLE);
//
//            setDescText(infoEntity.getCustDescriptions(), viewHolder);
//            viewHolder.tvTrafficCustomerName.setText(infoEntity.getCustName());
//            String createTime = DateUtils.dateFormat("MM.dd HH:mm", "yyyy-MM-dd'T'HH:mm:ss.SSSZ", infoEntity.getCreateTime());
//            viewHolder.tvTime.setText(createTime);
//            if(!TextUtils.isEmpty(infoEntity.getDelayDays())) {
//                viewHolder.tvTrafficDelay.setText(context.getResources().getString(R.string.task_e_commerce_delay) +
//                        infoEntity.getDelayDays() + context.getResources().getString(R.string.task_e_commerce_day));
//            }else {
//                viewHolder.tvTrafficDelay.setText("");
//            }
//        }else {

        if("11010".equals(infoEntity.getSrcTypeId())) {       //如果是到店
            viewHolder.ivYcCallPhone.setImageResource(R.mipmap.ic_call_phone_gray);
        }else {
            viewHolder.ivYcCallPhone.setImageResource(R.mipmap.ic_yellow_card_item_call);
        }

//        viewHolder.rlYCLayout.setVisibility(View.VISIBLE);
//        viewHolder.rlTrafficLayout.setVisibility(View.GONE);

        if(TextUtils.isEmpty(infoEntity.getCustDescription())){
            viewHolder.tvYcAction.setText(context.getResources().getString(R.string.task_tab_leads_desc_empty));
        }else {
            viewHolder.tvYcAction.setText(infoEntity.getCustDescription());
        }
        viewHolder.tvYcCustomerName.setText(infoEntity.getCustName());
        String createTime = DateUtils.dateFormat("MM.dd HH:mm", "yyyy-MM-dd'T'HH:mm:ss.SSSZ", infoEntity.getCreateTime());
        viewHolder.tvYcDateTime.setText(createTime);
        viewHolder.tvYcSource.setText(infoEntity.getSrcTypeName());
        if(TextUtils.isEmpty(infoEntity.getSeriesName())){
            viewHolder.tvYcCarType.setText(context.getResources().getString(R.string.yellow_car_series_empty));
        }else {
            viewHolder.tvYcCarType.setText(infoEntity.getSeriesName());
        }
        if(!TextUtils.isEmpty(infoEntity.getDelayDays())) {
            viewHolder.tvYcDelayDay.setText(context.getResources().getString(R.string.task_e_commerce_delay) +
                    infoEntity.getDelayDays() + context.getResources().getString(R.string.task_e_commerce_day));
        }else {
            viewHolder.tvYcDelayDay.setText("");
        }

        LinearLayout.LayoutParams backViewParams = new LinearLayout.LayoutParams(viewHolder.llBackView.getLayoutParams());
        LinearLayout.LayoutParams backViewItemOneParams = new LinearLayout.LayoutParams(viewHolder.llBackViewOne.getLayoutParams());
        backViewParams.width = context.getResources().getDimensionPixelSize(R.dimen.traffic_item_one_item_back_layout_width);
        backViewItemOneParams.width = context.getResources().getDimensionPixelSize(R.dimen.traffic_item_back_view_one_item_width);
        viewHolder.ivRightIcon.setImageResource(R.mipmap.ic_traffic_status_created_card);
        viewHolder.llBackViewOne.setBackgroundColor(context.getResources().getColor(R.color.resource_blue));
        viewHolder.ivBackViewOne.setImageResource(R.mipmap.ic_traffic_check);
        viewHolder.tvBackViewOne.setText(context.getResources().getText(R.string.traffic_back_view_check));
        viewHolder.dragLayout.setRange(backViewParams.width);
        viewHolder.llBackView.setLayoutParams(backViewParams);
        viewHolder.llBackViewOne.setLayoutParams(backViewItemOneParams);

        //设置来源背景
        if("11010".equals(infoEntity.getSrcTypeId())){          //到店
            viewHolder.tvYcSource.setBackgroundDrawable(
                    context.getResources().getDrawable(R.drawable.shape_yellow_list_customer_source_shop));
        }else if("11020".equals(infoEntity.getSrcTypeId())){    //电商
            viewHolder.tvYcSource.setBackgroundDrawable(
                    context.getResources().getDrawable(R.drawable.shape_yellow_list_customer_source_e_commerce));
        }else if("11030".equals(infoEntity.getSrcTypeId())){    //线索
            viewHolder.tvYcSource.setBackgroundDrawable(
                    context.getResources().getDrawable(R.drawable.shape_yellow_list_customer_source_leads));
        }else if("11040".equals(infoEntity.getSrcTypeId())){    //电话
            viewHolder.tvYcSource.setBackgroundDrawable(
                    context.getResources().getDrawable(R.drawable.shape_yellow_list_customer_source_phone));
        }else if("11050".equals(infoEntity.getSrcTypeId())){    //外拓
            viewHolder.tvYcSource.setBackgroundDrawable(
                    context.getResources().getDrawable(R.drawable.shape_yellow_list_customer_source_activities));
        }else if("11060".equals(infoEntity.getSrcTypeId())){    //朋友推荐
            viewHolder.tvYcSource.setBackgroundDrawable(
                    context.getResources().getDrawable(R.drawable.shape_yellow_list_customer_source_friend));
        }else if("11099".equals(infoEntity.getSrcTypeId())){    //其他
            viewHolder.tvYcSource.setBackgroundDrawable(
                    context.getResources().getDrawable(R.drawable.shape_yellow_list_customer_source_other));
        }else {                                                 //默认
            viewHolder.tvYcSource.setBackgroundColor(Color.TRANSPARENT);
        }

        if("11050".equals(infoEntity.getSrcTypeId())) {
            viewHolder.sdvYcOrderSrc.setVisibility(View.GONE);
            viewHolder.tvSrcTip.setVisibility(View.VISIBLE);
            if(!TextUtils.isEmpty(infoEntity.getActivitySubject())) {
                viewHolder.tvSrcTip.setText(infoEntity.getActivitySubject());
                viewHolder.vYcVerticalDivider.setVisibility(View.VISIBLE);
            }else {
                viewHolder.tvSrcTip.setText("");
                viewHolder.vYcVerticalDivider.setVisibility(View.INVISIBLE);
            }
        }else {
            viewHolder.sdvYcOrderSrc.setVisibility(View.VISIBLE);
            viewHolder.tvSrcTip.setVisibility(View.GONE);
            //设置来源图标
//            Uri uri = Uri.parse("http://imgsrc.baidu.com/baike/pic/item/aa64034f78f0f73602aa31780855b319ebc41376.jpg");
            if(!TextUtils.isEmpty(infoEntity.getImageUrl())) {
                Uri uri = Uri.parse(infoEntity.getImageUrl());
                DraweeController draweeController =
                        Fresco.newDraweeControllerBuilder()
                                .setUri(uri)
                                .setAutoPlayAnimations(false)
                                .build();
                viewHolder.sdvYcOrderSrc.setController(draweeController);
                viewHolder.sdvYcOrderSrc.setVisibility(View.VISIBLE);
                viewHolder.vYcVerticalDivider.setVisibility(View.VISIBLE);
            }else {
                viewHolder.sdvYcOrderSrc.setVisibility(View.INVISIBLE);
                viewHolder.vYcVerticalDivider.setVisibility(View.INVISIBLE);
            }
        }


        if ("10510".equals(infoEntity.getLeadsStatusId())) {  //未处理流量
            setItemStatus(TaskLeadsHolder.WAITING_STATUS, viewHolder);
        } else if ("10530".equals(infoEntity.getLeadsStatusId())) { //无效流量
            setItemStatus(TaskLeadsHolder.INVALID_STATUS, viewHolder);
        } else if ("10520".equals(infoEntity.getLeadsStatusId())) { //已建卡流量
            setItemStatus(TaskLeadsHolder.CREATED_CARD_STATUS, viewHolder);
        }else {
            setItemStatus(TaskLeadsHolder.Default_STATUS, viewHolder);
        }

        setListener(viewHolder, infoEntity, position);
    }

    /**
     * 根据不同的状态设置左滑出现的按钮数量、宽度、文字、图标
     * @param status
     * @param holder
     */
    private void setItemStatus(int status, TaskLeadsHolder holder){
        LinearLayout.LayoutParams backViewParams = new LinearLayout.LayoutParams(holder.llBackView.getLayoutParams());
        LinearLayout.LayoutParams backViewItemOneParams = new LinearLayout.LayoutParams(holder.llBackViewOne.getLayoutParams());
        switch (status){
            case TaskLeadsHolder.CREATED_CARD_STATUS:
                backViewParams.width = context.getResources().getDimensionPixelSize(R.dimen.traffic_item_one_item_back_layout_width);
                backViewItemOneParams.width = context.getResources().getDimensionPixelSize(R.dimen.traffic_item_back_view_one_item_width);
                holder.ivRightIcon.setImageResource(R.mipmap.ic_traffic_status_created_card);
                holder.llBackViewOne.setBackgroundColor(context.getResources().getColor(R.color.resource_blue));
                holder.ivBackViewOne.setImageResource(R.mipmap.ic_traffic_check);
                holder.tvBackViewOne.setText(context.getResources().getText(R.string.traffic_back_view_check));
                break;
            case TaskLeadsHolder.WAITING_STATUS:
                backViewParams.width = context.getResources().getDimensionPixelSize(R.dimen.traffic_item_three_item_back_layout_width);
                backViewItemOneParams.width = context.getResources().getDimensionPixelSize(R.dimen.traffic_item_back_view_three_item_width);
                holder.ivRightIcon.setImageResource(R.mipmap.ic_traffic_status_waiting);
                holder.llBackViewOne.setBackgroundColor(context.getResources().getColor(R.color.traffic_item_back_view_one_bg));
                holder.ivBackViewOne.setImageResource(R.mipmap.ic_traffic_create_card);
                holder.tvBackViewOne.setText(context.getResources().getText(R.string.traffic_back_view_create_card));
                break;
            case TaskLeadsHolder.INVALID_STATUS:
                backViewParams.width = 0;
                backViewItemOneParams.width = 0;
                holder.ivRightIcon.setImageResource(R.mipmap.ic_traffic_status_invalid);
                break;
            default:
                backViewParams.width = 0;
                backViewItemOneParams.width = 0;
                holder.ivRightIcon.setImageResource(R.mipmap.ic_traffic_status_invalid);
                break;
        }
        holder.dragLayout.setRange(backViewParams.width);
        holder.llBackView.setLayoutParams(backViewParams);
        holder.llBackViewOne.setLayoutParams(backViewItemOneParams);
    }

    @Override
    public int getItemCount() {
        return null == dataList ? 0 : dataList.size();
    }

    private void setListener(final TaskLeadsHolder holder, final TaskLeadsEntity.TaskLeadsInfoEntity infoEntity, final int position) {
        holder.ivYcCallPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if("11010".equals(infoEntity.getSrcTypeId())) {       //如果是到店
                    return;
                }
                if (null != onBackViewClickListener) {
                    onBackViewClickListener.onPhoneClick(v, infoEntity);
                }
            }
        });
        holder.llBackViewOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onBackViewClickListener) {
                    onBackViewClickListener.onBackViewOneClick(v, infoEntity, position);
                }
                holder.dragLayout.closeDelay(600);
            }
        });
        holder.llBackViewTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onBackViewClickListener) {
                    onBackViewClickListener.onBackViewTwoClick(v, infoEntity, position);
                }
                holder.dragLayout.closeDelay(600);
            }
        });
        holder.llBackViewThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onBackViewClickListener) {
                    onBackViewClickListener.onBackViewThreeClick(v, infoEntity, position);
                }
            }
        });
//        holder.rlYCLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (null != onBackViewClickListener) {
//                    onBackViewClickListener.onBackViewTwoClick(v, infoEntity, position);
//                }
//            }
//        });
    }

    /**
     * 设置描述字符串
     * @param strings
     */
    private void setDescText(String[] strings, TaskLeadsHolder holder){
        for(int i = 0; i < holder.llDescContainer.getChildCount(); i++){
            TextView textView = (TextView) holder.llDescContainer.getChildAt(i);
            textView.setVisibility(View.GONE);
        }
        if(null == strings || strings.length == 0){
            return;
        }

        for(int i = 0; i < strings.length; i++){
            TextView textView;
            if(i < holder.llDescContainer.getChildCount()) {
                textView = (TextView) holder.llDescContainer.getChildAt(i);
                textView.setVisibility(View.VISIBLE);
            }else {
                textView = (TextView) View.inflate(context, R.layout.ui_traffic_item_desc_text_view, null);
                holder.llDescContainer.addView(textView);
            }

            textView.setText(strings[i].trim());

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(textView.getLayoutParams());
            if(i == 0){
                params.leftMargin = 0;
            }else {
                params.leftMargin = DensityUtil.dp2px(context, 10);
            }
            textView.setLayoutParams(params);
        }
    }

    private class TaskLeadsHolder extends RelativeDragRecyclerViewAdapter.DragRelativeRecyclerViewHolder{

        //已建卡流量
        public static final int CREATED_CARD_STATUS = 2;
        //未处理流量
        public static final int WAITING_STATUS = 0;
        //无效流量
        public static final int INVALID_STATUS = 1;
        //默认
        public static final int Default_STATUS = -1;

        private View vDividerUp;
        private DragLayout dragYcLayout;
        private DragFrontRelativeLayout rlFrontView;
        private TextView tvTrafficCustomerName;
        private ImageView ivRightIcon;
        private LinearLayout llDescContainer;
        private TextView tvTime;
        private TextView tvTrafficDelay;
        private TextView tvYcCustomerName;
        private TextView tvYcSource;
        private View vYcVerticalDivider;
        private ImageView ivYcCallPhone;
        private SimpleDraweeView sdvYcOrderSrc;
        private ImageView ivYcCarType;
        private ImageView ivYcAction;
        private TextView tvYcCarType;
        private TextView tvYcAction;
        private TextView tvYcDelayDay;
        private TextView tvYcDateTime;
        private LinearLayout llBackView;
        private LinearLayout llBackViewOne;
        private ImageView ivBackViewOne;
        private TextView tvBackViewOne;
        private LinearLayout llBackViewTwo;
        private ImageView ivBackViewTwo;
        private TextView tvBackViewTwo;
        private LinearLayout llBackViewThree;
        private ImageView ivBackViewThree;
        private TextView tvBackViewThree;
        private RelativeLayout rlTrafficLayout;
        private RelativeLayout rlYCLayout;
        private TextView tvSrcTip;

        public TaskLeadsHolder(View itemView) {
            super(itemView);
            assignViews(itemView);
        }

        private void assignViews(View itemView) {

            dragLayout = (DragLayout) itemView.findViewById(R.id.drag_layout);
            frontLayout = (DragFrontRelativeLayout) itemView.findViewById(R.id.rl_front_view);
            vDividerUp = itemView.findViewById(R.id.v_divider_up);

            rlTrafficLayout = (RelativeLayout) itemView.findViewById(R.id.rl_traffic_layout);
            tvTrafficCustomerName = (TextView) itemView.findViewById(R.id.tv_traffic_customer_name);
            ivRightIcon = (ImageView) itemView.findViewById(R.id.iv_right_icon);
            llDescContainer = (LinearLayout) itemView.findViewById(R.id.ll_desc_container);
            tvTrafficDelay = (TextView) itemView.findViewById(R.id.tv_traffic_delay_day);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);

            rlYCLayout = (RelativeLayout) itemView.findViewById(R.id.rl_yc_layout);
            tvYcCustomerName = (TextView) itemView.findViewById(R.id.tv_yc_customer_name);
            tvYcSource = (TextView) itemView.findViewById(R.id.tv_yc_source);
            vYcVerticalDivider = itemView.findViewById(R.id.v_yc_vertical_divider);
            ivYcCallPhone = (ImageView) itemView.findViewById(R.id.iv_yc_call_phone);
            sdvYcOrderSrc = (SimpleDraweeView) itemView.findViewById(R.id.sdv_yc_order_src);
            tvSrcTip = (TextView) itemView.findViewById(R.id.tv_src_tip);
            ivYcCarType = (ImageView) itemView.findViewById(R.id.iv_yc_car_type);
            ivYcAction = (ImageView) itemView.findViewById(R.id.iv_yc_action);
            tvYcCarType = (TextView) itemView.findViewById(R.id.tv_yc_car_type);
            tvYcAction = (TextView) itemView.findViewById(R.id.tv_yc_action);
            tvYcDelayDay = (TextView) itemView.findViewById(R.id.tv_yc_delay_day);
            tvYcDateTime = (TextView) itemView.findViewById(R.id.tv_yc_date_time);

            llBackView = (LinearLayout) itemView.findViewById(R.id.ll_back_view);
            llBackViewOne = (LinearLayout) itemView.findViewById(R.id.ll_back_view_one);
            ivBackViewOne = (ImageView) itemView.findViewById(R.id.iv_back_view_one);
            tvBackViewOne = (TextView) itemView.findViewById(R.id.tv_back_view_one);
            llBackViewTwo = (LinearLayout) itemView.findViewById(R.id.ll_back_view_two);
            ivBackViewTwo = (ImageView) itemView.findViewById(R.id.iv_back_view_two);
            tvBackViewTwo = (TextView) itemView.findViewById(R.id.tv_back_view_two);
            llBackViewThree = (LinearLayout) itemView.findViewById(R.id.ll_back_view_three);
            ivBackViewThree = (ImageView) itemView.findViewById(R.id.iv_back_view_three);
            tvBackViewThree = (TextView) itemView.findViewById(R.id.tv_back_view_three);

        }
    }

    public interface OnBackViewClickListener{

        void onPhoneClick(View view, TaskLeadsEntity.TaskLeadsInfoEntity entity);

        void onBackViewOneClick(View view, TaskLeadsEntity.TaskLeadsInfoEntity entity, int position);

        void onBackViewTwoClick(View view, TaskLeadsEntity.TaskLeadsInfoEntity entity, int position);

        void onBackViewThreeClick(View view, TaskLeadsEntity.TaskLeadsInfoEntity entity, int position);
    }

    public void setOnBackViewClickListener(OnBackViewClickListener onBackViewClickListener){
        this.onBackViewClickListener = onBackViewClickListener;
    }
}
