package com.svw.dealerapp.ui.task.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.svw.dealerapp.R;
import com.svw.dealerapp.entity.task.TaskTrafficEntity;
import com.svw.dealerapp.ui.adapter.RelativeDragRecyclerViewAdapter;
import com.svw.dealerapp.ui.widget.DragFrontRelativeLayout;
import com.svw.dealerapp.ui.widget.DragLayout;
import com.svw.dealerapp.util.DateUtils;
import com.svw.dealerapp.util.DensityUtil;

import java.util.List;

/**
 * Created by qinshi on 5/25/2017.
 */
@Deprecated
public class TaskTrafficAdapter extends RelativeDragRecyclerViewAdapter<TaskTrafficEntity.TaskTrafficInfoEntity> {

    private TaskTrafficAdapter.OnBackViewClickListener backViewClickListener;

    public TaskTrafficAdapter(Context context, List<TaskTrafficEntity.TaskTrafficInfoEntity> dataList) {
        super(context, dataList);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_resource_traffic, null);
        final TrafficRecyclerViewHolder holder = new TrafficRecyclerViewHolder(view);

        setHolderListener(holder);

        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final TrafficRecyclerViewHolder arriveViewHolder = (TrafficRecyclerViewHolder) holder;
        final TaskTrafficEntity.TaskTrafficInfoEntity infoEntity = dataList.get(position);
        //折分描述
        infoEntity.splitDescriptions();

        if(position == 0){
            arriveViewHolder.vUpLine.setVisibility(View.VISIBLE);
        }else {
            arriveViewHolder.vUpLine.setVisibility(View.GONE);
        }

        if ("10510".equals(infoEntity.getLeadsStatusId())) {  //未处理流量
            setItemStatus(TaskTrafficAdapter.TrafficRecyclerViewHolder.WAITING_STATUS, arriveViewHolder);
        } else if ("10530".equals(infoEntity.getLeadsStatusId())) { //无效流量
            setItemStatus(TaskTrafficAdapter.TrafficRecyclerViewHolder.INVALID_STATUS, arriveViewHolder);
        } else if ("10520".equals(infoEntity.getLeadsStatusId())) { //已建卡流量
            setItemStatus(TaskTrafficAdapter.TrafficRecyclerViewHolder.CREATED_CARD_STATUS, arriveViewHolder);
        }else {
            setItemStatus(TaskTrafficAdapter.TrafficRecyclerViewHolder.Default_STATUS, arriveViewHolder);
        }

        setDescText(infoEntity.getCustDescriptions(), arriveViewHolder);

        arriveViewHolder.tvCustomerName.setText(infoEntity.getCustName());
        String createTime = DateUtils.dateFormat("MM.dd HH:mm", "yyyy-MM-dd'T'HH:mm:ss.SSSZ", infoEntity.getCreateTime());
        arriveViewHolder.tvTime.setText(createTime);

        arriveViewHolder.frontLayout.setTag(position);
        arriveViewHolder.llBackViewOne.setTag(position);
        arriveViewHolder.llBackViewTwo.setTag(position);
        arriveViewHolder.llBackViewThree.setTag(position);

