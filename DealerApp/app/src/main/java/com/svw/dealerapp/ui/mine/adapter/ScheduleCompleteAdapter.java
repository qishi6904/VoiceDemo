package com.svw.dealerapp.ui.mine.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.svw.dealerapp.R;
import com.svw.dealerapp.entity.mine.ScheduleCompleteEntity.ScheduleCompleteInfoEntity;
import com.svw.dealerapp.global.NewCustomerConstants;
import com.svw.dealerapp.ui.adapter.BaseRecyclerViewAdapter;
import com.svw.dealerapp.util.DateUtils;

import java.util.List;

/**
 * Created by qinshi on 6/2/2017.
 */

public class ScheduleCompleteAdapter extends BaseRecyclerViewAdapter<ScheduleCompleteInfoEntity>{

    public ScheduleCompleteAdapter(Context context, List<ScheduleCompleteInfoEntity> dataList) {
        super(context, dataList);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_mine_schedule_complete, null);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Holder viewHolder = (Holder) holder;
        ScheduleCompleteInfoEntity entity = dataList.get(position);

        if(0 == position){
            viewHolder.vUpDivider.setVisibility(View.VISIBLE);
        }else {
            viewHolder.vUpDivider.setVisibility(View.GONE);
        }

        if("0".equals(entity.getIsKeyuser())){
            viewHolder.vVipIndicator.setVisibility(View.VISIBLE);
        }else {
            viewHolder.vVipIndicator.setVisibility(View.INVISIBLE);
        }

        if("17010".equals(entity.getAppmTypeId())){  //邀约到店
            viewHolder.ivAction.setImageResource(R.mipmap.ic_schedule_arrive_shop_complete);
        }else if("17020".equals(entity.getAppmTypeId())) {  //试乘试驾
            viewHolder.ivAction.setImageResource(R.mipmap.ic_schdule_drive_complete);
        }else if("17030".equals(entity.getAppmTypeId())) {  //订单
            viewHolder.ivAction.setImageResource(R.mipmap.ic_schedule_order);
        }else if("17040".equals(entity.getAppmTypeId())) {  //交车
            viewHolder.ivAction.setImageResource(R.mipmap.ic_schedule_dlivery);
        }else {
            viewHolder.ivAction.setImageResource(0);
        }

        if("17510".equals(entity.getAppmStatusId())){   // 未完成
            viewHolder.ivScheduleStatus.setVisibility(View.INVISIBLE);
            viewHolder.ivScheduleStatus.setImageResource(0);
        }else if("17520".equals(entity.getAppmStatusId())){ // 已完成
            viewHolder.ivScheduleStatus.setVisibility(View.VISIBLE);
            viewHolder.ivScheduleStatus.setImageResource(R.mipmap.ic_home_schedule_finish);
        }else if("17530".equals(entity.getAppmStatusId())){ // 已逾期
            viewHolder.ivScheduleStatus.setVisibility(View.VISIBLE);
            viewHolder.ivScheduleStatus.setImageResource(R.mipmap.ic_home_schedule_delay);
        }else if("17540".equals(entity.getAppmStatusId())){ // 取消
            viewHolder.ivScheduleStatus.setVisibility(View.VISIBLE);
            viewHolder.ivScheduleStatus.setImageResource(R.mipmap.ic_home_schedule_cancel);
        }else {
            viewHolder.ivScheduleStatus.setVisibility(View.INVISIBLE);
            viewHolder.ivScheduleStatus.setImageResource(0);
        }

        viewHolder.tvDate.setText(DateUtils.dateFormat("MM月dd号", "yyyy-MM-dd'T'HH:mm:ss.SSSZ", entity.getAppmDateStr()));
        viewHolder.tvTime.setText(DateUtils.dateFormat("HH:mm", "yyyy-MM-dd'T'HH:mm:ss.SSSZ", entity.getAppmDateStr()));
        viewHolder.tvAction.setText(NewCustomerConstants.appointmentTypeMap.get(entity.getAppmTypeId()));
        viewHolder.tvName.setText(entity.getAppmCust());
        if(TextUtils.isEmpty(entity.getSeriesName())){
            viewHolder.tvCarType.setText(context.getResources().getString(R.string.yellow_car_series_empty));
        }else {
            viewHolder.tvCarType.setText(context.getResources().getString(R.string.mine_schedule_car_type) +
                    entity.getSeriesName());
        }

        setClickListener(viewHolder, entity, position);
    }

    @Override
    public int getItemCount() {
        return null == dataList ? 0 : dataList.size();
    }

    private void setClickListener(final Holder holder, final ScheduleCompleteInfoEntity entity, final int position){


        holder.ivCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri data = Uri.parse("tel:" + entity.getAppmMobile());
                intent.setData(data);
                context.startActivity(intent);
            }
        });

        holder.ivSendMsm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                Uri data = Uri.parse("smsto:" + entity.getAppmMobile());
                intent.setData(data);
                context.startActivity(intent);
            }
        });
    }

    private class Holder extends RecyclerView.ViewHolder {

        private View vUpDivider;
        private ImageView ivVerticalLine;
        private ImageView ivAction;
        private ImageView ivGenderIndicator;
        private TextView tvDate;
        private TextView tvAction;
        private TextView tvName;
        private TextView tvTime;
        private TextView tvCarType;
        private ImageView ivSendMsm;
        private ImageView ivCall;
        private View vVipIndicator;
        private ImageView ivScheduleStatus;

        public Holder(View itemView) {
            super(itemView);
            assignViews(itemView);
        }

        private void assignViews(View view) {
            vUpDivider = view.findViewById(R.id.v_up_divider);
            ivVerticalLine = (ImageView) view.findViewById(R.id.iv_vertical_line);
            ivAction = (ImageView) view.findViewById(R.id.iv_action);
            ivGenderIndicator = (ImageView) view.findViewById(R.id.iv_gender_indicator);
            tvDate = (TextView) view.findViewById(R.id.tv_date);
            tvAction = (TextView) view.findViewById(R.id.tv_action);
            tvName = (TextView) view.findViewById(R.id.tv_name);
            tvTime = (TextView) view.findViewById(R.id.tv_time);
            tvCarType = (TextView) view.findViewById(R.id.tv_car_type);
            ivSendMsm = (ImageView) view.findViewById(R.id.iv_send_msm);
            ivCall = (ImageView) view.findViewById(R.id.iv_call);
            vVipIndicator = view.findViewById(R.id.v_vip_indicator);
            ivScheduleStatus = (ImageView) view.findViewById(R.id.iv_schedule_status);
        }
    }
}
