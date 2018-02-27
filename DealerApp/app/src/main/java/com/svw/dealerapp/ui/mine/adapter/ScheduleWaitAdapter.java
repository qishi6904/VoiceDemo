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
import com.svw.dealerapp.global.NewCustomerConstants;
import com.svw.dealerapp.ui.adapter.BaseRecyclerViewAdapter;
import com.svw.dealerapp.entity.mine.ScheduleWaitEntity.ScheduleWaitInfoEntity;
import com.svw.dealerapp.util.DateUtils;
import com.svw.dealerapp.util.TalkingDataUtils;

import java.util.List;

/**
 * Created by qinshi on 6/2/2017.
 */

public class ScheduleWaitAdapter extends BaseRecyclerViewAdapter<ScheduleWaitInfoEntity>{

    private OnBackViewClickListener onBackViewClickListener;
    private Holder lastEditHolder;

    public ScheduleWaitAdapter(Context context, List<ScheduleWaitInfoEntity> dataList) {
        super(context, dataList);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_mine_schedule_wait, null);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Holder viewHolder = (Holder) holder;
        ScheduleWaitInfoEntity entity = dataList.get(position);

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
            viewHolder.ivAction.setImageResource(R.mipmap.appointment_invitation);
        }else if("17020".equals(entity.getAppmTypeId())) {  //试乘试驾
            viewHolder.ivAction.setImageResource(R.mipmap.appointment_trial);
        }else if("17030".equals(entity.getAppmTypeId())) {  //订单
            viewHolder.ivAction.setImageResource(R.mipmap.appintment_order);
        }else if("17040".equals(entity.getAppmTypeId())) {  //交车
            viewHolder.ivAction.setImageResource(R.mipmap.appointment_car);
        }else {
            viewHolder.ivAction.setImageResource(0);
        }

        if(entity.isShowEdit()){
            viewHolder.llBackView.setVisibility(View.VISIBLE);
        }else {
            viewHolder.llBackView.setVisibility(View.GONE);
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

    private void setClickListener(final Holder holder, final ScheduleWaitInfoEntity entity, final int position){
        holder.ivDealMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!holder.isDoingAnim) {
                    if (holder.llBackView.getVisibility() == View.VISIBLE) {
                        holder.hideBAckView();
                        entity.setShowEdit(false);
                        if(lastEditHolder == holder){
                            lastEditHolder = null;
                        }
                    } else {
                        holder.showBackView();
                        entity.setShowEdit(true);
                        if(lastEditHolder != holder){
                            if(null != lastEditHolder) {
                                lastEditHolder.hideBAckView();
                            }
                        }
                        lastEditHolder = holder;
                    }
                }
                TalkingDataUtils.onEvent(context, "预约事项操作", "我的-预约列表");
            }
        });

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

        holder.llUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != onBackViewClickListener){
                    onBackViewClickListener.onUpdateClick(entity, position);
                }
            }
        });

        holder.llCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != onBackViewClickListener){
                    onBackViewClickListener.onCancelClick(entity, position);
                }
            }
        });

        holder.llCheckYC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != onBackViewClickListener){
                    onBackViewClickListener.onCheckYCClick(entity, position);
                }
            }
        });
    }

    private class Holder extends RecyclerView.ViewHolder{

        private View vUpDivider;
        private ImageView ivVerticalLine;
        private ImageView ivAction;
        private ImageView ivGenderIndicator;
        private TextView tvDate;
        private TextView tvAction;
        private TextView tvName;
        private TextView tvTime;
        private ImageView ivDealMore;
        private TextView tvCarType;
        private ImageView ivSendMsm;
        private ImageView ivCall;
        private LinearLayout llUpdate;
        private LinearLayout llCancel;
        private LinearLayout llCheckYC;
        private LinearLayout llBackView;
        private View vVipIndicator;

        private AlphaAnimation showBackViewAnim;
        private AlphaAnimation hideBackViewAnim;
        private boolean isDoingAnim = false;

        public Holder(View itemView) {
            super(itemView);
            assignViews(itemView);
            initAnim();
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
            ivDealMore = (ImageView) view.findViewById(R.id.iv_deal_more);
            tvCarType = (TextView) view.findViewById(R.id.tv_car_type);
            ivSendMsm = (ImageView) view.findViewById(R.id.iv_send_msm);
            ivCall = (ImageView) view.findViewById(R.id.iv_call);
            llUpdate = (LinearLayout) view.findViewById(R.id.ll_schedule_update);
            llCancel = (LinearLayout) view.findViewById(R.id.ll_schedule_cancel);
            llCheckYC = (LinearLayout) view.findViewById(R.id.ll_schedule_check_yc);
            llBackView = (LinearLayout) view.findViewById(R.id.ll_back_view);
            vVipIndicator = view.findViewById(R.id.v_vip_indicator);
        }

        private void initAnim(){
            showBackViewAnim = new AlphaAnimation(0f, 1f);
            showBackViewAnim.setDuration(300);
            showBackViewAnim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    isDoingAnim = false;
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

            hideBackViewAnim = new AlphaAnimation(1f, 0f);
            hideBackViewAnim.setDuration(300);
            hideBackViewAnim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    llBackView.setVisibility(View.INVISIBLE);
                    isDoingAnim = false;
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }

        public void showBackView(){
            isDoingAnim = true;
            llBackView.setVisibility(View.VISIBLE);
            llBackView.clearAnimation();
            llBackView.startAnimation(showBackViewAnim);
        }

        public void hideBAckView(){
            isDoingAnim = true;
            llBackView.clearAnimation();
            llBackView.startAnimation(hideBackViewAnim);
        }
    }

    public interface OnBackViewClickListener{
        void onUpdateClick(ScheduleWaitInfoEntity entity, int position);
        void onCancelClick(ScheduleWaitInfoEntity entity, int position);
        void onCheckYCClick(ScheduleWaitInfoEntity entity, int position);
    }

    public void setOnBackViewClickListener(OnBackViewClickListener listener){
        this.onBackViewClickListener = listener;
    }
}