        arriveViewHolder.llBackViewOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != backViewClickListener){
                    backViewClickListener.onBackViewOneClick(infoEntity, position);
                }
            }
        });
        arriveViewHolder.llBackViewTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != backViewClickListener){
                    backViewClickListener.onBackViewTwoClick(infoEntity, position);
                }
            }
        });
        arriveViewHolder.llBackViewThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != backViewClickListener){
                    backViewClickListener.onBackViewThreeClick(infoEntity, position);
                }
            }
        });

        if("10530".equals(infoEntity.getLeadsStatusId())){
            arriveViewHolder.dragLayout.setOnTouchDragListener(null);
        }else {
            //根据左滑的距离设置item右边icon的透明度
            arriveViewHolder.dragLayout.setOnTouchDragListener(new DragLayout.OnTouchDragListener() {
                @Override
                public void onDrag(int rangeDistance, int offset) {
                    arriveViewHolder.ivRightIcon.setAlpha(1f - (offset * 1f / rangeDistance));
                }
            });
        }
        arriveViewHolder.ivRightIcon.setAlpha(1f);
    }

    /**
     * 设置描述字符串
     * @param strings
     */
    private void setDescText(String[] strings, TaskTrafficAdapter.TrafficRecyclerViewHolder holder){
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

    @Override
    public int getItemCount() {
        return null == dataList ? 0 : dataList.size();
    }

    private class TrafficRecyclerViewHolder extends RelativeDragRecyclerViewAdapter.DragRelativeRecyclerViewHolder {

        //已建卡流量
        public static final int CREATED_CARD_STATUS = 2;
        //未处理流量
        public static final int WAITING_STATUS = 0;
        //无效流量
        public static final int INVALID_STATUS = 1;
        //默认
        public static final int Default_STATUS = -1;

        View itemView;
        TextView tvCustomerName;
        TextView tvTime;
        LinearLayout llDescContainer;
        ImageView ivRightIcon;
        LinearLayout llBackView;
        LinearLayout llBackViewOne;
        LinearLayout llBackViewTwo;
        LinearLayout llBackViewThree;
        ImageView ivBackViewOne;
        TextView tvBackViewOne;
        View vUpLine;

        public TrafficRecyclerViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            assignViews(itemView);
        }

        private void assignViews(View itemView) {
            vUpLine = itemView.findViewById(R.id.v_divider_up);
            dragLayout = (DragLayout) itemView.findViewById(R.id.drag_layout);
            frontLayout = (DragFrontRelativeLayout) itemView.findViewById(R.id.rl_front_view);
            tvCustomerName = (TextView) itemView.findViewById(R.id.tv_customer_name);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
            llDescContainer = (LinearLayout) itemView.findViewById(R.id.ll_desc_container);
            ivRightIcon = (ImageView) itemView.findViewById(R.id.iv_right_icon);
            llBackView = (LinearLayout) itemView.findViewById(R.id.ll_back_view);
            llBackViewOne = (LinearLayout) itemView.findViewById(R.id.ll_back_view_one);
            llBackViewTwo = (LinearLayout) itemView.findViewById(R.id.ll_back_view_two);
            llBackViewThree = (LinearLayout) itemView.findViewById(R.id.ll_back_view_three);
            ivBackViewOne = (ImageView) itemView.findViewById(R.id.iv_back_view_one);
            tvBackViewOne = (TextView) itemView.findViewById(R.id.tv_back_view_one);
        }
    }

    /**
     * 根据不同的状态设置左滑出现的按钮数量、宽度、文字、图标
     * @param status
     * @param holder
     */
    private void setItemStatus(int status, TaskTrafficAdapter.TrafficRecyclerViewHolder holder){
        LinearLayout.LayoutParams backViewParams = new LinearLayout.LayoutParams(holder.llBackView.getLayoutParams());
        LinearLayout.LayoutParams backViewItemOneParams = new LinearLayout.LayoutParams(holder.llBackViewOne.getLayoutParams());
        switch (status){
            case TaskTrafficAdapter.TrafficRecyclerViewHolder.CREATED_CARD_STATUS:
                backViewParams.width = context.getResources().getDimensionPixelSize(R.dimen.traffic_item_one_item_back_layout_width);
                backViewItemOneParams.width = context.getResources().getDimensionPixelSize(R.dimen.traffic_item_back_view_one_item_width);
                holder.ivRightIcon.setImageResource(R.mipmap.ic_traffic_status_created_card);
                holder.llBackViewOne.setBackgroundColor(context.getResources().getColor(R.color.resource_blue));
                holder.ivBackViewOne.setImageResource(R.mipmap.ic_traffic_check);
                holder.tvBackViewOne.setText(context.getResources().getText(R.string.traffic_back_view_check));
                break;
            case TaskTrafficAdapter.TrafficRecyclerViewHolder.WAITING_STATUS:
                backViewParams.width = context.getResources().getDimensionPixelSize(R.dimen.traffic_item_three_item_back_layout_width);
                backViewItemOneParams.width = context.getResources().getDimensionPixelSize(R.dimen.traffic_item_back_view_three_item_width);
                holder.ivRightIcon.setImageResource(R.mipmap.ic_traffic_status_waiting);
                holder.llBackViewOne.setBackgroundColor(context.getResources().getColor(R.color.traffic_item_back_view_one_bg));
                holder.ivBackViewOne.setImageResource(R.mipmap.ic_traffic_create_card);
                holder.tvBackViewOne.setText(context.getResources().getText(R.string.traffic_back_view_create_card));
                break;
            case TaskTrafficAdapter.TrafficRecyclerViewHolder.INVALID_STATUS:
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

    public interface OnBackViewClickListener{
        void onBackViewOneClick(TaskTrafficEntity.TaskTrafficInfoEntity entity, int position);
        void onBackViewTwoClick(TaskTrafficEntity.TaskTrafficInfoEntity entity, int position);
        void onBackViewThreeClick(TaskTrafficEntity.TaskTrafficInfoEntity entity, int position);
    }

    public void setOnBackViewClickListener(TaskTrafficAdapter.OnBackViewClickListener listener){
        this.backViewClickListener = listener;
    }
}
